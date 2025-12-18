package com.upsaude.service.support.puericultura;

import com.upsaude.api.request.saude_publica.puericultura.PuericulturaRequest;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.entity.saude_publica.puericultura.Puericultura;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.profissional.equipe.EquipeSaudeRepository;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PuericulturaRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final EquipeSaudeRepository equipeSaudeRepository;

    public void resolver(Puericultura entity, PuericulturaRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        if (request.getProfissionalResponsavel() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissionalResponsavel(), tenantId);
            entity.setProfissionalResponsavel(profissional);
        }

        if (request.getEquipeSaude() != null) {
            EquipeSaude equipe = equipeSaudeRepository.findByIdAndTenant(request.getEquipeSaude(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Equipe de saúde não encontrada com ID: " + request.getEquipeSaude()));
            entity.setEquipeSaude(equipe);
        }
    }
}
