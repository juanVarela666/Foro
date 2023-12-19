package com.varela.foro.controller;

import com.varela.foro.modelo.curso.DatosCurso;
import com.varela.foro.modelo.topico.*;
import com.varela.foro.modelo.usuario.DatosUsuario;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * TopicoController es un controlador Spring MVC que maneja solicitudes relacionadas con temas en el foro.
 *
 * @RestController Indica que esta clase es un controlador Spring MVC e incluye automáticamente las anotaciones @Controller y @ResponseBody.
 * @RequestMapping("/topico") Define la ruta URI base para el controlador.
 */
@RestController
@RequestMapping("/topico")
public class TopicoController {

    /**
     * El TopicoRepository utilizado para acceder y administrar datos de temas en la base de datos.
     */
    @Autowired
    private TopicoRepository topicoRepository;

    /**
     * Se encarga de la creación de un nuevo topico.
     *
     * @param datosNuevoTopico     Los datos para crear un nuevo topico.
     * @param uriComponentsBuilder Generador para crear URI para el recurso recién creado.
     * @return ResponseEntity Contiene los detalles del tema recién creado en el cuerpo de la respuesta.
     */
    @PostMapping
    public ResponseEntity nuevoTopico(@RequestBody @Valid DatosNuevoTopico datosNuevoTopico, UriComponentsBuilder uriComponentsBuilder) {
        // Verifica datos duplicados (Titulo, Mensaje).
        if (existeTopicoDuplicado(datosNuevoTopico.titulo(), datosNuevoTopico.mensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registro duplicado no permitido");
        }
        // Guardar en la base de datos los datos del nuevo topico.
        Topico topico = topicoRepository.save(new Topico(datosNuevoTopico));
        var datosIDTopico = new DatosListadoTopicoID(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre(), topico.getCurso().getCategoria());
        URI url = uriComponentsBuilder.path("/topico/{id}").buildAndExpand(topico.getId()).toUri();
        return ResponseEntity.created(url).body(datosIDTopico);
    }

    /**
     * Método para comprobar si ya existe un topico con el mismo título y mensaje.
     */
    private boolean existeTopicoDuplicado(String titulo, String mensaje) {
        // Consultamos el repositorio para buscar tópicos con el mismo título y mensaje
        List<Topico> topicos = topicoRepository.findByTituloAndMensaje(titulo, mensaje);
        return !topicos.isEmpty();
    }

    /**
     * Maneja el listado de topicos con paginación.
     *
     * @param paginacion Información de paginación.
     * @return ResponseEntity Contiene una página de detalles del topico en el cuerpo de la respuesta.
     */
    @GetMapping
    public ResponseEntity<Page<DatosListadoTopico>> listarTopicos(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Topico> paginaTopicos = topicoRepository.findAll(paginacion);
        Page<DatosListadoTopico> paginaDatosTopico = paginaTopicos.map(topico -> {
            String nombreAutor = (topico.getAutor() != null) ? topico.getAutor().getNombre() : null;
            return new DatosListadoTopico(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                    topico.getStatus(), nombreAutor, topico.getCurso().getNombre(), topico.getCurso().getCategoria()
            );
        });
        return ResponseEntity.ok(paginaDatosTopico);
    }

    /**
     * Maneja el listado de un topico en especifico por su ID.
     *
     * @param id ID del topico.
     * @return ResponseEntity Contiene los detalles del tema solicitado en el cuerpo de la respuesta.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoTopicoID> listarTopicoPorId(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = optionalTopico.get();
        var datosIDTopico = new DatosListadoTopicoID(topico.getId(), topico.getTitulo(), topico.getMensaje(), topico.getFechaCreacion(),
                topico.getStatus(), topico.getAutor().getNombre(), topico.getCurso().getNombre(), topico.getCurso().getCategoria());
        return ResponseEntity.ok(datosIDTopico);
    }

    /**
     * Maneja la actualización de un tema por su ID.
     *
     * @param id                    El ID del tema que se actualizará.
     * @param datosActualizarTopico Los datos actualizados para el tema.
     * @return ResponseEntity Contiene los detalles actualizados del tema en el cuerpo de la respuesta.
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarTopico(@PathVariable Long id, @RequestBody @Valid DatosActualizarTopico datosActualizarTopico) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = optionalTopico.get();
        if (existeTopicoDuplicado(datosActualizarTopico.titulo(), datosActualizarTopico.mensaje())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Actualizacion duplicada no permitida");
        }
        topico.actualizarTopico(datosActualizarTopico);
        return ResponseEntity.ok(new DatosActualizarTopico(topico.getTitulo(), topico.getMensaje(),
                new DatosUsuario(topico.getAutor().getNombre(), topico.getAutor().getEmail(), topico.getAutor().getContrasena()),
                new DatosCurso(topico.getCurso().getNombre(), topico.getCurso().getCategoria())));
    }

    /**
     * Método para comprobar si ya existe un tema actualizado con el mismo título y mensaje.
     *
     * @param titulo  El título del tema actualizado.
     * @param mensaje El mensaje del tema actualizado.
     * @return Es verdadero si existe un tema actualizado duplicado; falso en caso contrario.
     */
    private boolean existeTopicoActualizadoDuplicado(String titulo, String mensaje) {
        List<Topico> topicos = topicoRepository.findByTituloAndMensaje(titulo, mensaje);
        return !topicos.isEmpty();
    }

    /**
     * Maneja la eliminación de un tema por su ID.
     *
     * @param id El ID del tema que se va a eliminar.
     * @return ResponseEntity Indicando el éxito de la eliminación.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarTopico(@PathVariable Long id) {
        Optional<Topico> optionalTopico = topicoRepository.findById(id);
        if (optionalTopico.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Topico topico = optionalTopico.get();
        topicoRepository.delete(topico);
        return ResponseEntity.noContent().build();
    }
}

