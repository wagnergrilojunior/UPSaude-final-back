package com.upsaude.service.support.educacaosaude;

import com.upsaude.api.request.educacao.EducacaoSaudeRequest;
import com.upsaude.entity.educacao.EducacaoSaude;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.equipesaude.EquipeSaudeTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EducacaoSaudeRelacionamentosHandler {

    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeTenantEnforcer equipeSaudeTenantEnforcer;
    private final PacienteTenantEnforcer pacienteTenantEnforcer;

    public void resolver(EducacaoSaude entity, EducacaoSaudeRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        ProfissionaisSaude responsavel = profissionaisSaudeTenantEnforcer.validarAcesso(
            Objects.requireNonNull(request.getProfissionalResponsavel(), "profissionalResponsavel é obrigatório"), tenantId);
        entity.setProfissionalResponsavel(responsavel);

        if (request.getEquipeSaude() != null) {
            entity.setEquipeSaude(equipeSaudeTenantEnforcer.validarAcesso(request.getEquipeSaude(), tenantId));
        } else {
            entity.setEquipeSaude(null);
        }

        List<UUID> participantesIds = request.getParticipantes() != null ? request.getParticipantes() : new ArrayList<>();
        List<Paciente> participantes = participantesIds.stream()
            .filter(Objects::nonNull)
            .distinct()
            .map(id -> pacienteTenantEnforcer.validarAcesso(id, tenantId))
            .collect(Collectors.toList());
        entity.setParticipantes(participantes);

        List<UUID> profissionaisIds = request.getProfissionaisParticipantes() != null ? request.getProfissionaisParticipantes() : new ArrayList<>();
        List<ProfissionaisSaude> profissionaisParticipantes = profissionaisIds.stream()
            .filter(Objects::nonNull)
            .distinct()
            .map(id -> profissionaisSaudeTenantEnforcer.validarAcesso(id, tenantId))
            .collect(Collectors.toList());
        entity.setProfissionaisParticipantes(profissionaisParticipantes);

        if (entity.getProfissionalResponsavel() != null && entity.getProfissionalResponsavel().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissionalResponsavel().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
