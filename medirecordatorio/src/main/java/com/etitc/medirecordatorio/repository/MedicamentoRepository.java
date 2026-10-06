package com.etitc.medirecordatorio.repository;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.model.Usuario;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MedicamentoRepository extends JpaRepository<Medicamento, Long> {

    // Búsqueda general con Paginación
    Page<Medicamento> findByNombreContainingIgnoreCase(String nombre, Pageable pageable);

    // Búsqueda y filtrado por Paciente con Paginación
    Page<Medicamento> findByPaciente(Usuario paciente, Pageable pageable);
    Page<Medicamento> findByPacienteAndNombreContainingIgnoreCase(Usuario paciente, String nombre, Pageable pageable);
}