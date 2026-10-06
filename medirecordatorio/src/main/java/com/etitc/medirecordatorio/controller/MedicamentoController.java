package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoController {

    @Autowired
    private MedicamentoService medicamentoService;

    // READ ALL: GET /api/medicamentos
    @GetMapping
    public List<Medicamento> listar() {
        // Usa el método de búsqueda paginada para obtener la lista de elementos
        Page<Medicamento> pagina = medicamentoService.buscarPaginado("", 0, 100);
        return pagina.getContent();
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
    public ResponseEntity<Medicamento> guardar(@RequestBody Medicamento medicamento) {
        Medicamento guardado = medicamentoService.guardarMedicamento(medicamento);
        return ResponseEntity.ok(guardado);
    }

    // DELETE: DELETE /api/medicamentos/{id}
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        medicamentoService.eliminarMedicamento(id);
        return ResponseEntity.noContent().build();
    }
}