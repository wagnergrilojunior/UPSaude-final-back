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
            
            // Processar informações do convênio se fornecidas
            if (request.getInformacoesConvenio() != null) {
                if (paciente.getInformacoesConvenio() == null) {
                    // Criar nova instância se não existe
                    InformacoesConvenioPaciente informacoes = informacoesConvenioMapper.toEntity(request.getInformacoesConvenio());
                    paciente.setInformacoesConvenio(informacoes);
                } else {
                    // Atualizar instância existente
                    informacoesConvenioMapper.updateFromRequest(request.getInformacoesConvenio(), paciente.getInformacoesConvenio());
                }
            }
        } else {
            // Se não há convênio, limpar informações do convênio
            paciente.setConvenio(null);
            paciente.setInformacoesConvenio(null);
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

        // Integrações governamentais agora são OneToMany
        // Se necessário, processar múltiplas integrações no futuro
        if (request.getIntegracaoGov() != null) {
            com.upsaude.entity.sistema.integracao.IntegracaoGov integracao = integracaoGovRepository.findById(request.getIntegracaoGov())
                    .orElseThrow(() -> new NotFoundException("Integração governamental não encontrada com ID: " + request.getIntegracaoGov()));
            integracao.setPaciente(paciente);
            if (paciente.getIntegracoesGov() == null) {
                paciente.setIntegracoesGov(new java.util.ArrayList<>());
            }
            if (!paciente.getIntegracoesGov().contains(integracao)) {
                paciente.getIntegracoesGov().add(integracao);
            }
        }

        return paciente;
    }

    public Paciente processarRelacionamentosNovos(Paciente paciente, PacienteRequest request) {
        // Processar dados sociodemográficos (criar nova entidade se necessário)
        processarDadosSociodemograficos(paciente, request);

        // Processar responsável legal (criar nova entidade se necessário)
        processarResponsavelLegal(paciente, request);

        return paciente;
    }

    private void processarDadosSociodemograficos(Paciente paciente, PacienteRequest request) {
        // Se foi fornecido UUID, buscar entidade existente
        if (request.getDadosSociodemograficos() != null) {
            DadosSociodemograficos dados = dadosSociodemograficosRepository.findById(request.getDadosSociodemograficos())
                    .orElseThrow(() -> new NotFoundException("Dados sociodemográficos não encontrados com ID: " + request.getDadosSociodemograficos()));
            dados.setPaciente(paciente);
            paciente.setDadosSociodemograficos(dados);
            return;
        }

        // Verificar se há campos diretos no request para criar nova entidade
        boolean temDados = request.getEscolaridade() != null ||
                           request.getRacaCor() != null ||
                           request.getNacionalidade() != null ||
                           (request.getPaisNascimento() != null && !request.getPaisNascimento().trim().isEmpty()) ||
                           (request.getNaturalidade() != null && !request.getNaturalidade().trim().isEmpty()) ||
                           (request.getOcupacaoProfissao() != null && !request.getOcupacaoProfissao().trim().isEmpty()) ||
                           request.getSituacaoRua() != null ||
                           request.getEstadoCivil() != null;

        if (!temDados) {
            log.debug("Nenhum dado sociodemográfico para processar para paciente ID: {}", paciente.getId());
            return;
        }

        log.debug("Criando dados sociodemográficos a partir dos campos do request para paciente ID: {}", paciente.getId());

        try {
            // Verificar se já existe registro (só funciona se paciente já foi salvo)
            DadosSociodemograficos dados = null;
            if (paciente.getId() != null) {
                dados = dadosSociodemograficosRepository
                        .findByPacienteId(paciente.getId())
                        .orElse(null);
            }
            if (dados == null) {
                dados = new DadosSociodemograficos();
            }

            // Criar DTO a partir dos campos do request
            DadosSociodemograficosRequest dadosRequest = DadosSociodemograficosRequest.builder()
                    .escolaridade(request.getEscolaridade())
                    .racaCor(request.getRacaCor())
                    .nacionalidade(request.getNacionalidade())
                    .paisNascimento(request.getPaisNascimento())
                    .naturalidade(request.getNaturalidade())
                    .ocupacaoProfissao(request.getOcupacaoProfissao())
                    .situacaoRua(request.getSituacaoRua())
                    .build();

            // Mapear para entidade
            if (dados.getId() == null) {
                DadosSociodemograficos novosDados = dadosSociodemograficosMapper.fromRequest(dadosRequest);
                dados.setRacaCor(novosDados.getRacaCor());
                dados.setNacionalidade(novosDados.getNacionalidade());
                dados.setPaisNascimento(novosDados.getPaisNascimento());
                dados.setNaturalidade(novosDados.getNaturalidade());
                dados.setEscolaridade(novosDados.getEscolaridade());
                dados.setOcupacaoProfissao(novosDados.getOcupacaoProfissao());
                dados.setSituacaoRua(novosDados.getSituacaoRua());
            } else {
                dadosSociodemograficosMapper.updateFromRequest(dadosRequest, dados);
            }

            dados.setPaciente(paciente);
            dados.setActive(true);

            // Definir tenant se não tiver
            if (dados.getTenant() == null) {
                Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
                if (tenant != null) {
                    dados.setTenant(tenant);
                }
            }

            // Não salvar aqui - será salvo pelo cascade quando o paciente for salvo
            // Apenas definir o relacionamento
            paciente.setDadosSociodemograficos(dados);

            log.info("Dados sociodemográficos criados/atualizados para paciente ID: {}", paciente.getId());
        } catch (Exception e) {
            log.warn("Erro ao criar dados sociodemográficos para paciente ID: {}. Erro: {}", 
                    paciente.getId(), e.getMessage());
            // Não lança exceção para não bloquear a criação do paciente
        }
    }

    private void processarResponsavelLegal(Paciente paciente, PacienteRequest request) {
        // Se foi fornecido UUID, buscar entidade existente
        if (request.getResponsavelLegal() != null) {
            ResponsavelLegal responsavel = responsavelLegalRepository.findById(request.getResponsavelLegal())
                    .orElseThrow(() -> new NotFoundException("Responsável legal não encontrado com ID: " + request.getResponsavelLegal()));
            responsavel.setPaciente(paciente);
            paciente.setResponsavelLegal(responsavel);
            return;
        }

        // Verificar se há campos diretos no request para criar nova entidade
        boolean temDados = (request.getResponsavelNome() != null && !request.getResponsavelNome().trim().isEmpty()) ||
                           (request.getResponsavelCpf() != null && !request.getResponsavelCpf().trim().isEmpty()) ||
                           (request.getResponsavelTelefone() != null && !request.getResponsavelTelefone().trim().isEmpty());

        if (!temDados) {
            log.debug("Nenhum dado de responsável legal para processar para paciente ID: {}", paciente.getId());
            return;
        }

        log.debug("Criando responsável legal a partir dos campos do request para paciente ID: {}", paciente.getId());

        try {
            // Verificar se já existe registro (só funciona se paciente já foi salvo)
            ResponsavelLegal responsavel = null;
            if (paciente.getId() != null) {
                responsavel = responsavelLegalRepository
                        .findByPacienteId(paciente.getId())
                        .orElse(null);
            }
            if (responsavel == null) {
                responsavel = new ResponsavelLegal();
            }

            // Criar DTO a partir dos campos do request
            ResponsavelLegalRequest responsavelRequest = ResponsavelLegalRequest.builder()
                    .nome(request.getResponsavelNome())
                    .cpf(request.getResponsavelCpf())
                    .telefone(request.getResponsavelTelefone())
                    .build();

            // Mapear para entidade
            if (responsavel.getId() == null) {
                ResponsavelLegal novoResponsavel = responsavelLegalMapper.fromRequest(responsavelRequest);
                responsavel.setNome(novoResponsavel.getNome());
                responsavel.setCpf(novoResponsavel.getCpf());
                responsavel.setTelefone(novoResponsavel.getTelefone());
                responsavel.setRg(novoResponsavel.getRg());
                responsavel.setCns(novoResponsavel.getCns());
                responsavel.setTipoResponsavel(novoResponsavel.getTipoResponsavel());
                responsavel.setAutorizacaoResponsavel(novoResponsavel.getAutorizacaoResponsavel());
            } else {
                responsavelLegalMapper.updateFromRequest(responsavelRequest, responsavel);
            }

            responsavel.setPaciente(paciente);
            responsavel.setActive(true);

            // Definir tenant se não tiver
            if (responsavel.getTenant() == null) {
                Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
                if (tenant != null) {
                    responsavel.setTenant(tenant);
                }
            }

            // Não salvar aqui - será salvo pelo cascade quando o paciente for salvo
            // Apenas definir o relacionamento
            paciente.setResponsavelLegal(responsavel);

            log.info("Responsável legal criado/atualizado para paciente ID: {}", paciente.getId());
        } catch (Exception e) {
            log.warn("Erro ao criar responsável legal para paciente ID: {}. Erro: {}", 
                    paciente.getId(), e.getMessage());
            // Não lança exceção para não bloquear a criação do paciente
        }
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
