package com.varela.foro.infra.security;

import com.varela.foro.modelo.usuario.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * AuthenticationService Es una clase de servicio que implementa la interfaz UserDetailsService.
 * Es responsable de cargar los detalles del usuario por nombre de usuario durante el proceso de autenticación.
 *
 * @Service Indica que esta clase es un componente de servicio en el contexto de la aplicación Spring
 */
@Service
public class AuthenticationService implements UserDetailsService {

    /**
     * El repositorio para gestionar las operaciones relacionadas con los usuarios.
     */
    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Carga los detalles del usuario por nombre de usuario durante el proceso de autenticación.
     *
     * @param email El nombre de usuario (correo electrónico) del usuario a cargar.
     * @return UserDetails que contiene información del usuario.
     * @throws UsernameNotFoundException Si no se encuentra el usuario con el nombre de usuario proporcionado.
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return usuarioRepository.findByEmail(email);
    }
}
