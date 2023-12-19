package com.varela.foro.modelo.topico;

import com.varela.foro.modelo.StatusTopico;

import java.time.LocalDateTime;

/**
 * Representa los datos necesarios para listar los tópicos del foro.
 * Esta clase es un "record" en Java, lo que significa que es una clase inmutable
 * que tiene campos inmutables y métodos generados automáticamente, como equals() y hashCode().
 */
public record DatosListadoTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        StatusTopico status,
        String autor,
        String nombreCurso,
        String categoriaCurso) {

}
