package com.upsaude.service.support.atividadeprofissional;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.AtividadeProfissionalRequest;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.profissional.AtividadeProfissional;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.service.support.cirurgia.CirurgiaTenantEnforcer;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class AtividadeProfissionalRelacionamentosHandler {

    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final CirurgiaTenantEnforcer cirurgiaTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final AtendimentoRepository atendimentoRepository;

    public AtividadeProfissional processarRelacionamentos(AtividadeProfissionalRequest request,
                                                         AtividadeProfissional entity,
                                                         UUID tenantId,
                                                         Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
        entity.setEstabelecimento(estabelecimento);

        entity.setProfissional(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissional(), tenantId));

        if (request.getMedico() != null) {
            entity.setMedico(medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId));
        } else {
            entity.setMedico(null);
        }

        if (request.getPaciente() != null) {
            entity.setPaciente(pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId));
        } else {
            entity.setPaciente(null);
        }

        if (request.getCirurgia() != null) {
            entity.setCirurgia(cirurgiaTenantEnforcer.validarAcesso(request.getCirurgia(), tenantId));
        } else {
            entity.setCirurgia(null);
        }

        if (request.getAtendimento() != null) {
            Atendimento atendimento = atendimentoRepository.findById(request.getAtendimento())
                .orElseThrow(() -> new NotFoundException("Atendimento não encontrado com ID: " + request.getAtendimento()));

            if (atendimento.getTenant() == null || atendimento.getTenant().getId() == null
                || !tenantId.equals(atendimento.getTenant().getId())) {
                throw new NotFoundException("Atendimento não encontrado com ID: " + request.getAtendimento());
            }

            entity.setAtendimento(atendimento);
        } else {
            entity.setAtendimento(null);
        }

        return entity;
    }
}

