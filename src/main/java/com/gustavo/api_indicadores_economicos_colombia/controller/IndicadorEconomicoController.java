package com.gustavo.api_indicadores_economicos_colombia.controller;

import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoRequest;
import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoResponse;
import com.gustavo.api_indicadores_economicos_colombia.model.IndicadorEconomico;
import com.gustavo.api_indicadores_economicos_colombia.service.IndicadorEconomicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/indicadores")
public class IndicadorEconomicoController {

    @Autowired
    private IndicadorEconomicoService indicadorEconomicoService;

    @GetMapping
    public List<IndicadorEconomicoResponse> listar(){
        return indicadorEconomicoService.listarTodos();
    }

    @PostMapping
    public IndicadorEconomicoResponse guardar(
            @Valid @RequestBody IndicadorEconomicoRequest indicadorEconomicoRequest
            ) {
        return indicadorEconomicoService.guardar(indicadorEconomicoRequest);
    }

}
