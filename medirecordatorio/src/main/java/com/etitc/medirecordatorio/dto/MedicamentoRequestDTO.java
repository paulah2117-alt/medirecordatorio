package com.etitc.medirecordatorio.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class MedicamentoRequestDTO {

    @NotBlank(message = "El nombre del medicamento es obligatorio")
    private String nombre;

    @NotBlank(message = "La dosis es obligatoria")
    private String dosis;

    @NotBlank(message = "La hora es obligatoria")
    private String hora;

    @NotBlank(message = "La frecuencia es obligatoria")
    private String frecuencia;

    @NotNull(message = "El ID del paciente es obligatorio")
    private Long pacienteId;

    private Long medicoId;

    public MedicamentoRequestDTO() {}

    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getDosis() { return dosis; }
    public void setDosis(String dosis) { this.dosis = dosis; }

    public String getHora() { return hora; }
    public void setHora(String hora) { this.hora = hora; }

    public String getFrecuencia() { return frecuencia; }
    public void setFrecuencia(String frecuencia) { this.frecuencia = frecuencia; }

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }
}