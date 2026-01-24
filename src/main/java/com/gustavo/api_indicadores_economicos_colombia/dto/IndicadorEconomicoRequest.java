package com.gustavo.api_indicadores_economicos_colombia.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record IndicadorEconomicoRequest(
        @NotBlank(message = "El nombres es obligatorio")
        String nombre,

        @NotBlank(message = "El codigo es obligatorio")
        String codigo,

        @NotNull(message = "El valor no puede ser nulo")
        @Positive(message = "El valor debe ser mayor que cero")
        Double valor

) {
}
