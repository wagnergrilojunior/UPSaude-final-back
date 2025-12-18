package com.upsaude.service.support.medico;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.repository.profissional.MedicosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoUpdater {

    private final MedicoValidationService validationService;
    private final MedicosMapper medicosMapper;
    private final MedicoTenantEnforcer tenantEnforcer;
    private final MedicoRelacionamentosHandler relacionamentosHandler;
    private final MedicosRepository medicosRepository;

    public Medicos atualizar(UUID id, MedicosRequest request, UUID tenantId, Tenant tenant) {
        log.debug("Atualizando médico. ID: {}", id);

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaAtualizacao(id, request, medicosRepository, tenantId);
        validationService.sanitizarFlags(request);

        Medicos medicosExistente = tenantEnforcer.validarAcesso(id, tenantId);

        Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar médico");

        medicosMapper.updateFromRequest(request, medicosExistente);

        sanitizarRegistroProfissional(medicosExistente);
        relacionamentosHandler.processarRelacionamentos(medicosExistente, request, tenant);

        Medicos medicosAtualizado = medicosRepository.save(Objects.requireNonNull(medicosExistente));
        log.info("Médico atualizado com sucesso. ID: {}, Tenant: {}", medicosAtualizado.getId(), tenantId);

        return medicosAtualizado;
    }

    private void sanitizarRegistroProfissional(Medicos medicos) {
        if (medicos.getRegistroProfissional() != null) {
            if (!StringUtils.hasText(medicos.getRegistroProfissional().getCrm())) {
                medicos.getRegistroProfissional().setCrm(null);
            }
            if (!StringUtils.hasText(medicos.getRegistroProfissional().getCrmUf())) {
                medicos.getRegistroProfissional().setCrmUf(null);
            }
        }
    }
}
