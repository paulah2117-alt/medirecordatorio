package com.etitc.medirecordatorio.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicamentoForm {

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    private String nombre;

    @NotBlank(message = "Debe seleccionar una categoría")
    private String categoria;

    @NotBlank(message = "Debe especificar para qué sirve el medicamento")
    private String paraQueSirve;

    @NotBlank(message = "La dosis es obligatoria (ej: 500 mg)")
    private String dosis;

    @NotBlank(message = "La hora es obligatoria (ej: 08:00 AM)")
    private String hora;

    @NotNull(message = "La frecuencia en horas es obligatoria")
    @Min(value = 1, message = "La frecuencia debe ser de al menos 1 hora")
    private Integer frecuenciaHoras;

    @NotBlank(message = "Los efectos secundarios son obligatorios")
    private String efectosSecundarios;

    @NotBlank(message = "Las interacciones no permitidas son obligatorias")
    private String interaccionesNoPermitidas;

    public MedicamentoForm() {}

    // Getters y Setters
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

    public Integer getFrecuenciaHoras() { return frecuenciaHoras; }
    public void setFrecuenciaHoras(Integer frecuenciaHoras) { this.frecuenciaHoras = frecuenciaHoras; }

    public String getEfectosSecundarios() { return efectosSecundarios; }
    public void setEfectosSecundarios(String efectosSecundarios) { this.efectosSecundarios = efectosSecundarios; }

    public String getInteraccionesNoPermitidas() { return interaccionesNoPermitidas; }
    public void setInteraccionesNoPermitidas(String interaccionesNoPermitidas) { this.interaccionesNoPermitidas = interaccionesNoPermitidas; }
}