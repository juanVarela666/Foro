package com.varela.foro.modelo.respuesta;

import com.varela.foro.modelo.topico.Topico;
import com.varela.foro.modelo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Representa una respuesta asociada a un tópico en un foro.
 * Esta clase es una entidad JPA, lo que significa que se puede almacenar en una base de datos
 * y está vinculada a una tabla en la base de datos.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Entity
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    @ManyToOne
    private Topico topico;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    @ManyToOne
    private Usuario autor;
    private Boolean solucion = false;
}
