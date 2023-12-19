package com.varela.foro.controller;

import com.varela.foro.infra.security.DatosJWTToken;
import com.varela.foro.infra.security.TokenService;
import jakarta.validation.Valid;
import com.varela.foro.modelo.usuario.DatosUsuario;
import com.varela.foro.modelo.usuario.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * AuthenticationController maneja las solicitudes de autenticación de usuarios.
 * Es responsable de autenticar a los usuarios y generar JSON Web Tokens (JWT) para los usuarios autenticados.
 *
 * @RestController Indica que esta clase es un controlador Spring MVC e incluye automáticamente las anotaciones @Controller y @ResponseBody.
 * @RequestMapping("/login") Define la ruta URI base para el controlador.
 */
@RestController
@RequestMapping("/login")
public class AuthenticationController {

    /**
     * El AuthenticationManager responsable de autenticar a los usuarios.
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * El TokenService responsable de generar tokens web JSON (JWT).
     */
    @Autowired
    private TokenService tokenService;

    /**
     * Construye una instancia de AuthenticationController con el AuthenticationManager y el TokenService especificados.
     */
    public AuthenticationController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    /**
     * Maneja la autenticación de un usuario utilizando las credenciales de usuario proporcionadas.
     * Tras una autenticación exitosa, genera un token JWT para el usuario autenticado.
     *
     * @param datosAutenticacionUsuario Las credenciales de usuario para la autenticación.
     * @return ResponseEntity Contiene el token JWT en el cuerpo de la respuesta.
     */
    @PostMapping
    public ResponseEntity<DatosJWTToken> autenticarUsuario(@RequestBody @Valid DatosUsuario datosAutenticacionUsuario) {
        //Crea un token de autenticación con las credenciales de usuario proporcionadas
        Authentication authToken = new UsernamePasswordAuthenticationToken(datosAutenticacionUsuario.email(),
                datosAutenticacionUsuario.contrasena());
        //Autenticar al usuario mediante AuthenticationManager
        var usuarioAutenticado = authenticationManager.authenticate(authToken);
        //Generar un token JWT para el usuario autenticado
        var JWTtoken = tokenService.generarToken((Usuario) usuarioAutenticado.getPrincipal());
        //Devuelve el token JWT en el cuerpo de la respuesta.
        return ResponseEntity.ok(new DatosJWTToken(JWTtoken));
    }
}
