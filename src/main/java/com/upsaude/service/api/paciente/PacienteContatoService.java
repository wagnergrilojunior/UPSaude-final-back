package com.upsaude.service.api.paciente;

import com.upsaude.entity.paciente.PacienteContato;
import com.upsaude.enums.TipoContatoEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteContatoService {

    PacienteContato criar(PacienteContato contato);

    PacienteContato atualizar(UUID id, PacienteContato contato);

    void excluir(UUID id);

    Optional<PacienteContato> obterPorId(UUID id);

    List<PacienteContato> listarPorPaciente(UUID pacienteId);

    Optional<PacienteContato> obterPrincipalPorTipo(UUID pacienteId, TipoContatoEnum tipo);

    PacienteContato verificarContato(UUID id);
}

