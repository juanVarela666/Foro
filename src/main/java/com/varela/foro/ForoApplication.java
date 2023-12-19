package com.varela.foro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

/**
 * Clase principal de la aplicación Spring Boot para el foro.
 * La anotación @SpringBootApplication indica que esta clase es la clase principal que inicializa la aplicación Spring Boot.
 * La anotación @EntityScan escanea las entidades dentro del paquete "com.varela.foro.modelo".
 */
@SpringBootApplication
@EntityScan("com.varela.foro.modelo")
public class ForoApplication {

    /**
     * Método principal que inicia la aplicación Spring Boot.
     *
     * @param args Los argumentos de línea de comandos proporcionados al iniciar la aplicación.
     */
    public static void main(String[] args) {
        SpringApplication.run(ForoApplication.class, args);
    }

}
