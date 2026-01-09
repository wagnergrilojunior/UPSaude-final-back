package com.upsaude.service.api.support.cirurgia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.clinica.cirurgia.CirurgiaProcedimento;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaProfissional;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.repository.referencia.cid.Cid10SubcategoriasRepository;
import com.upsaude.repository.referencia.sigtap.SigtapProcedimentoRepository;
import com.upsaude.service.api.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.api.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class CirurgiaRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final ConvenioRepository convenioRepository;
    private final Cid10SubcategoriasRepository cid10SubcategoriasRepository;
    private final SigtapProcedimentoRepository sigtapProcedimentoRepository;

    public Cirurgia processarRelacionamentos(Cirurgia entity, CirurgiaRequest request, UUID tenantId, Tenant tenant) {
        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        ProfissionaisSaude cirurgiaoPrincipal = profissionaisSaudeTenantEnforcer
                .validarAcesso(request.getCirurgiaoPrincipal(), tenantId);
        entity.setCirurgiaoPrincipal(cirurgiaoPrincipal);

        if (request.getMedicoCirurgiao() != null) {
            Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedicoCirurgiao(), tenantId);
            entity.setMedicoCirurgiao(medico);
        } else {
            entity.setMedicoCirurgiao(null);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(
                            () -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        } else {
            entity.setConvenio(null);
        }

        if (request.getDiagnosticoPrincipal() != null) {
            Cid10Subcategorias diagnostico = cid10SubcategoriasRepository.findById(request.getDiagnosticoPrincipal())
                    .orElseThrow(() -> {
                        log.warn("Diagnóstico CID10 não encontrado com ID: {}", request.getDiagnosticoPrincipal());
                        return new NotFoundException(
                                "Diagnóstico CID10 não encontrado com ID: " + request.getDiagnosticoPrincipal());
                    });
            entity.setDiagnosticoPrincipal(diagnostico);
        } else {
            entity.setDiagnosticoPrincipal(null);
        }

        processarProcedimentos(entity, request, tenant);
        processarEquipe(entity, request, tenantId, tenant);

        return entity;
    }

    private void processarProcedimentos(Cirurgia entity, CirurgiaRequest request, Tenant tenant) {
        if (request.getProcedimentos() == null || request.getProcedimentos().isEmpty()) {
            if (entity.getProcedimentos() != null) {
                entity.getProcedimentos().clear();
            }
            return;
        }

        // Limpar procedimentos existentes antes de adicionar novos
        if (entity.getProcedimentos() == null) {
            entity.setProcedimentos(new ArrayList<>());
        } else {
            entity.getProcedimentos().clear();
        }

        for (UUID sigtapId : request.getProcedimentos()) {
            SigtapProcedimento procedimento = sigtapProcedimentoRepository.findById(sigtapId)
                    .orElseThrow(() -> {
                        log.warn("Procedimento SIGTAP não encontrado com ID: {}", sigtapId);
                        return new NotFoundException("Procedimento SIGTAP não encontrado com ID: " + sigtapId);
                    });

            CirurgiaProcedimento cirurgiaProcedimento = new CirurgiaProcedimento();
            cirurgiaProcedimento.setCirurgia(entity);
            cirurgiaProcedimento.setProcedimento(procedimento);
            cirurgiaProcedimento.setTenant(tenant);
            cirurgiaProcedimento.setActive(true);
            cirurgiaProcedimento.setQuantidade(1);

            BigDecimal valorUnitario = procedimento.getValorServicoHospitalar() != null
                    ? procedimento.getValorServicoHospitalar()
                    : BigDecimal.ZERO;

            cirurgiaProcedimento.setValorUnitario(valorUnitario);
            cirurgiaProcedimento.setValorTotal(valorUnitario);

            entity.getProcedimentos().add(cirurgiaProcedimento);
        }
    }

    private void processarEquipe(Cirurgia entity, CirurgiaRequest request, UUID tenantId, Tenant tenant) {
        if (request.getEquipe() == null || request.getEquipe().isEmpty()) {
            if (entity.getEquipe() != null) {
                entity.getEquipe().clear();
            }
            return;
        }

        // Limpar equipes existentes antes de adicionar novas
        if (entity.getEquipe() == null) {
            entity.setEquipe(new ArrayList<>());
        } else {
            entity.getEquipe().clear();
        }

        // Criar uma única equipe para todos os profissionais listados na requisição
        // simplificada
        EquipeCirurgica equipe = new EquipeCirurgica();
        equipe.setCirurgia(entity);
        equipe.setTenant(tenant);
        equipe.setActive(true);
        equipe.setEhPrincipal(true);
        equipe.setProfissionais(new ArrayList<>());
        equipe.setMedicos(new ArrayList<>());

        for (UUID profId : request.getEquipe()) {
            // Valida acesso e obtém o profissional
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(profId, tenantId);

            EquipeCirurgicaProfissional equipeProfissional = new EquipeCirurgicaProfissional();
            equipeProfissional.setEquipeCirurgica(equipe);
            equipeProfissional.setProfissional(profissional);
            equipeProfissional.setTenant(tenant);
            equipeProfissional.setActive(true);

            equipe.getProfissionais().add(equipeProfissional);
        }

        entity.getEquipe().add(equipe);
    }
}
