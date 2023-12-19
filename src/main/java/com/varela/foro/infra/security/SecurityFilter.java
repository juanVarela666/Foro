package com.varela.foro.infra.security;

import com.varela.foro.modelo.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * Filtro de seguridad para procesar las solicitudes y gestionar la autenticación mediante tokens.
 * Este filtro extrae el token del encabezado de autorización, válida el token y establece la autenticación si es válido.
 */
@Component
public class SecurityFilter extends OncePerRequestFilter {
    @Autowired
    private TokenService tokenService;

    /**
     * El repositorio para gestionar las operaciones relacionadas con los usuarios.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Método principal que realiza el filtrado de la solicitud para procesar la autenticación con token.
     *
     * @param request     La solicitud HTTP.
     * @param response    La respuesta HTTP.
     * @param filterChain Cadena de filtros para continuar con el procesamiento de la solicitud.
     * @throws ServletException Sí ocurre una excepción relacionada con el servlet.
     * @throws IOException      Sí ocurre una excepción de entrada/salida.
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // Obtener el token del encabezado
        var authHeader = request.getHeader("Authorization");
        if (authHeader != null) {
            var token = authHeader.replace("Bearer", "");
            var email = tokenService.getSubject(token); // Extraer el nombre de usuario
            System.out.println("tokenservice: " + email);
            if (email != null) {
                //Token valido
                var usuario = usuarioRepository.findByEmail(email);
                var authentication = new UsernamePasswordAuthenticationToken(email, null, usuario.getAuthorities()); //Forzamos el inicio de secion
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
