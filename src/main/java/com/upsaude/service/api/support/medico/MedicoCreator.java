package com.upsaude.service.api.support.medico;

import java.util.Objects;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.upsaude.api.request.profissional.MedicosRequest;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.mapper.profissional.MedicosMapper;
import com.upsaude.repository.profissional.MedicosRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class MedicoCreator {

    private final MedicoValidationService validationService;
    private final MedicosMapper medicosMapper;
    private final MedicoRelacionamentosHandler relacionamentosHandler;
    private final MedicosRepository medicosRepository;

    public Medicos criar(MedicosRequest request, UUID tenantId, Tenant tenant) {
        log.debug("Criando novo médico. Request: {}", request);

        validationService.validarObrigatorios(request);
        validationService.validarUnicidadeParaCriacao(request, medicosRepository, tenantId);
        validationService.sanitizarFlags(request);

        Objects.requireNonNull(tenant, "tenant é obrigatório para criar médico");

        Medicos medicos = medicosMapper.fromRequest(request);
        medicos.setActive(true);
        medicos.setTenant(tenant);

        inicializarEmbeddables(medicos);
        sanitizarRegistroProfissional(medicos);

        relacionamentosHandler.processarRelacionamentos(medicos, request, tenant);

        Medicos medicosSalvo = medicosRepository.save(Objects.requireNonNull(medicos));
        log.info("Médico criado com sucesso. ID: {}, Tenant: {}", medicosSalvo.getId(), tenantId);

        return medicosSalvo;
    }

    private void inicializarEmbeddables(Medicos medicos) {
        if (medicos.getDadosPessoaisBasicos() == null) {
            medicos.setDadosPessoaisBasicos(new com.upsaude.entity.embeddable.DadosPessoaisBasicosMedico());
        }
        if (medicos.getDocumentosBasicos() == null) {
            medicos.setDocumentosBasicos(new com.upsaude.entity.embeddable.DocumentosBasicosMedico());
        }
        if (medicos.getDadosDemograficos() == null) {
            medicos.setDadosDemograficos(new com.upsaude.entity.embeddable.DadosDemograficosMedico());
        }
        if (medicos.getRegistroProfissional() == null) {
            medicos.setRegistroProfissional(new com.upsaude.entity.embeddable.RegistroProfissionalMedico());
        }
        if (medicos.getContato() == null) {
            medicos.setContato(new com.upsaude.entity.embeddable.ContatoMedico());
        }
    }

    private void sanitizarRegistroProfissional(Medicos medicos) {
        if (medicos.getRegistroProfissional() != null) {
            if (!StringUtils.hasText(medicos.getRegistroProfissional().getCrm())) {
                medicos.getRegistroProfissional().setCrm(null);
            }
            if (!StringUtils.hasText(medicos.getRegistroProfissional().getCrmUf())) {
                medicos.getRegistroProfissional().setCrmUf(null);
            }
        }
    }
}
