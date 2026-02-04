package com.gustavo.api_indicadores_economicos_colombia.service;

import com.gustavo.api_indicadores_economicos_colombia.dto.EstadisticasResponse;
import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoRequest;
import com.gustavo.api_indicadores_economicos_colombia.dto.IndicadorEconomicoResponse;
import com.gustavo.api_indicadores_economicos_colombia.exception.RecursoNoEncontradoException;
import com.gustavo.api_indicadores_economicos_colombia.exception.ReglaNegocioException;
import com.gustavo.api_indicadores_economicos_colombia.model.IndicadorEconomico;
import com.gustavo.api_indicadores_economicos_colombia.respository.IndicadorEconomicoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.DoubleSummaryStatistics;
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

    //obtener indicador por código
    public IndicadorEconomicoResponse obtenerPorCodigo(String codigo) {
        IndicadorEconomico indicador = indicadorEconomicoRepository.findByCodigo(codigo)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException(
                                "No existe indicador con código: " + codigo
                        )
                );

        return mapearResponse(indicador);
    }

    //eliminar
    public void eliminar(Long id) {
        if (!indicadorEconomicoRepository.existsById(id)) {
            throw new RecursoNoEncontradoException("Indicador no encontrado con id: " + id);
        }
        indicadorEconomicoRepository.deleteById(id);
    }

    //actualizar
    public IndicadorEconomicoResponse actualizar(Long id, IndicadorEconomicoRequest request) {
        // Buscar el indicador existente
        IndicadorEconomico indicador = indicadorEconomicoRepository.findById(id)
                .orElseThrow(() ->
                        new RecursoNoEncontradoException("Indicador no encontrado con id: " + id)
                );

        // Verificar si el código se está cambiando y si ya existe
        if (!indicador.getCodigo().equals(request.codigo()) &&
                indicadorEconomicoRepository.existsByCodigo(request.codigo())) {
            throw new ReglaNegocioException("Ya existe un indicador con código: " + request.codigo());
        }

        // Actualizar datos
        indicador.setNombre(request.nombre());
        indicador.setCodigo(request.codigo());
        indicador.setValor(request.valor());

        return mapearResponse(indicadorEconomicoRepository.save(indicador));
    }

    //buscar por nombre
    public List<IndicadorEconomicoResponse> buscarPorNombre(String nombre) {
        return indicadorEconomicoRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(this::mapearResponse)
                .toList();
    }

    //estadisticas
    public EstadisticasResponse obtenerEstadisticas() {
        List<IndicadorEconomico> todos = indicadorEconomicoRepository.findAll();

        if (todos.isEmpty()) {
            return new EstadisticasResponse(0, 0.0, 0.0, 0.0, null, null);
        }

        DoubleSummaryStatistics stats = todos.stream()
                .mapToDouble(IndicadorEconomico::getValor)
                .summaryStatistics();

        LocalDate fechaMasReciente = todos.stream()
                .map(IndicadorEconomico::getFechaRegistro)
                .max(LocalDate::compareTo)
                .orElse(null);

        LocalDate fechaMasAntigua = todos.stream()
                .map(IndicadorEconomico::getFechaRegistro)
                .min(LocalDate::compareTo)
                .orElse(null);

        return new EstadisticasResponse(
                stats.getCount(),
                stats.getAverage(),
                stats.getMax(),
                stats.getMin(),
                fechaMasReciente,
                fechaMasAntigua
        );
    }

    //Mapear
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
