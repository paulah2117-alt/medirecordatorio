package com.etitc.medirecordatorio.model;

public class Medicamento {
    private Long id;
    private String nombre;
    private String dosis;
    private String hora;
    private String frecuencia;
    private String efectosSecundarios;
    private String interaccionesNoPermitidas;

    // Constructor vacío necesario para Spring
    public Medicamento() {}

    // Constructor con parámetros
    public Medicamento(Long id, String nombre, String dosis, String hora, String frecuencia, String efectosSecundarios, String interaccionesNoPermitidas) {
        this.id = id;
        this.nombre = nombre;
        this.dosis = dosis;
        this.hora = hora;
        this.frecuencia = frecuencia;
        this.efectosSecundarios = efectosSecundarios;
        this.interaccionesNoPermitidas = interaccionesNoPermitidas;
    }

    // Métodos Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public String getEfectosSecundarios() { return efectosSecundarios; }
    public void setEfectosSecundarios(String efectosSecundarios) { this.efectosSecundarios = efectosSecundarios; }

    public String getInteraccionesNoPermitidas() { return interaccionesNoPermitidas; }
    public void setInteraccionesNoPermitidas(String interaccionesNoPermitidas) { this.interaccionesNoPermitidas = interaccionesNoPermitidas; }
}