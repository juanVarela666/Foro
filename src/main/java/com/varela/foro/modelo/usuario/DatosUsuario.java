package com.varela.foro.modelo.usuario;

import jakarta.validation.constraints.NotBlank;

/**
 * Representa los datos del usuario.
 * Esta clase es un record de Java, lo que significa que es inmutable y contiene
 * automáticamente métodos como equals, hashCode y toString.
 */
public record DatosUsuario(
        @NotBlank(message = "El nombre de usuario no debe ser nulo")
        String nombre,
        String email,
        String contrasena) {

}
