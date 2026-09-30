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

    // Mapeo para mostrar el formulario HTML
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