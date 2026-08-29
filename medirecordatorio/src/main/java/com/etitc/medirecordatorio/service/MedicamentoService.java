package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.model.Medicamento;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class MedicamentoService {

    public List<Medicamento> listarMedicamentos() {
        return List.of(
                // Hipertensos
                new Medicamento(1L, "Losartán", "Hipertensión", "Reducir la presión arterial alta y proteger los riñones", "50 mg", "07:00 AM", "Cada 24 horas", "Mareos, fatiga, congestión nasal", "Antiinflamatorios (Ibuprofeno), Suplementos de potasio"),
                new Medicamento(2L, "Enalapril", "Hipertensión", "Tratar la presión alta e insuficiencia cardíaca", "10 mg", "08:00 AM", "Cada 12 horas", "Tos seca persistente, mareos, dolor de cabeza", "Litio, Diuréticos ahorradores de potasio"),

                // Diabéticos
                new Medicamento(3L, "Metformina", "Diabetes", "Controlar los niveles de glucosa (azúcar) en sangre", "850 mg", "12:30 PM", "Cada 12 horas con las comidas", "Náuseas, diarrea, malestar estomacal inicial", "Alcohol, Contrastes yodados radiológicos"),
                new Medicamento(4L, "Glibenclamida", "Diabetes", "Estimular la producción de insulina en el páncreas", "5 mg", "07:30 AM", "Cada 24 horas antes del desayuno", "Hipoglucemia (bajada de azúcar), aumento de peso", "Alcohol, Betabloqueantes, Antimicóticos"),

                // Convulsionadores (Anticonvulsivos)
                new Medicamento(5L, "Ácido Valproico", "Anticonvulsivos", "Prevenir y controlar crisis epilépticas o convulsiones", "500 mg", "08:00 AM", "Cada 12 horas", "Somnolencia, temblor fino, aumento de peso", "Aspirina, Anticonceptivos orales, Carbamazepina"),
                new Medicamento(6L, "Carbamazepina", "Anticonvulsivos", "Controlar crisis convulsivas y neuralgia del trigémino", "200 mg", "09:00 PM", "Cada 12 horas", "Visión doble, mareos, inestabilidad", "Jugo de toronja, Anticoagulantes, Antidepresivos"),

                // Antidepresivos
                new Medicamento(7L, "Sertralina", "Antidepresivos", "Tratar el trastorno depresivo, ansiedad y pánico", "50 mg", "08:00 AM", "Cada 24 horas", "Insomnio, náuseas, sequedad de boca", "Tramadol, Inhibidores MAO, Antiinflamatorios"),

                // Antiinflamatorios
                new Medicamento(8L, "Ibuprofeno", "Antiinflamatorios", "Disminuir la inflamación, hinchazón y fiebre", "400 mg", "02:00 PM", "Cada 8 horas", "Acidez, dolor estomacal, úlceras", "Aspirina, Anticoagulantes, Antihipertensivos"),

                // Analgésicos
                new Medicamento(9L, "Paracetamol (Acetaminofén)", "Analgésicos", "Aliviar dolores moderados de cabeza, músculos y fiebre", "500 mg", "06:00 AM", "Cada 8 horas", "Daño hepático (en exceso), reacciones cutáneas raramente", "Alcohol, Anticoagulantes (Warfarina)")
        );
    }
}