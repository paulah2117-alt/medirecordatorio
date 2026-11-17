package com.etitc.medirecordatorio.webflux.service;

import java.time.Duration;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class VerificacionServicioSaludService {

    private final WebClient webClient;

    public VerificacionServicioSaludService(@Value("${medirecordatorio.servicio.salud.url}") String urlBase) {
        this.webClient = WebClient.builder().baseUrl(urlBase).build();
    }

    public Mono<String> verificarDisponibilidadFarmacia() {
        return webClient.get()
                .uri("/api/v1/fuente/estado-farmacia")
                .retrieve()
                .bodyToMono(String.class)
                .timeout(Duration.ofSeconds(3))
                .map(estado -> "Estado central de farmacia y alertas: " + estado)
                .onErrorResume(error -> Mono.just("Servicio de verificación de farmacia no disponible temporalmente"));
    }
}