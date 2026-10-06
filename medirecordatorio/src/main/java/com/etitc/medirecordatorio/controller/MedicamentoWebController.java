package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.service.MedicamentoService;
import com.etitc.medirecordatorio.service.UsuarioService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Optional;

@Controller
public class MedicamentoWebController {

    @Autowired
    private MedicamentoService medicamentoService;

    @Autowired
    private UsuarioService usuarioService;

    // 1. Listado Principal con Paginación y Búsqueda
    @GetMapping("/")
    public String index(@RequestParam(name = "buscar", required = false) String buscar,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        Model model, HttpSession session) {

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        Page<Medicamento> paginaMedicamentos;

        // Si el usuario es Paciente, solo se cargan sus medicamentos formulados
        if (usuarioLogueado != null && "PACIENTE".equals(usuarioLogueado.getRol())) {
            paginaMedicamentos = medicamentoService.buscarPorPacientePaginado(usuarioLogueado, buscar, page, 5);
        } else {
            paginaMedicamentos = medicamentoService.buscarPaginado(buscar, page, 5);
        }

        model.addAttribute("page", paginaMedicamentos);
        model.addAttribute("buscar", buscar);
        model.addAttribute("usuarioLogueado", usuarioLogueado);

        return "index";
    }

    // 2. Registro de Usuarios (Pacientes / Personal Médico)
    @GetMapping("/registro")
    public String mostrarRegistro(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "registro";
    }

    @PostMapping("/registro")
    public String registrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult bindingResult,
                                   RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "registro"; // Devuelve errores evaluados en Backend
        }

        usuarioService.registrarUsuario(usuario);
        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario registrado correctamente. Inicie sesión.");
        return "redirect:/login";
    }

    // 3. Inicio de Sesión
    @GetMapping("/login")
    public String mostrarLogin() {
        return "login";
    }

    @PostMapping("/login")
    public String procesarLogin(@RequestParam String documento,
                                @RequestParam String password,
                                @RequestParam String rol,
                                HttpSession session,
                                RedirectAttributes redirectAttributes) {

        if (usuarioService.autenticar(documento, password, rol)) {
            Optional<Usuario> userOpt = usuarioService.buscarPorDocumento(documento);
            userOpt.ifPresent(u -> session.setAttribute("usuarioLogueado", u));
            redirectAttributes.addFlashAttribute("mensajeExito", "Sesión iniciada correctamente.");
            return "redirect:/";
        }

        redirectAttributes.addFlashAttribute("mensajeError", "Documento o contraseña incorrectos.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate();
        return "redirect:/";
    }

    // 4. Formulario de Prescripción Médica (Médico formula a un Paciente)
    @GetMapping("/medicamento/nuevo")
    public String nuevoMedicamento(Model model, HttpSession session, RedirectAttributes redirectAttributes) {
        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        if (usuarioLogueado == null || !"MEDICO".equals(usuarioLogueado.getRol())) {
            redirectAttributes.addFlashAttribute("mensajeError", "Solo el personal médico puede formular medicamentos.");
            return "redirect:/";
        }

        model.addAttribute("medicamento", new Medicamento());
        model.addAttribute("pacientes", usuarioService.listarPacientes());
        return "formulario-medicamento";
    }

    @PostMapping("/medicamento/guardar")
    public String guardarMedicamento(@Valid @ModelAttribute("medicamento") Medicamento medicamento,
                                     BindingResult bindingResult,
                                     @RequestParam(required = false) Long pacienteId,
                                     HttpSession session,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            model.addAttribute("pacientes", usuarioService.listarPacientes());
            return "formulario-medicamento";
        }

        Usuario medico = (Usuario) session.getAttribute("usuarioLogueado");
        medicamento.setMedico(medico);

        if (pacienteId != null) {
            usuarioService.buscarPorId(pacienteId).ifPresent(medicamento::setPaciente);
        }

        medicamentoService.guardarMedicamento(medicamento);
        redirectAttributes.addFlashAttribute("mensajeExito", "Medicamento formulado y guardado con éxito.");
        return "redirect:/";
    }

    // 5. Eliminar Registro
    @GetMapping("/medicamento/eliminar/{id}")
    public String eliminarMedicamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        medicamentoService.eliminarMedicamento(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Registro eliminado correctamente.");
        return "redirect:/";
    }
}