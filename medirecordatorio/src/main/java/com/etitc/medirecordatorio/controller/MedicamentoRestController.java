package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.repository.MedicamentoRepository;
import com.etitc.medirecordatorio.repository.UsuarioRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/medicamentos")
public class MedicamentoRestController {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    // 1. GET: Consultar todos los registros (200 OK)
    @GetMapping
    public ResponseEntity<List<Medicamento>> listarTodos() {
        return ResponseEntity.ok(medicamentoRepository.findAll());
    }

    // 2. GET por ID: Consultar un registro por su clave primaria (200 OK / 404 Not Found)
    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerPorId(@PathVariable Long id) {
        Optional<Medicamento> medOpt = medicamentoRepository.findById(id);
        if (medOpt.isPresent()) {
            return ResponseEntity.ok(medOpt.get());
        }
        Map<String, String> error = new HashMap<>();
        error.put("error", "Medicamento con ID " + id + " no encontrado.");
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 3. POST: Crear un nuevo registro (201 Created / 400 Bad Request)
    @PostMapping
    public ResponseEntity<?> crearMedicamento(@Valid @RequestBody Medicamento medicamento, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        if (medicamento.getPaciente() == null || medicamento.getPaciente().getId() == null) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Debe incluir un paciente válido con su ID.");
            return ResponseEntity.badRequest().body(error);
        }

        Optional<Usuario> pacOpt = usuarioRepository.findById(medicamento.getPaciente().getId());
        if (pacOpt.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "El paciente con ID " + medicamento.getPaciente().getId() + " no existe.");
            return ResponseEntity.badRequest().body(error);
        }
        medicamento.setPaciente(pacOpt.get());

        if (medicamento.getMedico() != null && medicamento.getMedico().getId() != null) {
            usuarioRepository.findById(medicamento.getMedico().getId()).ifPresent(medicamento::setMedico);
        }

        Medicamento guardado = medicamentoRepository.save(medicamento);
        return ResponseEntity.status(HttpStatus.CREATED).body(guardado);
    }

    // 4. PUT: Actualizar un registro existente (200 OK / 400 Bad Request / 404 Not Found)
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarMedicamento(@PathVariable Long id,
                                                   @Valid @RequestBody Medicamento datos,
                                                   BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            Map<String, String> errores = new HashMap<>();
            bindingResult.getFieldErrors().forEach(e -> errores.put(e.getField(), e.getDefaultMessage()));
            return ResponseEntity.badRequest().body(errores);
        }

        Optional<Medicamento> medOpt = medicamentoRepository.findById(id);
        if (medOpt.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Medicamento con ID " + id + " no encontrado para actualizar.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        Medicamento existente = medOpt.get();
        existente.setNombre(datos.getNombre());
        existente.setDosis(datos.getDosis());
        existente.setHora(datos.getHora());
        existente.setFrecuencia(datos.getFrecuencia());

        if (datos.getPaciente() != null && datos.getPaciente().getId() != null) {
            usuarioRepository.findById(datos.getPaciente().getId()).ifPresent(existente::setPaciente);
        }

        if (datos.getMedico() != null && datos.getMedico().getId() != null) {
            usuarioRepository.findById(datos.getMedico().getId()).ifPresent(existente::setMedico);
        }

        Medicamento actualizado = medicamentoRepository.save(existente);
        return ResponseEntity.ok(actualizado);
    }

    // 5. DELETE: Eliminar un registro (200 OK / 404 Not Found)
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMedicamento(@PathVariable Long id) {
        Optional<Medicamento> medOpt = medicamentoRepository.findById(id);
        if (medOpt.isEmpty()) {
            Map<String, String> error = new HashMap<>();
            error.put("error", "Medicamento con ID " + id + " no encontrado para eliminar.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
        }

        medicamentoRepository.deleteById(id);
        Map<String, String> ok = new HashMap<>();
        ok.put("mensaje", "Medicamento con ID " + id + " eliminado con éxito.");
        return ResponseEntity.ok(ok);
    }
}