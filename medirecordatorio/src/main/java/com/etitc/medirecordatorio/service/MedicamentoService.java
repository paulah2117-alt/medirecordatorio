package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.model.Medicamento;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicamentoService {

    public List<Medicamento> listarMedicamentos() {
        return List.of(
                new Medicamento(
                        1L,
                        "Paracetamol",
                        "500 mg",
                        "08:00 AM",
                        "Cada 8 horas",
                        "Náuseas, mareos, malestar estomacal",
                        "Alcohol, Anticoagulantes"
                ),
                new Medicamento(
                        2L,
                        "Ibuprofeno",
                        "400 mg",
                        "02:00 PM",
                        "Cada 12 horas",
                        "Acidez, dolor estomacal, somnolencia",
                        "Aspirina, Antihipertensivos"
                )
        );
    }
}