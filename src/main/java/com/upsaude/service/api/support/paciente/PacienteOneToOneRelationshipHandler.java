package com.upsaude.service.api.support.paciente;

import java.util.UUID;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.api.request.paciente.PacienteRequest;
import com.upsaude.api.request.paciente.ResponsavelLegalRequest;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.embeddable.InformacoesConvenioPaciente;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.paciente.ResponsavelLegal;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.embeddable.InformacoesConvenioPacienteMapper;
import com.upsaude.mapper.paciente.DadosSociodemograficosMapper;
import com.upsaude.mapper.paciente.ResponsavelLegalMapper;
import org.mapstruct.factory.Mappers;
import com.upsaude.repository.convenio.ConvenioRepository;
import com.upsaude.repository.paciente.DadosClinicosBasicosRepository;
import com.upsaude.repository.paciente.DadosSociodemograficosRepository;
import com.upsaude.repository.paciente.ResponsavelLegalRepository;
import com.upsaude.repository.sistema.lgpd.LGPDConsentimentoRepository;
import com.upsaude.repository.sistema.integracao.IntegracaoGovRepository;
import com.upsaude.service.api.sistema.multitenancy.TenantService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class PacienteOneToOneRelationshipHandler {

    private final ConvenioRepository convenioRepository;
    private final DadosSociodemograficosRepository dadosSociodemograficosRepository;
    private final DadosClinicosBasicosRepository dadosClinicosBasicosRepository;
    private final ResponsavelLegalRepository responsavelLegalRepository;
    private final LGPDConsentimentoRepository lgpdConsentimentoRepository;
    private final IntegracaoGovRepository integracaoGovRepository;
    private final DadosSociodemograficosMapper dadosSociodemograficosMapper;
    private final ResponsavelLegalMapper responsavelLegalMapper;
    private final TenantService tenantService;
    private static final InformacoesConvenioPacienteMapper informacoesConvenioMapper = Mappers.getMapper(InformacoesConvenioPacienteMapper.class);

    public PacienteOneToOneRelationshipHandler(
            ConvenioRepository convenioRepository,
            DadosSociodemograficosRepository dadosSociodemograficosRepository,
            DadosClinicosBasicosRepository dadosClinicosBasicosRepository,
            ResponsavelLegalRepository responsavelLegalRepository,
            LGPDConsentimentoRepository lgpdConsentimentoRepository,
            IntegracaoGovRepository integracaoGovRepository,
            DadosSociodemograficosMapper dadosSociodemograficosMapper,
            ResponsavelLegalMapper responsavelLegalMapper,
            TenantService tenantService) {
        this.convenioRepository = convenioRepository;
        this.dadosSociodemograficosRepository = dadosSociodemograficosRepository;
        this.dadosClinicosBasicosRepository = dadosClinicosBasicosRepository;
        this.responsavelLegalRepository = responsavelLegalRepository;
        this.lgpdConsentimentoRepository = lgpdConsentimentoRepository;
        this.integracaoGovRepository = integracaoGovRepository;
        this.dadosSociodemograficosMapper = dadosSociodemograficosMapper;
        this.responsavelLegalMapper = responsavelLegalMapper;
        this.tenantService = tenantService;
    }

    public Paciente processarRelacionamentos(Paciente paciente, PacienteRequest request) {
        processarRelacionamentosPorUuid(paciente, request);
        processarRelacionamentosNovos(paciente, request);
        return paciente;
    }

    public Paciente processarRelacionamentosPorUuid(Paciente paciente, PacienteRequest request) {
        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenio())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            paciente.setConvenio(convenio);

            if (request.getInformacoesConvenio() != null) {
                if (paciente.getInformacoesConvenio() == null) {

                    InformacoesConvenioPaciente informacoes = informacoesConvenioMapper.toEntity(request.getInformacoesConvenio());
                    paciente.setInformacoesConvenio(informacoes);
                } else {

                    informacoesConvenioMapper.updateFromRequest(request.getInformacoesConvenio(), paciente.getInformacoesConvenio());
                }
            }
        } else {

            paciente.setConvenio(null);
            paciente.setInformacoesConvenio(null);
        }

        return paciente;
    }

    public Paciente processarRelacionamentosNovos(Paciente paciente, PacienteRequest request) {

        processarDadosSociodemograficos(paciente, request);

        processarResponsavelLegal(paciente, request);

        return paciente;
    }

    private void processarDadosSociodemograficos(Paciente paciente, PacienteRequest request) {

        log.debug("Dados sociodemográficos devem ser processados através do endpoint específico para paciente ID: {}", paciente.getId());
    }

    private void processarResponsavelLegal(Paciente paciente, PacienteRequest request) {

        log.debug("Responsável legal deve ser processado através do endpoint específico para paciente ID: {}", paciente.getId());
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
