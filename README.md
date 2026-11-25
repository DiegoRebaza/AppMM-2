# Backend Pastelería — microservicios

## Arquitectura

| Servicio    | Puerto | Base de datos   | Rol |
|-------------|--------|-----------------|-----|
| `gateway`   | 8080   | —               | Punto de entrada único del front, CORS, enrutamiento |
| `auth`      | 9080   | `bd_auth`       | Registro, login, JWT (access + refresh), usuarios |
| `producto`  | 9081   | `bd_producto`   | Catálogo, categorías, stock |
| `compra`    | 9082   | `bd_compra`     | Checkout, historial de compras, reservas |
| `contenido` | 9083   | `bd_contenido`  | Blog y mensajes de contacto |

Rutas del gateway:

- `/auth/**`, `/api/usuarios/**` → auth
- `/api/productos/**`, `/api/categorias/**` → producto
- `/api/compras/**`, `/api/reservas/**` → compra
- `/api/blog/**`, `/api/contacto/**` → contenido
- `/internal/**` NO se expone (solo compra → producto, con `X-Internal-Key`)

## Orden de arranque

1. MySQL corriendo en `localhost:3306`
2. `auth`, `producto`, `contenido` (cualquier orden)
3. `compra` (necesita `producto`)
4. `gateway`

## Variables de entorno (todas tienen valor por defecto para desarrollo local)

| Variable | Usada por | Por defecto | Nota |
|---|---|---|---|
| `DB_HOST`, `DB_PORT`, `DB_USER`, `DB_PASSWORD` | auth, producto, compra, contenido | localhost, 3306, root, root | Cambiar la clave en AWS |
| `DB_NAME` | cada servicio | `bd_<servicio>` | |
| `JWT_SECRET` | auth, producto, compra, contenido | secreto de desarrollo | **Debe ser idéntico en los 4**. Mínimo 32 caracteres |
| `INTERNAL_API_KEY` | producto, compra | clave de desarrollo | **Debe ser idéntica en ambos** |
| `ADMIN_EMAIL`, `ADMIN_PASSWORD` | auth | admin@pasteleria.cl / Admin12345! | **Cambiar antes de AWS** |
| `PRODUCTO_URL` | compra, gateway | http://localhost:9081 | |
| `AUTH_URL`, `COMPRA_URL`, `CONTENIDO_URL` | gateway | http://localhost:908x | |
| `CORS_ORIGINS` | gateway | http://localhost:5173,http://localhost:3000 | Dominio del front en AWS |

## Prueba de punta a punta (todo vía gateway, puerto 8080)

```bash
# 1. Login como admin
curl -s -X POST http://localhost:8080/auth/login -H "Content-Type: application/json" \
  -d '{"email":"admin@pasteleria.cl","password":"Admin12345!"}'
# -> copiar "accessToken"

# 2. Catálogo público (sin token)
curl -s http://localhost:8080/api/productos

# 3. Registrar un cliente y comprar
curl -s -X POST http://localhost:8080/auth/register -H "Content-Type: application/json" \
  -d '{"nombre":"Camila Silva","email":"camila@mail.com","password":"clave12345"}'
# -> copiar el accessToken del cliente

curl -s -X POST http://localhost:8080/api/compras \
  -H "Authorization: Bearer TOKEN_DEL_CLIENTE" -H "Content-Type: application/json" \
  -d '{"items":[{"productoId":2,"cantidad":2}],"tipoEntrega":"DOMICILIO",
       "direccionEntrega":"Av. Siempre Viva 123","comuna":"Santiago","metodoPago":"TARJETA",
       "titular":"Camila Silva","numeroTarjeta":"4242 4242 4242 4242","vencimiento":"12/30","cvv":"123"}'

# 4. Verificar que NO se expone lo interno (debe dar 404)
curl -i -X POST http://localhost:8080/internal/productos/descontar-stock
```

## Swagger de cada servicio (para la defensa)

- auth: http://localhost:9080/doc/swagger-ui.html
- producto: http://localhost:9081/doc/swagger-ui.html
- compra: http://localhost:9082/doc/swagger-ui.html
- contenido: http://localhost:9083/doc/swagger-ui.html

## Seguridad: qué decir en la defensa

- Contraseñas con BCrypt; el rol nunca viene del cliente (registro público = CLIENTE).
- JWT firmado (HS256) con access token de 30 min y refresh token de 7 días; el refresh no sirve para acceder a recursos.
- Cada microservicio verifica el JWT por sí mismo (no depende solo del gateway).
- Rutas de administración exigen rol ADMIN; las de usuario, token válido.
- Datos de tarjeta: se validan (Luhn, vencimiento, CVV) y se descartan; solo se guardan titular y últimos 4 dígitos.
- Los precios y totales los calcula el backend; el front no puede alterarlos.
- Stock con descuento atómico en una sola sentencia SQL, todo o nada.
- `/internal/**` protegido con clave compartida y no expuesto por el gateway.

## Límites conocidos

- No hay transacción distribuida entre compra y producto (se compensa reponiendo stock; un patrón Saga sería lo ideal).
- Los refresh tokens no se pueden revocar antes de expirar (no hay lista de tokens en base de datos).
- El pago está simulado; en un caso real se integraría Webpay u otra pasarela.
- El código fue escrito sin compilarse en el entorno de generación: la primera compilación en IntelliJ es la validación.
