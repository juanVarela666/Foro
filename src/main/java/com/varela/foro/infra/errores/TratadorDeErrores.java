package com.varela.foro.infra.errores;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * TratadorDeErrores es una clase de controlador de excepciones global para manejar excepciones específicas en la aplicación.
 *
 * @RestControllerAdvice Indica que esta clase está diseñada para manejar excepciones globalmente para todos los controladores.
 */
@RestControllerAdvice
public class TratadorDeErrores {

    /**
     * Maneja EntityNotFoundException devolviendo una ResponseEntity con estado 404 No encontrado.
     *
     * @return ResponseEntity con estado 404 No encontrado.
     */
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity tratarError404() {
        return ResponseEntity.notFound().build();
    }

    /**
     * Maneja MethodArgumentNotValidException devolviendo una ResponseEntity con estado 400 Bad Request, que contiene una lista de errores de validación.
     *
     * @param e Se lanza la excepción MethodArgumentNotValidException.
     * @return ResponseEntity con estado 400 Solicitud incorrecta y una lista de errores de validación en el cuerpo de la respuesta.
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity tratarError400(MethodArgumentNotValidException e) {
        var errores = e.getFieldErrors().stream().map(DatosErrorValidacion::new).toList();
        return ResponseEntity.badRequest().body(errores);
    }

    /**
     * Clase de datos que representa detalles del error de validación.
     */
    private record DatosErrorValidacion(String campo, String error) {
        /**
         * Construye DatosErrorValidacion a partir de un FieldError.
         *
         * @param error El FieldError que contiene detalles del error de validación.
         */
        public DatosErrorValidacion(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}
