package com.etitc.medirecordatorio.repository;

import com.etitc.medirecordatorio.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByDocumento(String documento);
    List<Usuario> findByRol(String rol);
}