package com.upsaude.service.api.support.filaespera;

import com.upsaude.api.request.agendamento.FilaEsperaRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.agendamento.FilaEspera;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.service.api.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.service.api.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class FilaEsperaRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final AgendamentoRepository agendamentoRepository;

    public FilaEspera processarRelacionamentos(FilaEsperaRequest request, FilaEspera entity, UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
        entity.setEstabelecimento(estabelecimento);

        entity.setPaciente(pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId));

        if (request.getProfissional() != null) {
            entity.setProfissional(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId));
        } else {
            entity.setProfissional(null);
        }

        if (request.getMedico() != null) {
            entity.setMedico(medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId));
        } else {
            entity.setMedico(null);
        }

        if (request.getAgendamento() != null) {
            Agendamento agendamento = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(agendamento);
        } else {
            entity.setAgendamento(null);
        }

        return entity;
    }
}
