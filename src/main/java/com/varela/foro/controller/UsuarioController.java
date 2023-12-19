package com.varela.foro.controller;

import com.varela.foro.modelo.usuario.*;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

/**
 * UsuarioController es un controlador Spring MVC que maneja solicitudes relacionadas con las operaciones del usuario.
 *
 * @RestController Indica que esta clase es un controlador Spring MVC e incluye automáticamente las anotaciones @Controller y @ResponseBody.
 * @RequestMapping("/usuario") Define la ruta URI base para el controlador.
 */
@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    /**
     * El UsuarioRepository utilizado para acceder y administrar los datos del usuario en la base de datos.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * BCryptPasswordEncoder se utiliza para codificar y decodificar contraseñas de usuario.
     */
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    /**
     * Maneja la creación de un nuevo usuario.
     *
     * @param datosUsuario         Los datos para crear un nuevo usuario.
     * @param uriComponentsBuilder Generador para crear URI para el recurso recién creado.
     * @return ResponseEntity Contiene los detalles del usuario recién creado en el cuerpo de la respuesta.
     */
    @PostMapping
    public ResponseEntity nuevoUsuario(@RequestBody @Valid DatosUsuario datosUsuario, UriComponentsBuilder uriComponentsBuilder) {
        // Verifica datos duplicados (Nombre, Email).
        if (existeUsuarioDuplicado(datosUsuario.nombre(), datosUsuario.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Registro duplicado no permitido");
        }

        // Guarda en la base de datos
        Usuario usuario = usuarioRepository.save(new Usuario(datosUsuario, passwordEncoder));

        var datosIDUsuario = new DatosListadoUsuarios(usuario.getId(), usuario.getNombre(), usuario.getEmail());
        URI url = uriComponentsBuilder.path("/usuario/{id}").buildAndExpand(usuario.getId()).toUri();
        return ResponseEntity.created(url).body(datosIDUsuario);
    }

    /**
     * Método para comprobar si ya existe un usuario con el mismo nombre y correo electrónico.
     *
     * @param nombre El nombre del usuario.
     * @param email  El correo electrónico del usuario.
     * @return Es verdadero si existe un usuario duplicado; falso en caso contrario.
     */
    private boolean existeUsuarioDuplicado(String nombre, String email) {
        // Consultamos el repositorio para buscar tópicos con el mismo título y mensaje
        List<Usuario> usuarios = usuarioRepository.findByNombreAndEmail(nombre, email);
        return !usuarios.isEmpty();
    }

    /**
     * Maneja el listado de usuarios con paginación.
     *
     * @param paginacion La información de paginación.
     * @return ResponseEntity Contiene una página de detalles del usuario en el cuerpo de la respuesta.
     */
    @GetMapping
    public ResponseEntity<Page<DatosListadoUsuarios>> listarUsuarios(@PageableDefault(size = 10) Pageable paginacion) {
        Page<Usuario> paginaUsuarios = usuarioRepository.findAll(paginacion);
        Page<DatosListadoUsuarios> paginaDatosUsuarios = paginaUsuarios.map(usuario -> {
            return new DatosListadoUsuarios(usuario.getId(), usuario.getNombre(), usuario.getEmail());
        });
        return ResponseEntity.ok(paginaDatosUsuarios);
    }

    /**
     * Maneja la recuperación de un usuario por su ID.
     *
     * @param id ID de usuario.
     * @return ResponseEntity Contiene los detalles del usuario solicitado en el cuerpo de la respuesta.
     */
    @GetMapping("/{id}")
    public ResponseEntity<DatosListadoUsuarios> listarUsuariosPorId(@PathVariable Long id) {
        Optional<Usuario> optionalUsaurio = usuarioRepository.findById(id);
        if (optionalUsaurio.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = optionalUsaurio.get();
        var datosIDUsuario = new DatosListadoUsuarios(usuario.getId(), usuario.getNombre(), usuario.getEmail());
        return ResponseEntity.ok(datosIDUsuario);
    }

    /**
     * Maneja la actualización de un usuario por su ID.
     *
     * @param id           El ID del usuario que se va a actualizar.
     * @param datosNuevoUsuario Los datos actualizados para el usuario.
     * @return ResponseEntity Contiene los detalles actualizados del usuario en el cuerpo de la respuesta.
     */
    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity actualizarUsuario(@PathVariable Long id, @RequestBody @Valid DatosNuevoUsuario datosNuevoUsuario) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = optionalUsuario.get();
        if (existeUsuarioDuplicado(datosNuevoUsuario.nombre(), datosNuevoUsuario.email())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Actualizacion duplicada no permitida");
        }
        usuario.actualizarDatos(datosNuevoUsuario);
        return ResponseEntity.ok(new DatosUsuario(usuario.getNombre(), usuario.getEmail(), usuario.getContrasena()));
    }

    /**
     * Método para comprobar si ya existe un usuario actualizado con el mismo nombre y correo electrónico.
     *
     * @param nombre El nombre del usuario actualizado.
     * @param email  El correo electrónico del usuario actualizado.
     * @return Es verdadero si existe un usuario actualizado duplicado; falso en caso contrario.
     */
    private boolean existeUsuarioActualizadoDuplicado(String nombre, String email) {
        List<Usuario> usuario = usuarioRepository.findByNombreAndEmail(nombre, email);
        return !usuario.isEmpty();
    }

    /**
     * Maneja la eliminación de un usuario por su ID.
     *
     * @param id El ID del usuario que se va a eliminar.
     * @return ResponseEntity Indica el éxito de la eliminación.
     */
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminarUsuario(@PathVariable Long id) {
        Optional<Usuario> optionalUsuario = usuarioRepository.findById(id);
        if (optionalUsuario.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        Usuario usuario = optionalUsuario.get();
        usuarioRepository.delete(usuario);
        return ResponseEntity.noContent().build();
    }
}

