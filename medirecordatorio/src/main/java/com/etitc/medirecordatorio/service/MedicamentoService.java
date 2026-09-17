package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.dto.MedicamentoForm;
import com.etitc.medirecordatorio.model.Medicamento;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MedicamentoService {

    // Lista modificable (ArrayList) para permitir guardar nuevos registros desde el formulario
    private final List<Medicamento> listaMedicamentos = new ArrayList<>(List.of(
            new Medicamento(1L, "Losartán", "Hipertensión", "Reducir la presión arterial alta", "50 mg", "07:00 AM", "Cada 24 horas", "Mareos, fatiga", "Antiinflamatorios (Ibuprofeno)"),
            new Medicamento(2L, "Metformina", "Diabetes", "Controlar niveles de glucosa", "850 mg", "12:30 PM", "Cada 12 horas", "Náuseas, diarrea", "Alcohol"),
            new Medicamento(3L, "Carbamazepina", "Anticonvulsivos", "Prevenir crisis epilépticas", "200 mg", "08:00 AM", "Cada 12 horas", "Somnolencia, mareos", "Tramadol, Inhibidores MAO"),
            new Medicamento(4L, "Ibuprofeno", "Antiinflamatorios", "Disminuir la inflamación y fiebre", "400 mg", "02:00 PM", "Cada 8 horas", "Acidez, dolor estomacal", "Aspirina, Anticoagulantes"),
            new Medicamento(5L, "Paracetamol (Acetaminofén)", "Analgésicos", "Aliviar dolores moderados", "500 mg", "06:00 AM", "Cada 8 horas", "Daño hepático en exceso", "Alcohol, Anticoagulantes")
    ));

    public List<Medicamento> listarMedicamentos() {
        return listaMedicamentos;
    }

    // Método de la Semana 7 para agregar medicamentos capturados desde el formulario
    public void guardarMedicamento(MedicamentoForm form) {
        Long nuevoId = (long) (listaMedicamentos.size() + 1);
        String frecuenciaTexto = "Cada " + form.getFrecuenciaHoras() + " horas";

        Medicamento nuevo = new Medicamento(
                nuevoId,
                form.getNombre(),
                form.getCategoria(),
                form.getParaQueSirve(),
                form.getDosis(),
                form.getHora(),
                frecuenciaTexto,
                form.getEfectosSecundarios(),
                form.getInteraccionesNoPermitidas()
        );

        listaMedicamentos.add(nuevo);
    }
}