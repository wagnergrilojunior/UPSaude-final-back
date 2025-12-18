package com.upsaude.service.support.cuidadosenfermagem;

import com.upsaude.api.request.enfermagem.CuidadosEnfermagemRequest;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.enfermagem.CuidadosEnfermagem;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CuidadosEnfermagemRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final AtendimentoRepository atendimentoRepository;

    public void resolver(CuidadosEnfermagem entity, CuidadosEnfermagemRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
            entity.setPaciente(paciente);
        }

        if (request.getProfissional() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId);
            entity.setProfissional(profissional);
        }

        if (request.getAtendimento() != null) {
            Atendimento atendimento = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + request.getAtendimento()));
            entity.setAtendimento(atendimento);
            
            if (atendimento.getEstabelecimento() != null) {
                entity.setEstabelecimento(atendimento.getEstabelecimento());
            }
        } else {
            entity.setAtendimento(null);
        }
    }
}
