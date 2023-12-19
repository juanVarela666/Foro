CREATE TABLE respuesta (

    id BIGINT NOT NULL AUTO_INCREMENT,
    mensaje VARCHAR(255),
    topico_id BIGINT,
    fecha_creacion DATETIME(6),
    autor_id BIGINT,
    solucion BIT,

    PRIMARY KEY (id),

    FOREIGN KEY (autor_id) REFERENCES usuario (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (topico_id) REFERENCES topico (id) ON UPDATE CASCADE ON DELETE CASCADE

) ENGINE=InnoDB;