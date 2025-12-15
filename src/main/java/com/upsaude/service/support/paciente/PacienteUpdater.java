package com.upsaude.service.support.paciente;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.PacienteMapper;
import com.upsaude.repository.PacienteRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteUpdater {

    private final PacienteValidationService validationService;
    private final PacienteMapper pacienteMapper;
    private final PacienteTenantEnforcer tenantEnforcer;
    private final PacienteOneToOneRelationshipHandler oneToOneHandler;
    private final PacienteRepository pacienteRepository;

    public Paciente atualizar(UUID id, PacienteRequest request, UUID tenantId) {
        log.debug("Atualizando paciente. ID: {}", id);

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaAtualizacao(id, request, pacienteRepository, tenantId);
        validationService.sanitizarFlags(request);

        Paciente pacienteExistente = tenantEnforcer.validarAcesso(id, tenantId);

        pacienteMapper.updateFromRequest(request, pacienteExistente);
        oneToOneHandler.processarRelacionamentos(pacienteExistente, request);

        Paciente pacienteAtualizado = pacienteRepository.save(Objects.requireNonNull(pacienteExistente));
        log.info("Paciente atualizado com sucesso. ID: {}, Tenant: {}", pacienteAtualizado.getId(), tenantId);

        return pacienteAtualizado;
    }
}
