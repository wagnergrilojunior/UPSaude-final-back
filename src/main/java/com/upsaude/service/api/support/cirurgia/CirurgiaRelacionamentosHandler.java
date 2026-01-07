package com.upsaude.service.api.support.cirurgia;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.cirurgia.CirurgiaProcedimentoRequest;
import com.upsaude.api.request.clinica.cirurgia.CirurgiaRequest;
import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaMedicoRequest;
import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaProfissionalRequest;
import com.upsaude.entity.clinica.cirurgia.Cirurgia;
import com.upsaude.entity.clinica.cirurgia.CirurgiaProcedimento;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaMedico;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaProfissional;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import com.upsaude.entity.referencia.sigtap.SigtapProcedimento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.clinica.cirurgia.CirurgiaProcedimentoMapper;
import com.upsaude.mapper.clinica.cirurgia.EquipeCirurgicaMapper;
import com.upsaude.mapper.clinica.cirurgia.EquipeCirurgicaMedicoMapper;
import com.upsaude.mapper.clinica.cirurgia.EquipeCirurgicaProfissionalMapper;
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
    private final CirurgiaProcedimentoMapper cirurgiaProcedimentoMapper;
    private final EquipeCirurgicaMapper equipeCirurgicaMapper;
    private final EquipeCirurgicaMedicoMapper equipeCirurgicaMedicoMapper;
    private final EquipeCirurgicaProfissionalMapper equipeCirurgicaProfissionalMapper;

    public Cirurgia processarRelacionamentos(Cirurgia entity, CirurgiaRequest request, UUID tenantId, Tenant tenant) {
        Paciente paciente = pacienteTenantEnforcer.validarAcesso(request.getPaciente(), tenantId);
        entity.setPaciente(paciente);

        ProfissionaisSaude cirurgiaoPrincipal = profissionaisSaudeTenantEnforcer.validarAcesso(request.getCirurgiaoPrincipal(), tenantId);
        entity.setCirurgiaoPrincipal(cirurgiaoPrincipal);

        if (request.getMedicoCirurgiao() != null) {
            Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedicoCirurgiao(), tenantId);
            entity.setMedicoCirurgiao(medico);
        } else {
            entity.setMedicoCirurgiao(null);
        }

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findByIdAndTenant(request.getConvenio(), tenantId)
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            entity.setConvenio(convenio);
        } else {
            entity.setConvenio(null);
        }

        if (request.getDiagnosticoPrincipal() != null) {
            Cid10Subcategorias diagnostico = cid10SubcategoriasRepository.findById(request.getDiagnosticoPrincipal())
                    .orElseThrow(() -> {
                        log.warn("Diagnóstico CID10 não encontrado com ID: {}", request.getDiagnosticoPrincipal());
                        return new NotFoundException("Diagnóstico CID10 não encontrado com ID: " + request.getDiagnosticoPrincipal());
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

        for (CirurgiaProcedimentoRequest procRequest : request.getProcedimentos()) {
            SigtapProcedimento procedimento = sigtapProcedimentoRepository.findById(procRequest.getProcedimento())
                    .orElseThrow(() -> {
                        log.warn("Procedimento SIGTAP não encontrado com ID: {}", procRequest.getProcedimento());
                        return new NotFoundException("Procedimento SIGTAP não encontrado com ID: " + procRequest.getProcedimento());
                    });

            CirurgiaProcedimento cirurgiaProcedimento = cirurgiaProcedimentoMapper.fromRequest(procRequest);
            cirurgiaProcedimento.setCirurgia(entity);
            cirurgiaProcedimento.setProcedimento(procedimento);
            cirurgiaProcedimento.setTenant(tenant);
            cirurgiaProcedimento.setActive(true);

            if (procRequest.getValorUnitario() == null && procedimento.getValorServicoHospitalar() != null) {
                cirurgiaProcedimento.setValorUnitario(procedimento.getValorServicoHospitalar());
            }

            if (procRequest.getValorTotal() == null) {
                BigDecimal valorUnitario = cirurgiaProcedimento.getValorUnitario() != null 
                    ? cirurgiaProcedimento.getValorUnitario() 
                    : BigDecimal.ZERO;
                BigDecimal quantidade = BigDecimal.valueOf(procRequest.getQuantidade() != null ? procRequest.getQuantidade() : 1);
                cirurgiaProcedimento.setValorTotal(valorUnitario.multiply(quantidade));
            }

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

        for (EquipeCirurgicaRequest equipeRequest : request.getEquipe()) {
            EquipeCirurgica equipe = equipeCirurgicaMapper.fromRequest(equipeRequest);
            equipe.setCirurgia(entity);
            equipe.setTenant(tenant);
            equipe.setActive(true);

            // Processar profissionais
            if (equipe.getProfissionais() == null) {
                equipe.setProfissionais(new ArrayList<>());
            } else {
                equipe.getProfissionais().clear();
            }
            
            if (equipeRequest.getProfissionais() != null) {
                for (EquipeCirurgicaProfissionalRequest profRequest : equipeRequest.getProfissionais()) {
                    ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(profRequest.getProfissional(), tenantId);
                    
                    EquipeCirurgicaProfissional equipeProfissional = equipeCirurgicaProfissionalMapper.fromRequest(profRequest);
                    equipeProfissional.setEquipeCirurgica(equipe);
                    equipeProfissional.setProfissional(profissional);
                    equipeProfissional.setTenant(tenant);
                    equipeProfissional.setActive(true);
                    
                    equipe.getProfissionais().add(equipeProfissional);
                }
            }

            // Processar médicos
            if (equipe.getMedicos() == null) {
                equipe.setMedicos(new ArrayList<>());
            } else {
                equipe.getMedicos().clear();
            }
            
            if (equipeRequest.getMedicos() != null) {
                for (EquipeCirurgicaMedicoRequest medRequest : equipeRequest.getMedicos()) {
                    Medicos medico = medicoTenantEnforcer.validarAcesso(medRequest.getMedico(), tenantId);
                    
                    EquipeCirurgicaMedico equipeMedico = equipeCirurgicaMedicoMapper.fromRequest(medRequest);
                    equipeMedico.setEquipeCirurgica(equipe);
                    equipeMedico.setMedico(medico);
                    equipeMedico.setTenant(tenant);
                    equipeMedico.setActive(true);
                    
                    equipe.getMedicos().add(equipeMedico);
                }
            }

            entity.getEquipe().add(equipe);
        }
    }
}
