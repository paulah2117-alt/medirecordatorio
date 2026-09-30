package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public boolean autenticar(String documento, String password, String rol) {
        Optional<Usuario> usuarioOpt = usuarioRepository.findByDocumento(documento);
        if (usuarioOpt.isPresent()) {
            Usuario u = usuarioOpt.get();
            return u.getPassword().equals(password) && u.getRol().equalsIgnoreCase(rol);
        }
        return false;
    }
}