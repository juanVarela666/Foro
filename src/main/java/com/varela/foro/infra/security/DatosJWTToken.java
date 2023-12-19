package com.varela.foro.infra.security;

/**
 * DatosJWTToken es un registro que representa los datos del JSON Web Token (JWT).
 * Este registro contiene la información del token JWT para su uso en autenticación y autorización.
 * Está diseñado como un registro para proporcionar una representación simple e inmutable de los datos del token JWT.
 */
public record DatosJWTToken(String jwTtoken) {
}
