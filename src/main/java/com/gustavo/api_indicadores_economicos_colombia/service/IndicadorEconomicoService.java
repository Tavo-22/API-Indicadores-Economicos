package com.gustavo.api_indicadores_economicos_colombia.service;

import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoRequest;
import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoResponse;
import com.gustavo.api_indicadores_economicos_colombia.exception.ReglaNegocioException;
import com.gustavo.api_indicadores_economicos_colombia.model.IndicadorEconomico;
import com.gustavo.api_indicadores_economicos_colombia.respository.IndicadorEconomicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class IndicadorEconomicoService {

    @Autowired
    private IndicadorEconomicoRepository indicadorEconomicoRepository;

    //listar
    public List<IndicadorEconomicoResponse> listarTodos(){
        return indicadorEconomicoRepository.findAll()
                .stream()
                .map(this::mapearResponse)
                .toList();
    }

    //guardar
    public IndicadorEconomicoResponse guardar(IndicadorEconomicoRequest indicadorEconomicoRequest) {

        if (indicadorEconomicoRepository.existsByCodigo(indicadorEconomicoRequest.codigo())) {
            throw new ReglaNegocioException(
                    "Ya existe un indicador con el código: " + indicadorEconomicoRequest.codigo()
            );
        }
        IndicadorEconomico indicador = new IndicadorEconomico(
                indicadorEconomicoRequest.nombre(),
                indicadorEconomicoRequest.codigo(),
                indicadorEconomicoRequest.valor(),
                LocalDate.now()
        );
        return mapearResponse(indicadorEconomicoRepository.save(indicador));
    }

    private IndicadorEconomicoResponse mapearResponse(IndicadorEconomico indicadorEconomico){
        return new IndicadorEconomicoResponse(
                indicadorEconomico.getId(),
                indicadorEconomico.getNombre(),
                indicadorEconomico.getCodigo(),
                indicadorEconomico.getValor(),
                indicadorEconomico.getFechaRegistro()
        );
    }
}
