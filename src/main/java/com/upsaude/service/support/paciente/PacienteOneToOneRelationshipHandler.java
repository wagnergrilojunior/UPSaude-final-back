package com.upsaude.service.support.paciente;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.entity.*;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.*;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.function.Consumer;

@Service
public class PacienteOneToOneRelationshipHandler {

    private final ConvenioRepository convenioRepository;
    private final DadosSociodemograficosRepository dadosSociodemograficosRepository;
    private final DadosClinicosBasicosRepository dadosClinicosBasicosRepository;
    private final ResponsavelLegalRepository responsavelLegalRepository;
    private final LGPDConsentimentoRepository lgpdConsentimentoRepository;
    private final IntegracaoGovRepository integracaoGovRepository;

    public PacienteOneToOneRelationshipHandler(
            ConvenioRepository convenioRepository,
            DadosSociodemograficosRepository dadosSociodemograficosRepository,
            DadosClinicosBasicosRepository dadosClinicosBasicosRepository,
            ResponsavelLegalRepository responsavelLegalRepository,
            LGPDConsentimentoRepository lgpdConsentimentoRepository,
            IntegracaoGovRepository integracaoGovRepository) {
        this.convenioRepository = convenioRepository;
        this.dadosSociodemograficosRepository = dadosSociodemograficosRepository;
        this.dadosClinicosBasicosRepository = dadosClinicosBasicosRepository;
        this.responsavelLegalRepository = responsavelLegalRepository;
        this.lgpdConsentimentoRepository = lgpdConsentimentoRepository;
        this.integracaoGovRepository = integracaoGovRepository;
    }

    public Paciente processarRelacionamentos(Paciente paciente, PacienteRequest request) {
        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenio())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            paciente.setConvenio(convenio);
        }

        processarRelacionamentoOneToOne(request.getDadosSociodemograficos(), dadosSociodemograficosRepository, dados -> {
            dados.setPaciente(paciente);
            paciente.setDadosSociodemograficos(dados);
        }, "Dados sociodemográficos");

        processarRelacionamentoOneToOne(request.getDadosClinicosBasicos(), dadosClinicosBasicosRepository, dados -> {
            dados.setPaciente(paciente);
            paciente.setDadosClinicosBasicos(dados);
        }, "Dados clínicos básicos");

        processarRelacionamentoOneToOne(request.getResponsavelLegal(), responsavelLegalRepository, responsavel -> {
            responsavel.setPaciente(paciente);
            paciente.setResponsavelLegal(responsavel);
        }, "Responsável legal");

        processarRelacionamentoOneToOne(request.getLgpdConsentimento(), lgpdConsentimentoRepository, consentimento -> {
            consentimento.setPaciente(paciente);
            paciente.setLgpdConsentimento(consentimento);
        }, "Consentimento LGPD");

        processarRelacionamentoOneToOne(request.getIntegracaoGov(), integracaoGovRepository, integracao -> {
            integracao.setPaciente(paciente);
            paciente.setIntegracaoGov(integracao);
        }, "Integração governamental");

        return paciente;
    }

    public <T> void processarRelacionamentoOneToOne(
            UUID uuid,
            org.springframework.data.jpa.repository.JpaRepository<T, UUID> repository,
            Consumer<T> sincronizador,
            String nomeEntidade) {

        if (uuid != null) {
            T entidade = repository.findById(uuid)
                    .orElseThrow(() -> new NotFoundException(nomeEntidade + " não encontrado com ID: " + uuid));
            sincronizador.accept(entidade);
        }
    }
}
