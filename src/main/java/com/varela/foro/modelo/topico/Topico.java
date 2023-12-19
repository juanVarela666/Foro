package com.varela.foro.modelo.topico;

import com.varela.foro.modelo.StatusTopico;
import com.varela.foro.modelo.curso.Curso;
import com.varela.foro.modelo.respuesta.Respuesta;
import com.varela.foro.modelo.usuario.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Representa un tópico en el foro, que puede contener mensajes y respuestas de usuarios.
 * Esta clase está mapeada a la tabla "topico" en la base de datos.
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
@Table(name = "topico")
@Entity(name = "Topico")
public class Topico {
    @Id
    @Column(insertable = false, updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechaCreacion = LocalDateTime.now();
    private StatusTopico status = StatusTopico.NO_RESPONDIDO;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "autor_id")
    private Usuario autor;
    @ManyToOne(cascade = CascadeType.ALL)
    private Curso curso;
    @OneToMany
    private List<Respuesta> respuestas = new ArrayList<>();

    /**
     * Construye un objeto Topico a partir de datos nuevos proporcionados.
     *
     * @param datosNuevoTopico Los datos necesarios para crear un nuevo tópico.
     */
    public Topico(DatosNuevoTopico datosNuevoTopico) {
        this.titulo = datosNuevoTopico.titulo();
        this.mensaje = datosNuevoTopico.mensaje();
        this.autor = new Usuario(datosNuevoTopico.autor());
        this.curso = new Curso(datosNuevoTopico.curso());
    }

    /**
     * Actualiza los atributos del tópico con los datos proporcionados.
     *
     * @param datosActualizarTopico Los datos necesarios para actualizar el tópico.
     */
    public void actualizarTopico(DatosActualizarTopico datosActualizarTopico) {
        this.titulo = datosActualizarTopico.titulo();
        this.mensaje = datosActualizarTopico.mensaje();
        this.autor = new Usuario(datosActualizarTopico.autor());
        this.curso = new Curso(datosActualizarTopico.curso());
    }
}
