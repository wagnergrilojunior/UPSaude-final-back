package com.upsaude.service.api.support.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgica;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaMedico;
import com.upsaude.entity.clinica.cirurgia.EquipeCirurgicaProfissional;

import java.util.List;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.clinica.cirurgia.EquipeCirurgicaMapper;
import com.upsaude.mapper.clinica.cirurgia.EquipeCirurgicaMedicoMapper;
import com.upsaude.mapper.clinica.cirurgia.EquipeCirurgicaProfissionalMapper;
import com.upsaude.repository.clinica.cirurgia.EquipeCirurgicaMedicoRepository;
import com.upsaude.repository.clinica.cirurgia.EquipeCirurgicaProfissionalRepository;
import com.upsaude.repository.clinica.cirurgia.EquipeCirurgicaRepository;
import com.upsaude.service.api.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.api.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeCirurgicaUpdater {

    private final EquipeCirurgicaRepository repository;
    private final EquipeCirurgicaProfissionalRepository equipeCirurgicaProfissionalRepository;
    private final EquipeCirurgicaMedicoRepository equipeCirurgicaMedicoRepository;
    private final EntityManager entityManager;
    private final EquipeCirurgicaMapper mapper;
    private final EquipeCirurgicaTenantEnforcer tenantEnforcer;
    private final EquipeCirurgicaValidationService validationService;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final EquipeCirurgicaProfissionalMapper equipeCirurgicaProfissionalMapper;
    private final EquipeCirurgicaMedicoMapper equipeCirurgicaMedicoMapper;

    public EquipeCirurgica atualizar(UUID id, EquipeCirurgicaRequest request, UUID tenantId, Tenant tenant) {
        validationService.validarObrigatorios(request);

        EquipeCirurgica entity = tenantEnforcer.validarAcesso(id, tenantId);

        mapper.updateFromRequest(request, entity);
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório para atualizar equipe cirúrgica"));

        
        if (entity.getProfissionais() != null && !entity.getProfissionais().isEmpty()) {
            List<EquipeCirurgicaProfissional> profissionaisToRemove = new ArrayList<>(entity.getProfissionais());
            entity.getProfissionais().clear();
            entityManager.flush(); 
            equipeCirurgicaProfissionalRepository.deleteAll(profissionaisToRemove);
            entityManager.flush(); 
        }
        if (entity.getProfissionais() == null) {
            entity.setProfissionais(new ArrayList<>());
        }

        if (request.getProfissionais() != null && !request.getProfissionais().isEmpty()) {
            for (var profRequest : request.getProfissionais()) {
                ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(profRequest.getProfissional(), tenantId);
                
                EquipeCirurgicaProfissional equipeProfissional = equipeCirurgicaProfissionalMapper.fromRequest(profRequest);
                equipeProfissional.setEquipeCirurgica(entity);
                equipeProfissional.setProfissional(profissional);
                equipeProfissional.setTenant(tenant);
                equipeProfissional.setActive(true);
                
                entity.getProfissionais().add(equipeProfissional);
            }
        }

        
        if (entity.getMedicos() != null && !entity.getMedicos().isEmpty()) {
            List<EquipeCirurgicaMedico> medicosToRemove = new ArrayList<>(entity.getMedicos());
            entity.getMedicos().clear();
            entityManager.flush(); 
            equipeCirurgicaMedicoRepository.deleteAll(medicosToRemove);
            entityManager.flush(); 
        }
        if (entity.getMedicos() == null) {
            entity.setMedicos(new ArrayList<>());
        }

        if (request.getMedicos() != null && !request.getMedicos().isEmpty()) {
            for (var medRequest : request.getMedicos()) {
                Medicos medico = medicoTenantEnforcer.validarAcesso(medRequest.getMedico(), tenantId);
                
                EquipeCirurgicaMedico equipeMedico = equipeCirurgicaMedicoMapper.fromRequest(medRequest);
                equipeMedico.setEquipeCirurgica(entity);
                equipeMedico.setMedico(medico);
                equipeMedico.setTenant(tenant);
                equipeMedico.setActive(true);
                
                entity.getMedicos().add(equipeMedico);
            }
        }

        EquipeCirurgica updated = repository.save(Objects.requireNonNull(entity));
        log.info("Equipe cirúrgica atualizada com sucesso. ID: {}, tenant: {}", updated.getId(), tenantId);
        return updated;
    }
}

