package com.gustavo.api_indicadores_economicos_colombia.dto;

import java.time.LocalDate;

public record IndicadorEconomicoResponse(
        Long id,
        String nombre,
        String codigo,
        Double valor,
        LocalDate fechaRegistro
) {
}
