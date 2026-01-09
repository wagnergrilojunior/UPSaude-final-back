package com.upsaude.service.api.paciente;

import com.upsaude.entity.paciente.PacienteContato;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteContatoService {

    PacienteContato criar(PacienteContato contato);

    PacienteContato atualizar(UUID id, PacienteContato contato);

    void excluir(UUID id);

    Optional<PacienteContato> obterPorId(UUID id);

    List<PacienteContato> listarPorPaciente(UUID pacienteId);
}
