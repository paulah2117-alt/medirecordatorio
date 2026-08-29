package com.etitc.medirecordatorio.model;

public class Medicamento {
    private Long id;
    private String nombre;
    private String categoria;
    private String paraQueSirve;
    private String dosis;
    private String hora;
    private String frecuencia;
    private String efectosSecundarios;
    private String interaccionesNoPermitidas;

    public Medicamento() {}

    public Medicamento(Long id, String nombre, String categoria, String paraQueSirve, String dosis, String hora, String frecuencia, String efectosSecundarios, String interaccionesNoPermitidas) {
        this.id = id;
        this.nombre = nombre;
        this.categoria = categoria;
        this.paraQueSirve = paraQueSirve;
        this.dosis = dosis;
        this.hora = hora;
        this.frecuencia = frecuencia;
        this.efectosSecundarios = efectosSecundarios;
        this.interaccionesNoPermitidas = interaccionesNoPermitidas;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }

    public String getParaQueSirve() { return paraQueSirve; }
    public void setParaQueSirve(String paraQueSirve) { this.paraQueSirve = paraQueSirve; }

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