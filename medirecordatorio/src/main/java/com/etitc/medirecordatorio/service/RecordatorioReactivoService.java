package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.model.RecordatorioReactivo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Service
public class RecordatorioReactivoService {

    // Fuente reactiva con al menos 4 registros, incluyendo uno inactivo
    public Flux<RecordatorioReactivo> obtenerFlujoRecordatorios() {
        List<RecordatorioReactivo> datos = Arrays.asList(
                new RecordatorioReactivo("Amoxicilina", "500 mg", "Carlos Perez", true),
                new RecordatorioReactivo("Ibuprofeno", "400 mg", "Maria Lopez", true),
                new RecordatorioReactivo("Omeprazol", "20 mg", "Juan Diaz", false), // Inactivo
                new RecordatorioReactivo("Losartan", "50 mg", "Ana Torres", true)
        );
        return Flux.fromIterable(datos);
    }

    // Filtrar activos y transformar mediante map()
    public Flux<String> procesarRecordatoriosActivos() {
        return obtenerFlujoRecordatorios()
                .filter(RecordatorioReactivo::isActivo)
                .map(r -> "[ALARMA ACTIVADA] Paciente: " + r.getPaciente() + " -> Tomar: " + r.getMedicamento() + " (" + r.getDosis() + ")");
    }

    // Contar registros activos con Mono<Long> y el operador count()
    public Mono<Long> contarRecordatoriosActivos() {
        return obtenerFlujoRecordatorios()
                .filter(RecordatorioReactivo::isActivo)
                .count();
    }

    // Flujo vacío para probar el caso sin registros activos
    public Flux<String> procesarCasoSinActivos() {
        return Flux.just(
                        new RecordatorioReactivo("Paracetamol", "500 mg", "Pedro Ruiz", false),
                        new RecordatorioReactivo("Aspirina", "100 mg", "Laura Rios", false)
                )
                .filter(RecordatorioReactivo::isActivo)
                .map(RecordatorioReactivo::toString);
    }
}