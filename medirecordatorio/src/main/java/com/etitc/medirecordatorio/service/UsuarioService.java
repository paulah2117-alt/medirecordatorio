package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Usuario registrarUsuario(Usuario usuario) {
        return usuarioRepository.save(usuario);
    }

    public List<Usuario> listarPacientes() {
        return usuarioRepository.findByRol("PACIENTE");
    }

    public Optional<Usuario> buscarPorDocumento(String documento) {
        return usuarioRepository.findByDocumento(documento);
    }

    public Optional<Usuario> buscarPorId(Long id) {
        return usuarioRepository.findById(id);
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