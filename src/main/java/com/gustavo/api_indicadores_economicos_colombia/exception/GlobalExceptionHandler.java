package com.gustavo.api_indicadores_economicos_colombia.exception;

import com.gustavo.api_indicadores_economicos_colombia.dto.ErrorRespuesta;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(RecursoNoEncontradoException.class)
    public ResponseEntity<ErrorRespuesta> manejarNoEncontrado(
            RecursoNoEncontradoException ex,
            HttpServletRequest request
    ) {
        return construirError(
                HttpStatus.NOT_FOUND,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler({
            ReglaNegocioException.class,
            IndicadorEconomicoExistenteException.class
    })
    public ResponseEntity<ErrorRespuesta> manejarReglasNegocio(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        return construirError(
                HttpStatus.BAD_REQUEST,
                ex.getMessage(),
                request.getRequestURI()
        );
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorRespuesta> manejarValidaciones(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        Map<String, String> errores = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));

        return ResponseEntity.badRequest().body(
                new ErrorRespuesta(
                        LocalDateTime.now(),
                        HttpStatus.BAD_REQUEST.value(),
                        HttpStatus.BAD_REQUEST.name(),
                        "Error de validación",
                        request.getRequestURI(),
                        errores
                )
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorRespuesta> manejarJsonInvalido(
            HttpServletRequest request
    ) {
        return construirError(
                HttpStatus.BAD_REQUEST,
                "El JSON enviado es inválido",
                request.getRequestURI()
        );
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorRespuesta> manejarExcepcionGeneral(
            Exception ex,
            HttpServletRequest request
    ) {
        return construirError(
                HttpStatus.INTERNAL_SERVER_ERROR,
                "Ocurrió un error inesperado",
                request.getRequestURI()
        );
    }

    private ResponseEntity<ErrorRespuesta> construirError(
            HttpStatus status,
            String mensaje,
            String path
    ) {
        return ResponseEntity.status(status).body(
                new ErrorRespuesta(
                        LocalDateTime.now(),
                        status.value(),
                        status.name(),
                        mensaje,
                        path
                )
        );
    }
}
