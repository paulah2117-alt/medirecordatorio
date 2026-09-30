package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.repository.MedicamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicamentoService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    public List<Medicamento> listarMedicamentos() {
        return medicamentoRepository.findAll();
    }

    public List<Medicamento> listarTodos() {
        return medicamentoRepository.findAll();
    }

    public Optional<Medicamento> obtenerPorId(Long id) {
        return medicamentoRepository.findById(id);
    }

    public Medicamento guardarMedicamento(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    public Medicamento guardar(Medicamento medicamento) {
        return medicamentoRepository.save(medicamento);
    }

    public void eliminarMedicamento(Long id) {
        medicamentoRepository.deleteById(id);
    }

    public void eliminar(Long id) {
        medicamentoRepository.deleteById(id);
    }
}