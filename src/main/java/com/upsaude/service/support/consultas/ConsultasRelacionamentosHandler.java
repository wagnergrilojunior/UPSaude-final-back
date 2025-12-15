package com.upsaude.service.support.consultas;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Consultas;
import com.upsaude.entity.EspecialidadesMedicas;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.repository.EspecialidadesMedicasRepository;
import com.upsaude.service.support.convenio.ConvenioTenantEnforcer;
import com.upsaude.service.support.medico.MedicoTenantEnforcer;
import com.upsaude.service.support.paciente.PacienteTenantEnforcer;
import com.upsaude.service.support.profissionaissaude.ProfissionaisSaudeTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConsultasRelacionamentosHandler {

    private final PacienteTenantEnforcer pacienteTenantEnforcer;
    private final MedicoTenantEnforcer medicoTenantEnforcer;
    private final ProfissionaisSaudeTenantEnforcer profissionaisSaudeTenantEnforcer;
    private final ConvenioTenantEnforcer convenioTenantEnforcer;
    private final EspecialidadesMedicasRepository especialidadesMedicasRepository;
    private final CidDoencasRepository cidDoencasRepository;

    public void resolver(Consultas entity, ConsultasRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        UUID pacienteId = Objects.requireNonNull(request.getPaciente(), "paciente é obrigatório");
        entity.setPaciente(pacienteTenantEnforcer.validarAcesso(pacienteId, tenantId));

        if (request.getMedico() != null) {
            Medicos medico = medicoTenantEnforcer.validarAcesso(request.getMedico(), tenantId);
            entity.setMedico(medico);
        } else {
            entity.setMedico(null);
        }

        if (request.getProfissionalSaude() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeTenantEnforcer.validarAcesso(request.getProfissionalSaude(), tenantId);
            entity.setProfissionalSaude(profissional);
        } else {
            entity.setProfissionalSaude(null);
        }

        if (request.getConvenio() != null) {
            entity.setConvenio(convenioTenantEnforcer.validarAcesso(request.getConvenio(), tenantId));
        } else {
            entity.setConvenio(null);
        }

        if (request.getEspecialidade() != null) {
            UUID especialidadeId = Objects.requireNonNull(request.getEspecialidade(), "especialidade");
            EspecialidadesMedicas especialidade = especialidadesMedicasRepository.findById(especialidadeId)
                .orElseThrow(() -> new NotFoundException("Especialidade não encontrada com ID: " + especialidadeId));
            entity.setEspecialidade(especialidade);
        } else {
            entity.setEspecialidade(null);
        }

        if (request.getCidPrincipal() != null) {
            UUID cidId = Objects.requireNonNull(request.getCidPrincipal(), "cidPrincipal");
            CidDoencas cid = cidDoencasRepository.findById(cidId)
                .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + cidId));
            entity.setCidPrincipal(cid);
        } else {
            entity.setCidPrincipal(null);
        }

        if (entity.getProfissionalSaude() != null && entity.getProfissionalSaude().getEstabelecimento() != null) {
            entity.setEstabelecimento(entity.getProfissionalSaude().getEstabelecimento());
        } else {
            entity.setEstabelecimento(null);
        }
    }
}
