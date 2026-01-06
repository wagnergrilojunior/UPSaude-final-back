package com.upsaude.service.api.support.paciente;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.paciente.PacienteIdentificadorRepository;
import com.upsaude.repository.paciente.PacienteContatoRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteUpdater {

    private final PacienteValidationService validationService;
    private final PacienteMapper pacienteMapper;
    private final PacienteTenantEnforcer tenantEnforcer;
    private final PacienteAssociacoesManager associacoesManager;
    private final PacienteRepository pacienteRepository;
    private final PacienteIdentificadorRepository identificadorRepository;
    private final PacienteContatoRepository contatoRepository;

    public Paciente atualizar(UUID id, PacienteRequest request, UUID tenantId) {
        log.debug("Atualizando paciente. ID: {}", id);

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaAtualizacao(id, request, pacienteRepository, identificadorRepository,
                contatoRepository, tenantId);
        validationService.sanitizarFlags(request);

        Paciente pacienteExistente = tenantEnforcer.validarAcesso(id, tenantId);

        pacienteMapper.updateFromRequest(request, pacienteExistente);

        // Processar todas as associações (incluindo atualizações)
        associacoesManager.processarTodas(pacienteExistente, request, tenantId);

        Paciente pacienteAtualizado = pacienteRepository.save(Objects.requireNonNull(pacienteExistente));
        log.info("Paciente atualizado com sucesso. ID: {}, Tenant: {}", pacienteAtualizado.getId(), tenantId);

        return pacienteAtualizado;
    }
}
