package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.service.MedicamentoService;
import com.etitc.medirecordatorio.service.UsuarioService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
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

    @GetMapping("/")
    public String index(@RequestParam(name = "buscar", required = false) String buscar,
                        @RequestParam(name = "page", defaultValue = "0") int page,
                        Model model, HttpSession session, HttpServletRequest request) {

        Usuario usuarioLogueado = (Usuario) session.getAttribute("usuarioLogueado");
        Page<Medicamento> paginaMedicamentos;

        if (usuarioLogueado != null && "PACIENTE".equals(usuarioLogueado.getRol())) {
            paginaMedicamentos = medicamentoService.buscarPorPacientePaginado(usuarioLogueado, buscar, page, 5);
        } else {
            paginaMedicamentos = medicamentoService.buscarPaginado(buscar, page, 5);
        }

        String tema = "claro";
        if (request.getCookies() != null) {
            for (Cookie c : request.getCookies()) {
                if ("pref_tema".equals(c.getName())) {
                    tema = c.getValue();
                    break;
                }
            }
        }

        model.addAttribute("page", paginaMedicamentos);
        model.addAttribute("buscar", buscar);
        model.addAttribute("usuarioLogueado", usuarioLogueado);
        model.addAttribute("tema", tema);

        return "index";
    }

    @GetMapping("/cambiar-tema")
    public String cambiarTema(@RequestParam String tema, HttpServletResponse response, HttpServletRequest request) {
        Cookie cookie = new Cookie("pref_tema", tema);
        cookie.setMaxAge(7 * 24 * 60 * 60);
        cookie.setPath("/");
        response.addCookie(cookie);

        String referer = request.getHeader("Referer");
        return "redirect:" + (referer != null ? referer : "/");
    }

    @GetMapping("/login")
    public String mostrarLogin(@RequestParam(required = false) String expirado, Model model) {
        if ("true".equals(expirado)) {
            model.addAttribute("mensajeError", "Su sesión se ha cerrado por inactividad.");
        }
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

        redirectAttributes.addFlashAttribute("mensajeError", "El usuario no existe o las credenciales son incorrectas. Por favor verifique o regístrese.");
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, RedirectAttributes redirectAttributes) {
        session.invalidate();
        redirectAttributes.addFlashAttribute("mensajeExito", "Sesión cerrada correctamente.");
        return "redirect:/login";
    }

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
            return "registro";
        }

        usuarioService.registrarUsuario(usuario);
        redirectAttributes.addFlashAttribute("mensajeExito", "Usuario registrado correctamente. Inicie sesión.");
        return "redirect:/login";
    }

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
                                     @RequestParam(name = "pacienteId", required = false) Long pacienteId,
                                     HttpSession session,
                                     Model model,
                                     RedirectAttributes redirectAttributes) {

        // Validar obligatoriedad de selección de paciente
        if (pacienteId == null) {
            model.addAttribute("mensajeErrorPaciente", "Debe seleccionar un paciente para formular el medicamento.");
            model.addAttribute("pacientes", usuarioService.listarPacientes());
            return "formulario-medicamento";
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("pacientes", usuarioService.listarPacientes());
            return "formulario-medicamento";
        }

        Usuario medico = (Usuario) session.getAttribute("usuarioLogueado");
        medicamento.setMedico(medico);

        usuarioService.buscarPorId(pacienteId).ifPresent(medicamento::setPaciente);

        medicamentoService.guardarMedicamento(medicamento);
        redirectAttributes.addFlashAttribute("mensajeExito", "Medicamento formulado con éxito.");
        return "redirect:/";
    }

    @GetMapping("/medicamento/eliminar/{id}")
    public String eliminarMedicamento(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        medicamentoService.eliminarMedicamento(id);
        redirectAttributes.addFlashAttribute("mensajeExito", "Registro eliminado correctamente.");
        return "redirect:/";
    }

    @GetMapping("/probar-error-500")
    public String probarError500() {
        throw new RuntimeException("Prueba intencional de Error 500");
    }
}