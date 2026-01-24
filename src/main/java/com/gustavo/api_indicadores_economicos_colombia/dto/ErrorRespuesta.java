package com.gustavo.api_indicadores_economicos_colombia.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record ErrorRespuesta(
        LocalDateTime fecha,
        int status,
        String error,
        String mensaje,
        String path,
        Map<String, String> errores
) {
    public ErrorRespuesta(
            LocalDateTime fecha,
            int status,
            String error,
            String mensaje,
            String path
    ) {
        this(fecha, status, error, mensaje, path, null);
    }
}
