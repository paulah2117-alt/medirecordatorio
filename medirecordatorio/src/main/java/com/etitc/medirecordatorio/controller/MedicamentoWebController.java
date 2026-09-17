package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.dto.MedicamentoForm;
import com.etitc.medirecordatorio.service.MedicamentoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoWebController {

    private final MedicamentoService medicamentoService;

    public MedicamentoWebController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    // Listado principal
    @GetMapping
    public String listarMedicamentos(Model model) {
        model.addAttribute("listaMedicamentos", medicamentoService.listarMedicamentos());
        model.addAttribute("titulo", "Gestión y Recordatorio de Medicamentos");
        return "medicamentos";
    }

    // Ruta GET: Mostrar Formulario de Registro (Semana 7)
    @GetMapping("/nuevo")
    public String mostrarFormulario(Model model) {
        model.addAttribute("medicamentoForm", new MedicamentoForm());
        return "formulario-medicamento";
    }

    // Ruta POST: Procesar y Validar datos (Semana 7)
    @PostMapping("/guardar")
    public String guardarMedicamento(@Valid @ModelAttribute("medicamentoForm") MedicamentoForm medicamentoForm,
                                     BindingResult bindingResult,
                                     Model model) {
        if (bindingResult.hasErrors()) {
            return "formulario-medicamento"; // Regresa al formulario con mensajes de error
        }

        medicamentoService.guardarMedicamento(medicamentoForm);
        return "redirect:/medicamentos"; // Redirección tras guardar exitosamente
    }

    // Login Personal Médico / Usuario
    @GetMapping("/login-usuario")
    public String loginUsuario() {
        return "login-usuario";
    }

    // Login Paciente
    @GetMapping("/login-paciente")
    public String loginPaciente() {
        return "login-paciente";
    }
}