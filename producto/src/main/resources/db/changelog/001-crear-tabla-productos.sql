--liquibase formatted sql

--changeset creamy:001
CREATE TABLE productos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT NOT NULL,
    detalle TEXT,
    precio INT NOT NULL,
    precio_original INT NOT NULL,
    descuento INT DEFAULT 0,
    imagen VARCHAR(255),
    alt VARCHAR(150),
    categoria VARCHAR(100) NOT NULL,
    tag VARCHAR(50),
    personalizado BOOLEAN DEFAULT FALSE
);