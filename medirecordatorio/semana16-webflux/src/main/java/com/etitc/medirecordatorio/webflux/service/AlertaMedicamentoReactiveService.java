package com.etitc.medirecordatorio.webflux.service;

import java.time.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AlertaMedicamentoReactiveService {

    private static final List<String> RECORDATORIOS_ADULTO_MAYOR = List.of(
            "07:00 AM - Losartán 50 mg (Control de presión arterial)",
            "08:30 AM - Metformina 850 mg (Desayuno - Control glucémico)",
            "12:00 PM - Omeprazol 20 mg (Protector gástrico)",
            "04:00 PM - Atorvastatina 20 mg (Control de colesterol)",
            "08:00 PM - Ácido Acetilsalicílico 100 mg (Dosis nocturna)"
    );

    public Mono<String> generarSaludoPaciente(String paciente) {
        String nombre = (paciente == null || paciente.isBlank()) ? "Paciente" : paciente.trim();
        return Mono.just("Hola " + nombre + ", tus recordatorios diarios de medicación están activos y monitoreados.");
    }

    public Flux<String> emitirCronogramaDosisEnVivo() {
        DateTimeFormatter formatoHora = DateTimeFormatter.ofPattern("HH:mm:ss");
        return Flux.interval(Duration.ofSeconds(1))
                .take(5)
                .map(indice -> {
                    String medicamento = RECORDATORIOS_ADULTO_MAYOR.get(indice.intValue());
                    return "[ALERTA #" + (indice + 1) + " | " + LocalTime.now().format(formatoHora) + "] " + medicamento;
                });
    }
}