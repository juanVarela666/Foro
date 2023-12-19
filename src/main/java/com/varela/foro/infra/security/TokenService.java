package com.varela.foro.infra.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.varela.foro.modelo.usuario.Usuario;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * Servicio para la generación y verificación de tokens JWT.
 * Este servicio proporciona métodos para generar tokens JWT basados en la información del usuario
 * y para verificar la validez de un token existente.
 */
@Service
public class TokenService {

    /**
     * Clave secreta utilizada para firmar y verificar los tokens JWT.
     */
    @Value("${api.security.secret}")
    private String apiSecret;

    /**
     * Genera un token JWT para el usuario especificado.
     *
     * @param usuario El usuario para el cual se genera el token.
     * @return El token JWT generado.
     * @throws RuntimeException Sí ocurre un error durante la generación del token.
     */
    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            return JWT.create()
                    .withIssuer("voll med")
                    .withSubject(usuario.getEmail())
                    .withClaim("id", usuario.getId())
                    .withExpiresAt(generarFechaExpiracion())
                    .sign(algorithm);
        } catch (JWTCreationException exception) {
            throw new RuntimeException();
        }
    }

    /**
     * Obtiene el sujeto (subject) del token JWT, verificando su validez.
     *
     * @param token El token JWT a verificar.
     * @return El sujeto del token.
     * @throws RuntimeException Si el token es nulo, inválido o si ocurre un error durante la verificación.
     */
    public String getSubject(String token) {
        if (token == null) {
            throw new RuntimeException();
        }
        DecodedJWT verifier = null;
        try {
            Algorithm algorithm = Algorithm.HMAC256(apiSecret);
            verifier = JWT.require(algorithm)
                    .withIssuer("voll med")
                    .build()
                    .verify(token);
            verifier.getSubject();
        } catch (JWTVerificationException exception) {
            System.out.println(exception.toString());
        }
        if (verifier.getSubject() == null) {
            throw new RuntimeException("Verifier invalido");
        }
        return verifier.getSubject();
    }

    /**
     * Genera la fecha de expiración para el token (24 horas a partir de ahora).
     *
     * @return La fecha de expiración instantánea.
     */
    private Instant generarFechaExpiracion() {
        return LocalDateTime.now().plusHours(24).toInstant(ZoneOffset.of("-05:00"));
    }
}

