package com.varela.foro.modelo.topico;

import com.varela.foro.modelo.curso.DatosCurso;
import com.varela.foro.modelo.usuario.DatosUsuario;
import com.varela.foro.modelo.usuario.Usuario;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

/**
 * Representa los datos necesarios para insertar un nuevo tópico al foro.
 * Esta clase es un "record" en Java, lo que significa que es una clase inmutable
 * que tiene campos inmutables y métodos generados automáticamente, como equals() y hashCode().
 */
public record DatosNuevoTopico(
        @NotBlank(message = "El titulo no debe ser nulo")
        String titulo,
        @NotBlank(message = "El mensaje no debe ser nulo")
        String mensaje,
        @Valid
        DatosUsuario autor,
        @Valid
        DatosCurso curso) {

    public Usuario getAutor() {
        return new Usuario(autor);
    }
}
