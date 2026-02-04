package com.gustavo.api_indicadores_economicos_colombia.dto;

import java.time.LocalDate;

public record EstadisticasResponse(
        long totalIndicadores,
        Double valorPromedio,
        Double valorMaximo,
        Double valorMinimo,
        LocalDate fechaRegistroMasReciente,
        LocalDate fechaRegistroMasAntigua
) {
}
