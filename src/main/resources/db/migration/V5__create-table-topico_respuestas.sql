CREATE TABLE topico_respuestas (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,
        mensaje VARCHAR(255),
        topico_id BIGINT,
        fecha_creacion DATETIME(6),
        autor_id BIGINT,
        solucion BIT(1),
        respuestas_id BIGINT,

        FOREIGN KEY (topico_id) REFERENCES topico(id) ON UPDATE CASCADE ON DELETE CASCADE,
        FOREIGN KEY (autor_id) REFERENCES usuario(id) ON UPDATE CASCADE ON DELETE CASCADE,
        FOREIGN KEY (respuestas_id) REFERENCES respuesta(id) ON UPDATE CASCADE ON DELETE CASCADE

) ENGINE=InnoDB;