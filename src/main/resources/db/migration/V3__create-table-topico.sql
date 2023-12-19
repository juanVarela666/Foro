CREATE TABLE IF NOT EXISTS topico (

    id BIGINT NOT NULL AUTO_INCREMENT,
    titulo VARCHAR(255),
    mensaje VARCHAR(255),
    fecha_creacion DATETIME(6),
    status TINYINT CHECK (status BETWEEN 0 AND 3),
    autor_id BIGINT,
    curso_id BIGINT,

    PRIMARY KEY (id),
    FOREIGN KEY (curso_id) REFERENCES curso (id) ON UPDATE CASCADE ON DELETE CASCADE,
    FOREIGN KEY (autor_id) REFERENCES usuario (id) ON UPDATE CASCADE ON DELETE CASCADE

) ENGINE=InnoDB;