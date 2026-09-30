package com.etitc.medirecordatorio.controller;

import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioRestController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/registro")
    public ResponseEntity<?> registrar(@RequestBody Usuario usuario) {
        Usuario nuevo = usuarioService.registrarUsuario(usuario);
        return ResponseEntity.ok(nuevo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credenciales) {
        String documento = credenciales.get("documento");
        String password = credenciales.get("password");
        String rol = credenciales.get("rol");

        boolean exito = usuarioService.autenticar(documento, password, rol);
        if (exito) {
            return ResponseEntity.ok().body(Map.of("mensaje", "Autenticación exitosa"));
        } else {
            return ResponseEntity.status(401).body(Map.of("mensaje", "Cédula o contraseña incorrectas"));
        }
    }
}