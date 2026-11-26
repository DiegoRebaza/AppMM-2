--liquibase formatted sql

--changeset creamy:001
CREATE TABLE pedidos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    numero VARCHAR(30) NOT NULL UNIQUE,
    usuario_email VARCHAR(150) NOT NULL,
    usuario_nombre VARCHAR(150),
    subtotal INT NOT NULL,
    envio INT NOT NULL,
    total INT NOT NULL,
    tipo_entrega VARCHAR(30) NOT NULL,
    direccion VARCHAR(300),
    comuna VARCHAR(100),
    metodo_pago VARCHAR(30) NOT NULL,
    ultimos4 VARCHAR(4),
    estado VARCHAR(30) DEFAULT 'Confirmado',
    fecha TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE pedido_items (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pedido_id BIGINT NOT NULL,
    producto_id BIGINT,
    nombre VARCHAR(150) NOT NULL,
    precio INT NOT NULL,
    cantidad INT NOT NULL,
    imagen VARCHAR(255),
    FOREIGN KEY (pedido_id) REFERENCES pedidos(id) ON DELETE CASCADE
);

CREATE TABLE encargos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    correo VARCHAR(150),
    nombre VARCHAR(150) NOT NULL,
    contacto VARCHAR(150) NOT NULL,
    torta VARCHAR(150) NOT NULL,
    pisos INT NOT NULL,
    personas INT NOT NULL,
    sabor VARCHAR(100) NOT NULL,
    quitar TEXT,
    fecha_entrega DATE NOT NULL,
    diseno TEXT,
    foto LONGTEXT,
    estado VARCHAR(30) DEFAULT 'Pendiente',
    creado TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);