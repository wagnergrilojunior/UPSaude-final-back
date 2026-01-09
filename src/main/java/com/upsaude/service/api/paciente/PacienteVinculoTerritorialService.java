package com.upsaude.service.api.paciente;

import com.upsaude.entity.paciente.PacienteVinculoTerritorial;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PacienteVinculoTerritorialService {

    PacienteVinculoTerritorial criar(PacienteVinculoTerritorial vinculo);

    PacienteVinculoTerritorial atualizar(UUID id, PacienteVinculoTerritorial vinculo);

    void excluir(UUID id);

    Optional<PacienteVinculoTerritorial> obterPorId(UUID id);

    List<PacienteVinculoTerritorial> listarPorPaciente(UUID pacienteId);

    Optional<PacienteVinculoTerritorial> obterVinculoAtivo(UUID pacienteId);

    void desativarVinculoAtivo(UUID pacienteId);
}
