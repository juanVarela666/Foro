package com.varela.foro.modelo.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.List;

/**
 * Interfaz que proporciona métodos de acceso a la base de datos para la entidad Usuario.
 * Utiliza Spring Data JPA y extiende JpaRepository para realizar operaciones CRUD.
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    /**
     * Busca y devuelve una lista de usuarios que coinciden con el nombre y el email proporcionados.
     *
     * @param nombre El nombre del usuario a buscar.
     * @param email  El email del usuario a buscar.
     * @return Una lista de usuarios que coinciden con los parámetros de búsqueda.
     */
    List<Usuario> findByNombreAndEmail(String nombre, String email);

    /**
     * Busca y devuelve un UserDetails asociado al email proporcionado.
     *
     * @param email El email del usuario para recuperar UserDetails.
     * @return UserDetails asociado al email proporcionado.
     */
    UserDetails findByEmail(String email);
}
