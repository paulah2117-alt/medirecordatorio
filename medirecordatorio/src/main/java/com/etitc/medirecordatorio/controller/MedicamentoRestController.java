package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.dto.MedicamentoRequestDTO;
import com.etitc.medirecordatorio.dto.MedicamentoResponseDTO;
import com.etitc.medirecordatorio.service.MedicamentoRestService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoRestController {

    @Autowired
    private MedicamentoRestService medicamentoRestService;

    // 1. GET /api/medicamentos -> 200 OK
    @GetMapping
    public ResponseEntity<List<MedicamentoResponseDTO>> listarTodos() {
        return ResponseEntity.ok(medicamentoRestService.listarTodos());
    }

    // 2. GET /api/medicamentos/{id} -> 200 OK / 404 Not Found
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<MedicamentoResponseDTO> dtoOpt = medicamentoRestService.obtenerPorId(id);
        if (dtoOpt.isPresent()) {
            return ResponseEntity.ok(dtoOpt.get());
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Medicamento con ID " + id + " no encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 3. POST /api/medicamentos -> 201 Created con cabecera Location / 400 Bad Request
    @PostMapping
    public ResponseEntity<?> crearMedicamento(@Valid @RequestBody MedicamentoRequestDTO request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            MedicamentoResponseDTO creado = medicamentoRestService.crear(request);
            URI location = ServletUriComponentsBuilder
                    .fromCurrentRequest()
                    .path("/{id}")
                    .buildAndExpand(creado.getId())
                    .toUri();
            return ResponseEntity.created(location).body(creado);
        } catch (IllegalArgumentException ex) {
            Map<String, String> err = new HashMap<>();
            err.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(err);
        }
    }

    // 4. PUT /api/medicamentos/{id} -> 200 OK / 400 Bad Request / 404 Not Found
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMedicamento(@PathVariable Long id,
                                                   @Valid @RequestBody MedicamentoRequestDTO request,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        try {
            Optional<MedicamentoResponseDTO> actualizadoOpt = medicamentoRestService.actualizar(id, request);
            if (actualizadoOpt.isPresent()) {
                return ResponseEntity.ok(actualizadoOpt.get());
            }
            Map<String, String> error = new HashMap<>();
            error.put("error", "Medicamento con ID " + id + " no encontrado para actualizar.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        } catch (IllegalArgumentException ex) {
            Map<String, String> err = new HashMap<>();
            err.put("error", ex.getMessage());
            return ResponseEntity.badRequest().body(err);
        }
    }

    // 5. DELETE /api/medicamentos/{id} -> 204 No Content / 404 Not Found
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMedicamento(@PathVariable Long id) {
        boolean eliminado = medicamentoRestService.eliminar(id);
        if (eliminado) {
            return ResponseEntity.noContent().build(); // 204 No Content sin cuerpo
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Medicamento con ID " + id + " no encontrado para eliminar.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }
}