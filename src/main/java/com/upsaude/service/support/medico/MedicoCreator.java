package com.upsaude.service.support.medico;

import com.upsaude.api.request.MedicosRequest;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Tenant;
import com.upsaude.mapper.MedicosMapper;
import com.upsaude.repository.MedicosRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Objects;
import java.util.UUID;

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
        if (medicos.getDadosPessoais() == null) {
            medicos.setDadosPessoais(new com.upsaude.entity.embeddable.DadosPessoaisMedico());
        }
        if (medicos.getRegistroProfissional() == null) {
            medicos.setRegistroProfissional(new com.upsaude.entity.embeddable.RegistroProfissionalMedico());
        }
        if (medicos.getFormacao() == null) {
            medicos.setFormacao(new com.upsaude.entity.embeddable.FormacaoMedico());
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
