package com.varela.foro.modelo.usuario;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa los datos necesarios para la creación de un nuevo usuario.
 * Esta clase es un record de Java, lo que significa que es inmutable y contiene
 * automáticamente métodos como equals, hashCode y toString.
 */
public record DatosNuevoUsuario(
        @NotBlank(message = "El nombre de usuario no debe ser nulo")
        String nombre,
        @NotBlank(message = "El email no debe ser nulo")
        String email,
        @NotBlank(message = "La contraseña no debe ser nula")
        String contrasena) {
}
