package com.etitc.medirecordatorio.runner;

import com.etitc.medirecordatorio.model.RecordatorioReactivo;
import com.etitc.medirecordatorio.service.RecordatorioReactivoService;
import org.reactivestreams.Subscription;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import reactor.core.publisher.BaseSubscriber;

@Component
public class ReactivoDemoRunner implements CommandLineRunner {

    @Autowired
    private RecordatorioReactivoService reactivoService;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("\n=======================================================");
        System.out.println("   DEMOSTRACIÓN REACTIVA - SEMANA 15 (FLUX & MONO)    ");
        System.out.println("=======================================================");

        // 1. Demostración de Flux con subscribe(), onNext, onError y onComplete
        System.out.println("\n--- 1. PROCESAMIENTO DE REGISTROS ACTIVOS (FILTER + MAP) ---");
        reactivoService.procesarRecordatoriosActivos()
                .subscribe(
                        elemento -> System.out.println("[onNext] " + elemento),
                        error -> System.err.println("[onError] Ocurrió un fallo: " + error.getMessage()),
                        () -> System.out.println("[onComplete] Todos los recordatorios activos fueron emitidos exitosamente.")
                );

        // 2. Conteo de elementos con Mono y count()
        System.out.println("\n--- 2. CONTEO REACTIVO CON MONO (COUNT) ---");
        reactivoService.contarRecordatoriosActivos()
                .subscribe(total -> System.out.println("[Mono Resultado] Total de recordatorios activos: " + total));

        // 3. Caso sin registros activos
        System.out.println("\n--- 3. CASO SIN REGISTROS ACTIVOS ---");
        reactivoService.procesarCasoSinActivos()
                .subscribe(
                        elemento -> System.out.println("[onNext] " + elemento),
                        error -> System.err.println("[onError] " + error.getMessage()),
                        () -> System.out.println("[onComplete] Flujo finalizado: Cero elementos activos encontrados.")
                );

        // 4. Control de demanda con BaseSubscriber, request(1) y cancel()
        System.out.println("\n--- 4. CONTROL DE DEMANDA Y CANCELACIÓN (BACKPRESSURE) ---");
        reactivoService.obtenerFlujoRecordatorios()
                .subscribe(new BaseSubscriber<>() {
                    private int recibidos = 0;

                    @Override
                    protected void hookOnSubscribe(Subscription subscription) {
                        System.out.println("[Demanda] Suscripción establecida. Solicitando 1 elemento...");
                        request(1);
                    }

                    @Override
                    protected void hookOnNext(RecordatorioReactivo valor) {
                        recibidos++;
                        System.out.println("[Demanda Recibido " + recibidos + "] " + valor.getMedicamento() + " (" + valor.getPaciente() + ")");

                        if (recibidos == 2) {
                            System.out.println("[Demanda] Límite de 2 alcanzado -> Ejecutando cancel()...");
                            cancel();
                            System.out.println("[Demanda] Cancelación ejecutada. No se emitirán más elementos.");
                        } else {
                            System.out.println("[Demanda] Solicitando el siguiente elemento individual request(1)...");
                            request(1);
                        }
                    }

                    @Override
                    protected void hookOnComplete() {
                        System.out.println("[Demanda] hookOnComplete: Flujo terminado normalmente.");
                    }

                    @Override
                    protected void hookOnCancel() {
                        System.out.println("[Demanda] hookOnCancel: Flujo cancelado por el consumidor.");
                    }
                });

        System.out.println("=======================================================\n");
    }
}