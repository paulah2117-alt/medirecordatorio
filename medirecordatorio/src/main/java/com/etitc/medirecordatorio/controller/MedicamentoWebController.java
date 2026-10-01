package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.service.MedicamentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class MedicamentoWebController {

    @Autowired
    private MedicamentoService medicamentoService;

    // Mapeo para mostrar el formulario Thymeleaf HTML
    @GetMapping("/formulario-medicamento")
    public String mostrarFormulario(Model model) {
        model.addAttribute("medicamentoForm", new Medicamento());
        return "formulario-medicamento";
    }

    // Procesamiento directo con la entidad Medicamento
    @PostMapping("/guardar")
    public String guardarMedicamento(@ModelAttribute("medicamentoForm") Medicamento medicamento) {
        medicamentoService.guardarMedicamento(medicamento);
        return "redirect:/"; // Redirige al inicio / listado principal
    }

    // Redirección a Login Personal Médico / Usuario (recurso estático)
    @GetMapping("/login-usuario")
    public String loginUsuario() {
        return "redirect:/login-usuario.html";
    }

    // Redirección a Login Paciente (recurso estático)
    @GetMapping("/login-paciente")
    public String loginPaciente() {
        return "redirect:/login-paciente.html";
    }
}