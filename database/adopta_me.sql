CREATE DATABASE IF NOT EXISTS adopta_me;
USE adopta_me;

CREATE TABLE usuario (
    id_usuario BIGINT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    apellido_paterno VARCHAR(100) NOT NULL,
    apellido_materno VARCHAR(100),
    email VARCHAR(150) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    telefono VARCHAR(20),
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_registro DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cat_tipo_mascota (
    id_tipo_mascota BIGINT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(50) NOT NULL,
    activo BOOLEAN NOT NULL DEFAULT TRUE
);

CREATE TABLE mascota (
    id_mascota BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_usuario_donador BIGINT NOT NULL,
    id_tipo_mascota BIGINT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    raza VARCHAR(100),
    sexo VARCHAR(20) NOT NULL,
    edad_aproximada VARCHAR(50),
    descripcion TEXT,
    estado_adopcion VARCHAR(30) NOT NULL DEFAULT 'Disponible',
    activo BOOLEAN NOT NULL DEFAULT TRUE,
    fecha_publicacion DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_mascota_usuario FOREIGN KEY (id_usuario_donador) REFERENCES usuario(id_usuario),
    CONSTRAINT fk_mascota_tipo FOREIGN KEY (id_tipo_mascota) REFERENCES cat_tipo_mascota(id_tipo_mascota)
);

CREATE TABLE imagen_mascota (
    id_imagen BIGINT AUTO_INCREMENT PRIMARY KEY,
    id_mascota BIGINT NOT NULL,
    url_imagen VARCHAR(255) NOT NULL,
    imagen_principal BOOLEAN NOT NULL DEFAULT FALSE,
    fecha_subida DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_imagen_mascota FOREIGN KEY (id_mascota) REFERENCES mascota(id_mascota) ON DELETE CASCADE
);