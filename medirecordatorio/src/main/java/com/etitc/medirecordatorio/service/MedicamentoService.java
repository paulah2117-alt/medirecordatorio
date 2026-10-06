package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public Page<Medicamento> buscarPaginado(String nombre, int pagina, int tamano) {
        Pageable pageable = PageRequest.of(pagina, tamano);
        if (nombre != null && !nombre.trim().isEmpty()) {
            return medicamentoRepository.findByNombreContainingIgnoreCase(nombre, pageable);
        }
        return medicamentoRepository.findAll(pageable);
    }

    public Page<Medicamento> buscarPorPacientePaginado(Usuario paciente, String nombre, int pagina, int tamano) {
        Pageable pageable = PageRequest.of(pagina, tamano);
        if (nombre != null && !nombre.trim().isEmpty()) {
            return medicamentoRepository.findByPacienteAndNombreContainingIgnoreCase(paciente, nombre, pageable);
        }
        return medicamentoRepository.findByPaciente(paciente, pageable);
    }

    public Optional<Medicamento> obtenerPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    @Transactional
    public Medicamento guardarMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    @Transactional
    public void eliminarMedicamento(Long id) {
        medicamentoRepository.deleteById(id);
    }
}