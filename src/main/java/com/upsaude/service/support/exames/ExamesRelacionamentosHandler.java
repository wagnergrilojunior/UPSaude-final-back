package com.upsaude.service.support.exames;
import com.upsaude.entity.BaseEntity;

import com.upsaude.api.request.exame.ExamesRequest;
import com.upsaude.entity.atendimento.Atendimento;
import com.upsaude.entity.atendimento.Consultas;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.exame.Exames;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.atendimento.AtendimentoRepository;
import com.upsaude.repository.atendimento.ConsultasRepository;
import com.upsaude.service.support.catalogoexames.CatalogoExamesTenantEnforcer;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExamesRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final CatalogoExamesTenantEnforcer catalogoExamesTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;
    private final AtendimentoRepository atendimentoRepository;
    private final ConsultasRepository consultasRepository;

    public Exames processarRelacionamentos(ExamesRequest request, Exames entity, UUID tenantId, Tenant tenant) {
        if (tenantId == null || tenant == null || tenant.getId() == null || !tenantId.equals(tenant.getId())) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado");
        }

        entity.setTenant(tenant);

        // Base obrigatória
        entity.setPaciente(pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId));

        // Catálogo (opcional, tenant-aware)
        if (request.getCatalogoExame() != null) {
            entity.setCatalogoExame(catalogoExamesTenantEnforcer.validarAcesso(request.getCatalogoExame(), tenantId));
        } else {
            entity.setCatalogoExame(null);
        }

        // Profissionais / Médicos solicitante/responsável (opcionais, tenant-aware)
        if (request.getProfissionalSolicitante() != null) {
            entity.setProfissionalSolicitante(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissionalSolicitante(), tenantId));
        } else {
            entity.setProfissionalSolicitante(null);
        }

        if (request.getMedicoSolicitante() != null) {
            entity.setMedicoSolicitante(medicoTenantEnforcer.validarAcesso(request.getMedicoSolicitante(), tenantId));
        } else {
            entity.setMedicoSolicitante(null);
        }

        if (request.getProfissionalResponsavel() != null) {
            entity.setProfissionalResponsavel(profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissionalResponsavel(), tenantId));
        } else {
            entity.setProfissionalResponsavel(null);
        }

        if (request.getMedicoResponsavel() != null) {
            entity.setMedicoResponsavel(medicoTenantEnforcer.validarAcesso(request.getMedicoResponsavel(), tenantId));
        } else {
            entity.setMedicoResponsavel(null);
        }

        // Estabelecimento realizador (opcional, tenant-aware)
        if (request.getEstabelecimentoRealizador() != null) {
            Estabelecimentos estabelecimentoRealizador = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimentoRealizador(), tenantId);
            entity.setEstabelecimentoRealizador(estabelecimentoRealizador);
            // BaseEntity.estabelecimento usado para filtros tenant-aware
            entity.setEstabelecimento(estabelecimentoRealizador);
        } else {
            entity.setEstabelecimentoRealizador(null);
        }

        // Atendimento (opcional, validar tenant na mão)
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

        // Consulta (opcional, validar tenant na mão)
        if (request.getConsulta() != null) {
            Consultas consulta = consultasRepository.findById(request.getConsulta())
                .orElseThrow(() -> new NotFoundException("Consulta não encontrada com ID: " + request.getConsulta()));

            if (consulta.getTenant() == null || consulta.getTenant().getId() == null
                || !tenantId.equals(consulta.getTenant().getId())) {
                throw new NotFoundException("Consulta não encontrada com ID: " + request.getConsulta());
            }

            entity.setConsulta(consulta);
        } else {
            entity.setConsulta(null);
        }

        return entity;
    }
}

