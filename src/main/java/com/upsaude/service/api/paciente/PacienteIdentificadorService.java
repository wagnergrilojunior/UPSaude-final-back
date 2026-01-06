package com.upsaude.service.api.paciente;

import com.upsaude.entity.paciente.PacienteIdentificador;
import com.upsaude.enums.TipoIdentificadorEnum;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteIdentificadorService {

    PacienteIdentificador criar(PacienteIdentificador identificador);

    PacienteIdentificador atualizar(UUID id, PacienteIdentificador identificador);

    void excluir(UUID id);

    Optional<PacienteIdentificador> obterPorId(UUID id);

    List<PacienteIdentificador> listarPorPaciente(UUID pacienteId);

    Optional<PacienteIdentificador> obterPrincipalPorTipo(UUID pacienteId, TipoIdentificadorEnum tipo);

    Optional<PacienteIdentificador> buscarPorTipoEValor(TipoIdentificadorEnum tipo, String valor, UUID tenantId);
}
