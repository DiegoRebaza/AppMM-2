--liquibase formatted sql

--changeset creamy:002
INSERT INTO pedidos (id, numero, usuario_email, usuario_nombre, subtotal, envio, total, tipo_entrega, direccion, comuna, metodo_pago, ultimos4, estado) VALUES
(1, 'CRM-A1B2C3', 'cliente@creamy.cl', 'Camila Rojas', 10970, 2500, 13470, 'despacho', 'Av. Providencia 1234, Depto 402', 'Providencia', 'tarjeta', '4532', 'Confirmado'),
(2, 'CRM-D4E5F6', 'diego@creamy.cl', 'Diego Muñoz', 7980, 0, 7980, 'retiro', NULL, NULL, 'efectivo', NULL, 'Entregado');

INSERT INTO pedido_items (pedido_id, producto_id, nombre, precio, cantidad, imagen) VALUES
(1, 1, 'Torta de crema y frutilla', 3490, 2, '/images/comida/pastel_frutilla.jpg'),
(1, 5, 'Torta tres leches', 3990, 1, '/images/comida/tres_leches.jpg'),
(2, 2, 'Torta de chocolate', 4490, 1, '/images/comida/pastel_chocolate.jpg'),
(2, 19, 'Latte de vainilla', 3490, 1, '/images/bebestible/latte_vainilla.jpg');

INSERT INTO encargos (id, correo, nombre, contacto, torta, pisos, personas, sabor, quitar, fecha_entrega, diseno, estado) VALUES
(1, 'cliente@creamy.cl', 'Camila Rojas', '+56 9 8765 4321', 'Torta de zanahoria', 2, 25, 'Vainilla y queso crema', 'Sin nueces', '2026-12-15', 'Decoración floral en tonos pastel con mensaje Feliz Cumpleaños.', 'Pendiente');