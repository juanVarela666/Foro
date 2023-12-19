package com.varela.foro.modelo.topico;


import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interfaz que proporciona métodos de acceso a la base de datos para la entidad Topico.
 * Utiliza Spring Data JPA y extiende JpaRepository para realizar operaciones CRUD.
 */
public interface TopicoRepository extends JpaRepository<Topico, Long> {
    List<Topico> findByTituloAndMensaje(String titulo, String mensaje);
}
