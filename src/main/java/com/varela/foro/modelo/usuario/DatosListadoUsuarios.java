package com.varela.foro.modelo.usuario;

/**
 * Representa los datos necesarios para listar los usuarios del foro.
 * Esta clase es un record de Java, lo que significa que es inmutable y contiene
 * automáticamente métodos como equals, hashCode y toString.
 */
public record DatosListadoUsuarios(
        Long id,
        String nombre,
        String email) {
}
