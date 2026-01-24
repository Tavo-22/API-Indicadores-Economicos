package com.gustavo.api_indicadores_economicos_colombia.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "indicadores_economicos")
public class IndicadorEconomico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false, unique = true)
    private String codigo;

    @Column(nullable = false)
    private Double valor;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    public IndicadorEconomico() {
    }

    public IndicadorEconomico(String nombre, String codigo, Double valor, LocalDate fechaRegistro) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.valor = valor;
        this.fechaRegistro = fechaRegistro;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
