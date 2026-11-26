# Backend Pastelería con Docker

## Estructura esperada

Pon los 5 proyectos y estos archivos en la misma carpeta:

```
pasteleria-backend/
├── auth/            (con su Dockerfile)
├── producto/
├── compra/
├── contenido/
├── gateway/
├── docker-compose.yml
├── .env.example
└── .gitignore
```

## Primera vez

```bash
cp .env.example .env        # en Windows (PowerShell): Copy-Item .env.example .env
# edita .env y cambia las claves
docker compose up -d --build
```

La primera construcción tarda varios minutos (descarga Maven y las dependencias). Las siguientes son mucho más rápidas.

## Comandos del día a día

| Qué quieres hacer | Comando |
|---|---|
| Ver el estado de todo | `docker compose ps` |
| Ver logs de un servicio | `docker compose logs -f compra` |
| Reconstruir un solo servicio tras cambiar código | `docker compose up -d --build compra` |
| Detener todo (conserva los datos) | `docker compose down` |
| Detener y BORRAR la base de datos | `docker compose down -v` |

## Puertos en tu computador

| Servicio | URL |
|---|---|
| Gateway (el front usa este) | http://localhost:8080 |
| auth / producto / compra / contenido | http://localhost:9080 / 9081 / 9082 / 9083 |
| MySQL | localhost:**3307** (usuario root) |

Swagger de cada servicio: `http://localhost:90XX/doc/swagger-ui.html`.

## Notas

- Necesitas dar a Docker Desktop al menos **4 GB de RAM** (5 aplicaciones Java + MySQL).
- Los servicios esperan a que MySQL (y `producto`, en el caso de `compra`) estén sanos antes de arrancar; el primer arranque completo puede tardar 1 a 2 minutos.
- Si cambias `MYSQL_ROOT_PASSWORD` DESPUÉS de la primera vez, MySQL conserva la clave anterior: ejecuta `docker compose down -v` para empezar de cero.
- Los puertos 9080-9083 y 3307 se publican solo para desarrollo y para mostrar Swagger. En AWS solo debe quedar expuesto el gateway.
