package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.dto.MedicamentoForm;
import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.repository.MedicamentoRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MedicamentoService {

    private final MedicamentoRepository medicamentoRepository;

    public MedicamentoService(MedicamentoRepository medicamentoRepository) {
        this.medicamentoRepository = medicamentoRepository;
    }

    // Inicializa datos de prueba en la BD al arrancar la app solo si está vacía
    @PostConstruct
    public void cargarDatosIniciales() {
        if (medicamentoRepository.count() == 0) {
            medicamentoRepository.save(new Medicamento(null, "Losartán", "Hipertensión", "Reducir la presión arterial alta", "50 mg", "07:00 AM", "Cada 24 horas", "Mareos, fatiga", "Antiinflamatorios (Ibuprofeno)"));
            medicamentoRepository.save(new Medicamento(null, "Metformina", "Diabetes", "Controlar niveles de glucosa", "850 mg", "12:30 PM", "Cada 12 horas", "Náuseas, diarrea", "Alcohol"));
            medicamentoRepository.save(new Medicamento(null, "Carbamazepina", "Anticonvulsivos", "Prevenir crisis epilépticas", "200 mg", "08:00 AM", "Cada 12 horas", "Somnolencia, mareos", "Tramadol, Inhibidores MAO"));
            medicamentoRepository.save(new Medicamento(null, "Ibuprofeno", "Antiinflamatorios", "Disminuir la inflamación y fiebre", "400 mg", "02:00 PM", "Cada 8 horas", "Acidez, dolor estomacal", "Aspirina, Anticoagulantes"));
            medicamentoRepository.save(new Medicamento(null, "Paracetamol (Acetaminofén)", "Analgésicos", "Aliviar dolores moderados", "500 mg", "06:00 AM", "Cada 8 horas", "Daño hepático en exceso", "Alcohol, Anticoagulantes"));
        }
    }

    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public void guardarMedicamento(MedicamentoForm form) {
        String frecuenciaTexto = "Cada " + form.getFrecuenciaHoras() + " horas";

        Medicamento nuevo = new Medicamento(
                null,
                form.getNombre(),
                form.getCategoria(),
                form.getParaQueSirve(),
                form.getDosis(),
                form.getHora(),
                frecuenciaTexto,
                form.getEfectosSecundarios(),
                form.getInteraccionesNoPermitidas()
        );

        medicamentoRepository.save(nuevo);
    }
}