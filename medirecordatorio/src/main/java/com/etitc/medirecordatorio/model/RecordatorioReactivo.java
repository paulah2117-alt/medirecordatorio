package com.etitc.medirecordatorio.model;

public class RecordatorioReactivo {

    private String medicamento;
    private String dosis;
    private String paciente;
    private boolean activo;

    public RecordatorioReactivo(String medicamento, String dosis, String paciente, boolean activo) {
        this.medicamento = medicamento;
        this.dosis = dosis;
        this.paciente = paciente;
        this.activo = activo;
    }

    public String getMedicamento() { return medicamento; }
    public String getDosis() { return dosis; }
    public String getPaciente() { return paciente; }
    public boolean isActivo() { return activo; }

    @Override
    public String toString() {
        return "RecordatorioReactivo{" +
                "medicamento='" + medicamento + '\'' +
                ", dosis='" + dosis + '\'' +
                ", paciente='" + paciente + '\'' +
                ", activo=" + activo +
                '}';
    }
}