package com.varela.foro.modelo.curso;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa los datos necesarios para la creación o actualización de un curso.
 * Esta clase es un record de Java, lo que significa que es inmutable y contiene
 * automáticamente métodos como equals, hashCode y toString.
 */
public record DatosCurso(
        @NotBlank(message = "El nombre del curso no debe ser nulo")
        String nombre,
        @NotBlank(message = "La categoria del curso no debe ser nula")
        String categoria) {
}
