package com.upsaude.service.support.filaespera;

import com.upsaude.api.request.FilaEsperaRequest;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.FilaEspera;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.service.support.agendamento.AgendamentoTenantEnforcer;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
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
    private final AgendamentoTenantEnforcer agendamentoTenantEnforcer;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;

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
            entity.setAgendamento(agendamentoTenantEnforcer.validarAcesso(request.getAgendamento(), tenantId));
        } else {
            entity.setAgendamento(null);
        }

        if (request.getEspecialidade() != null) {
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(request.getEspecialidade())
                .orElseThrow(() -> new NotFoundException("Especialidade médica não encontrada com ID: " + request.getEspecialidade()));
            entity.setEspecialidade(especialidade);
        } else {
            entity.setEspecialidade(null);
        }

        return entity;
    }
}

