package com.etitc.medirecordatorio.webflux.controller;

import com.etitc.medirecordatorio.webflux.service.AlertaMedicamentoReactiveService;
import com.etitc.medirecordatorio.webflux.service.VerificacionServicioSaludService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/reactive")
public class AlertaReactiveController {

    private final AlertaMedicamentoReactiveService alertaService;
    private final VerificacionServicioSaludService verificacionService;

    public AlertaReactiveController(AlertaMedicamentoReactiveService alertaService,
                                    VerificacionServicioSaludService verificacionService) {
        this.alertaService = alertaService;
        this.verificacionService = verificacionService;
    }

    @GetMapping("/saludo/{paciente}")
    public Mono<String> saludoPaciente(@PathVariable String paciente) {
        return alertaService.generarSaludoPaciente(paciente);
    }

    @GetMapping(value = "/alertas-dosis", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<String> streamAlertasDosis() {
        return alertaService.emitirCronogramaDosisEnVivo();
    }

    @GetMapping("/estado-farmacia")
    public Mono<String> estadoFarmacia() {
        return verificacionService.verificarDisponibilidadFarmacia();
    }
}