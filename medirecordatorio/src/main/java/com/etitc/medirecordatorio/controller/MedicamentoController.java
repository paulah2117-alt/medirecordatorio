package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    // READ: GET /api/medicamentos
    @GetMapping
    public List<Medicamento> listar() {
        return medicamentoService.listarMedicamentos();
    }

    // READ ONE: GET /api/medicamentos/{id}
    @GetMapping("/{id}")
    public ResponseEntity<Medicamento> obtenerPorId(@PathVariable Long id) {
        return medicamentoService.obtenerPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // CREATE / UPDATE: POST /api/medicamentos
    @PostMapping
    public Medicamento guardar(@RequestBody Medicamento medicamento) {
        return medicamentoService.guardarMedicamento(medicamento);
    }

    // DELETE: DELETE /api/medicamentos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentoService.eliminarMedicamento(id);
        return ResponseEntity.noContent().build();
    }
}