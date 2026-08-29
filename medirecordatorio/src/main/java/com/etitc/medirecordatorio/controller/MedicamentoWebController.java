package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.service.MedicamentoService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/medicamentos")
public class MedicamentoWebController {

    private final MedicamentoService medicamentoService;

    public MedicamentoWebController(MedicamentoService medicamentoService) {
        this.medicamentoService = medicamentoService;
    }

    @GetMapping
    public String listarMedicamentos(Model model) {
        // Pasa los datos al modelo para que Thymeleaf los muestre en la vista
        model.addAttribute("listaMedicamentos", medicamentoService.listarMedicamentos());
        model.addAttribute("titulo", "Gestión y Recordatorio de Medicamentos");
        return "medicamentos"; // Llama a la plantilla medicamentos.html
    }
}