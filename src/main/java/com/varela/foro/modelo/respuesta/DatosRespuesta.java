package com.varela.foro.modelo.respuesta;

public record DatosRespuesta(
        String mensaje,
        String topico,
        String fechaCreacion,
        String autor,
        String solucion

) {
}
