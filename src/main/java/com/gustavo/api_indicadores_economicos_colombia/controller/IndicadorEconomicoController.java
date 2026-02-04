package com.gustavo.api_indicadores_economicos_colombia.controller;

import com.gustavo.api_indicadores_economicos_colombia.dto.EstadisticasResponse;
import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoRequest;
import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoResponse;
import com.gustavo.api_indicadores_economicos_colombia.model.IndicadorEconomico;
import com.gustavo.api_indicadores_economicos_colombia.service.IndicadorEconomicoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/indicadores")
public class IndicadorEconomicoController {

    @Autowired
    private IndicadorEconomicoService indicadorEconomicoService;

    @GetMapping
    public ResponseEntity<List<IndicadorEconomicoResponse>> listar(){
        return ResponseEntity.ok(indicadorEconomicoService.listarTodos());
    }

    @PostMapping
    public ResponseEntity<IndicadorEconomicoResponse> guardar(
            @Valid @RequestBody IndicadorEconomicoRequest indicadorEconomicoRequest
            ) {
        IndicadorEconomicoResponse response = indicadorEconomicoService.guardar(indicadorEconomicoRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    //buscar por codigo
    @GetMapping("/codigo/{codigo}")
    public ResponseEntity<IndicadorEconomicoResponse> obtenerPorCodigo(
            @PathVariable String codigo
    ) {
        return ResponseEntity.ok(indicadorEconomicoService.obtenerPorCodigo(codigo));
    }

    //eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        indicadorEconomicoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    //actulizar
    @PutMapping("/{id}")
    public ResponseEntity<IndicadorEconomicoResponse> actualizar(
            @PathVariable Long id,
            @Valid @RequestBody IndicadorEconomicoRequest request
    ) {
        return ResponseEntity.ok(indicadorEconomicoService.actualizar(id, request));
    }

    //buscar por nombre
    @GetMapping("/buscar")
    public ResponseEntity<List<IndicadorEconomicoResponse>> buscarPorNombre(
            @RequestParam(required = false) String nombre
    ) {
        List<IndicadorEconomicoResponse> response;
        if (nombre != null && !nombre.trim().isEmpty()) {
            response = indicadorEconomicoService.buscarPorNombre(nombre);
        } else {
            response = indicadorEconomicoService.listarTodos();
        }
        return ResponseEntity.ok(response);
    }

    //estadisticas
    @GetMapping("/estadisticas")
    public ResponseEntity<EstadisticasResponse> obtenerEstadisticas() {
        return ResponseEntity.ok(indicadorEconomicoService.obtenerEstadisticas());
    }
}
