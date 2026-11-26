--liquibase formatted sql

--changeset creamy:001
CREATE TABLE blogs (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(200) NOT NULL,
    resumen TEXT NOT NULL,
    categoria VARCHAR(50) NOT NULL,
    fecha DATE NOT NULL,
    autor VARCHAR(100) NOT NULL,
    minutos INT NOT NULL,
    imagen VARCHAR(255),
    alt VARCHAR(150)
);

CREATE TABLE blog_parrafos (
    blog_id BIGINT NOT NULL,
    orden INT NOT NULL,
    texto TEXT NOT NULL,
    PRIMARY KEY (blog_id, orden),
    FOREIGN KEY (blog_id) REFERENCES blogs(id) ON DELETE CASCADE
);

CREATE TABLE mensajes_contacto (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL,
    mensaje VARCHAR(500) NOT NULL,
    fecha_envio TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);