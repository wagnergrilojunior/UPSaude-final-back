package com.upsaude.service.support.cuidadosenfermagem;

import com.upsaude.api.request.enfermagem.CuidadosEnfermagemRequest;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.enfermagem.CuidadosEnfermagem;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.atendimento.AtendimentoRepository;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final AtendimentoRepository atendimentoRepository;

    public void resolver(CuidadosEnfermagem entity, CuidadosEnfermagemRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        entity.setPaciente(pacienteTenantEnforcer.validarAcesso(
            Objects.requireNonNull(request.getPaciente(), "paciente é obrigatório"), tenantId));

        ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(
            Objects.requireNonNull(request.getProfissional(), "profissional é obrigatório"), tenantId);
        entity.setProfissional(profissional);

        if (request.getAtendimento() != null) {
            UUID atendimentoId = request.getAtendimento();
            Atendimento atendimento = atendimentoRepository.findById(atendimentoId)
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + atendimentoId));
            if (atendimento.getTenant() == null || atendimento.getTenant().getId() == null || !tenantId.equals(atendimento.getTenant().getId())) {
                throw new NotFoundException("Atendimento não encontrado com ID: " + atendimentoId);
            }
            entity.setAtendimento(atendimento);
        } else {
            entity.setAtendimento(null);
        }

        if (entity.getProfissional() != null && entity.getProfissional().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissional().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
