package com.etitc.medirecordatorio.webflux.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1/fuente")
public class FuenteFarmaciaDemoController {

    @GetMapping("/estado-farmacia")
    public Mono<String> estadoFarmacia() {
        return Mono.just("DISPENSACION_ACTIVA_24H");
    }
}
