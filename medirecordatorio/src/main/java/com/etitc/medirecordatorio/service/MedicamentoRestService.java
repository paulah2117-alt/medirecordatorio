package com.etitc.medirecordatorio.service;

import com.etitc.medirecordatorio.dto.MedicamentoRequestDTO;
import com.etitc.medirecordatorio.dto.MedicamentoResponseDTO;
import com.etitc.medirecordatorio.model.Medicamento;
import com.etitc.medirecordatorio.model.Usuario;
import com.etitc.medirecordatorio.repository.MedicamentoRepository;
import com.etitc.medirecordatorio.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MedicamentoRestService {

    @Autowired
    private MedicamentoRepository medicamentoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<MedicamentoResponseDTO> listarTodos() {
        return medicamentoRepository.findAll().stream()
                .map(this::convertirAResponseDTO)
                .collect(Collectors.toList());
    }

    public Optional<MedicamentoResponseDTO> obtenerPorId(Long id) {
        return medicamentoRepository.findById(id).map(this::convertirAResponseDTO);
    }

    public MedicamentoResponseDTO crear(MedicamentoRequestDTO request) {
        Usuario paciente = usuarioRepository.findById(request.getPacienteId())
                .orElseThrow(() -> new IllegalArgumentException("El paciente con ID " + request.getPacienteId() + " no existe."));

        Medicamento nuevo = new Medicamento();
        nuevo.setNombre(request.getNombre());
        nuevo.setDosis(request.getDosis());
        nuevo.setHora(request.getHora());
        nuevo.setFrecuencia(request.getFrecuencia());
        nuevo.setPaciente(paciente);

        if (request.getMedicoId() != null) {
            usuarioRepository.findById(request.getMedicoId()).ifPresent(nuevo::setMedico);
        }

        Medicamento guardado = medicamentoRepository.save(nuevo);
        return convertirAResponseDTO(guardado);
    }

    public Optional<MedicamentoResponseDTO> actualizar(Long id, MedicamentoRequestDTO request) {
        return medicamentoRepository.findById(id).map(existente -> {
            Usuario paciente = usuarioRepository.findById(request.getPacienteId())
                    .orElseThrow(() -> new IllegalArgumentException("El paciente con ID " + request.getPacienteId() + " no existe."));

            existente.setNombre(request.getNombre());
            existente.setDosis(request.getDosis());
            existente.setHora(request.getHora());
            existente.setFrecuencia(request.getFrecuencia());
            existente.setPaciente(paciente);

            if (request.getMedicoId() != null) {
                usuarioRepository.findById(request.getMedicoId()).ifPresent(existente::setMedico);
            }

            Medicamento actualizado = medicamentoRepository.save(existente);
            return convertirAResponseDTO(actualizado);
        });
    }

    public boolean eliminar(Long id) {
        if (medicamentoRepository.existsById(id)) {
            medicamentoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private MedicamentoResponseDTO convertirAResponseDTO(Medicamento m) {
        MedicamentoResponseDTO dto = new MedicamentoResponseDTO();
        dto.setId(m.getId());
        dto.setNombre(m.getNombre());
        dto.setDosis(m.getDosis());
        dto.setHora(m.getHora());
        dto.setFrecuencia(m.getFrecuencia());

        if (m.getPaciente() != null) {
            dto.setPacienteId(m.getPaciente().getId());
            dto.setPacienteNombre(m.getPaciente().getNombreCompleto());
        }

        if (m.getMedico() != null) {
            dto.setMedicoId(m.getMedico().getId());
            dto.setMedicoNombre(m.getMedico().getNombreCompleto());
        }

        return dto;
    }
}
