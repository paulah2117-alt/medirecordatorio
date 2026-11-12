package com.etitc.medirecordatorio.dto;

public class MedicamentoResponseDTO {

    private Long id;
    private String nombre;
    private String dosis;
    private String hora;
    private String frecuencia;
    private Long pacienteId;
    private String pacienteNombre;
    private Long medicoId;
    private String medicoNombre;

    public MedicamentoResponseDTO() {}

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

    public Long getPacienteId() { return pacienteId; }
    public void setPacienteId(Long pacienteId) { this.pacienteId = pacienteId; }

    public String getPacienteNombre() { return pacienteNombre; }
    public void setPacienteNombre(String pacienteNombre) { this.pacienteNombre = pacienteNombre; }

    public Long getMedicoId() { return medicoId; }
    public void setMedicoId(Long medicoId) { this.medicoId = medicoId; }

    public String getMedicoNombre() { return medicoNombre; }
    public void setMedicoNombre(String medicoNombre) { this.medicoNombre = medicoNombre; }
}