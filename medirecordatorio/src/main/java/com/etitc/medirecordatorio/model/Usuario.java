package com.etitc.medirecordatorio.model;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String documento; // Actúa como el nombre de usuario (Cédula/Documento)

    @Column(nullable = false)
    private String password;

    private String nombreCompleto;

    @Column(nullable = false)
    private String rol; // "PACIENTE" o "MEDICO"

    public Usuario() {}

    public Usuario(String documento, String password, String nombreCompleto, String rol) {
        this.documento = documento;
        this.password = password;
        this.nombreCompleto = nombreCompleto;
        this.rol = rol;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDocumento() { return documento; }
    public void setDocumento(String documento) { this.documento = documento; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getNombreCompleto() { return nombreCompleto; }
    public void setNombreCompleto(String nombreCompleto) { this.nombreCompleto = nombreCompleto; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }
}