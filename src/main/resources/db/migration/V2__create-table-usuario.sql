CREATE TABLE IF NOT EXISTS usuario (

    id BIGINT NOT NULL AUTO_INCREMENT,
    nombre VARCHAR(255),
    email VARCHAR(255),
    contrasena VARCHAR(255),

    PRIMARY KEY (id)

) ENGINE=InnoDB;