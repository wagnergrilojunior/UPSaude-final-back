package com.upsaude.service.impl;

import java.time.LocalDate;
import java.time.Period;
import java.util.UUID;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.request.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.api.response.PacienteSimplificadoResponse;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.Tenant;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.mapper.PacienteMapper;
import com.upsaude.repository.CidadesRepository;
import com.upsaude.repository.ConvenioRepository;
import com.upsaude.repository.DadosClinicosBasicosRepository;
import com.upsaude.repository.DadosSociodemograficosRepository;
import com.upsaude.repository.EnderecoRepository;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.repository.IntegracaoGovRepository;
import com.upsaude.repository.LGPDConsentimentoRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ResponsavelLegalRepository;
import com.upsaude.service.AlergiasPacienteService;
import com.upsaude.service.DeficienciasPacienteService;
import com.upsaude.service.DoencasPacienteService;
import com.upsaude.service.EnderecoService;
import com.upsaude.service.MedicacaoPacienteService;
import com.upsaude.service.PacienteService;
import com.upsaude.service.TenantService;
import com.upsaude.helper.ValidadorHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    private final EnderecoMapper enderecoMapper;
    private final EnderecoRepository enderecoRepository;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;

    private final ConvenioRepository convenioRepository;
    private final DadosSociodemograficosRepository dadosSociodemograficosRepository;
    private final DadosClinicosBasicosRepository dadosClinicosBasicosRepository;
    private final ResponsavelLegalRepository responsavelLegalRepository;
    private final LGPDConsentimentoRepository lgpdConsentimentoRepository;
    private final IntegracaoGovRepository integracaoGovRepository;

    private final AlergiasPacienteService alergiasPacienteService;
    private final DeficienciasPacienteService deficienciasPacienteService;
    private final DoencasPacienteService doencasPacienteService;
    private final MedicacaoPacienteService medicacaoPacienteService;
    private final EnderecoService enderecoService;
    private final TenantService tenantService;

    @Autowired
    @Lazy
    private PacienteService self;

    @Override
    @Transactional
    @CachePut(value = "paciente", key = "#result.id")
    public PacienteResponse criar(PacienteRequest request) {
        log.debug("Criando novo paciente: {}", request != null ? request.getNomeCompleto() : "null");

        try {
            if (request == null || !StringUtils.hasText(request.getNomeCompleto())) {
                throw new BadRequestException("Nome completo é obrigatório");
            }

            if (StringUtils.hasText(request.getRg()) && request.getRg().length() > 20) {
                throw new BadRequestException("RG deve ter no máximo 20 caracteres");
            }

            ValidadorHelper.validarCpfUnicoPaciente(null, request.getCpf(), pacienteRepository);
            ValidadorHelper.validarEmailUnicoPaciente(null, request.getEmail(), pacienteRepository);
            ValidadorHelper.validarCnsUnicoPaciente(null, request.getCns(), pacienteRepository);
            ValidadorHelper.validarRgUnicoPaciente(null, request.getRg(), pacienteRepository);

            Paciente paciente = pacienteMapper.fromRequest(request);
            paciente.setActive(true);

            if (paciente.getAcompanhadoPorEquipeEsf() == null) {
                paciente.setAcompanhadoPorEquipeEsf(false);
            }
            if (paciente.getPossuiDeficiencia() == null) {
                paciente.setPossuiDeficiencia(false);
            }
            if (paciente.getCnsValidado() == null) {
                paciente.setCnsValidado(false);
            }
            if (paciente.getSituacaoRua() == null) {
                paciente.setSituacaoRua(false);
            }
            if (paciente.getCartaoSusAtivo() == null) {
                paciente.setCartaoSusAtivo(true);
            }
            if (paciente.getStatusPaciente() == null) {
                paciente.setStatusPaciente(StatusPacienteEnum.ATIVO);
            }

            processarRelacionamentos(paciente, request);

            Paciente pacienteSalvo;
            try {
                pacienteSalvo = pacienteRepository.save(paciente);
            } catch (DataIntegrityViolationException e) {
                if (e.getMessage() != null && e.getMessage().contains("pacientes_enderecos_endereco_id_key")) {
                    log.warn("Erro ao associar endereços: endereço já associado a outro paciente. Criando novos endereços.");
                    pacienteSalvo = pacienteRepository.save(paciente);
                } else {
                    throw e;
                }
            }
            log.info("Paciente criado com sucesso. ID: {} - Será adicionado ao cache automaticamente", pacienteSalvo.getId());

            processarRelacionamentosPorId(pacienteSalvo, request);

            PacienteResponse response = pacienteMapper.toResponse(pacienteSalvo);
            calcularIdade(response);
            log.debug("Paciente criado adicionado ao cache com chave: {}", response.getId());
            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar Paciente. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao persistir Paciente", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar Paciente. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "paciente", key = "#id")
    public PacienteResponse obterPorId(UUID id) {
        log.debug("Buscando paciente por ID: {} - Verificando cache primeiro", id);
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        log.debug("Cache MISS para paciente ID: {} - Buscando no banco de dados", id);

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        Hibernate.initialize(paciente.getEnderecos());
        Hibernate.initialize(paciente.getDoencas());
        Hibernate.initialize(paciente.getAlergias());
        Hibernate.initialize(paciente.getDeficiencias());
        Hibernate.initialize(paciente.getMedicacoes());
        Hibernate.initialize(paciente.getDadosSociodemograficos());
        Hibernate.initialize(paciente.getDadosClinicosBasicos());
        Hibernate.initialize(paciente.getResponsavelLegal());
        Hibernate.initialize(paciente.getLgpdConsentimento());
        Hibernate.initialize(paciente.getIntegracaoGov());

        if (paciente.getDoencas() != null) {
            paciente.getDoencas().forEach(doenca -> {
                if (doenca.getDoenca() != null) {
                    Hibernate.initialize(doenca.getDoenca());
                }
                if (doenca.getCidPrincipal() != null) {
                    Hibernate.initialize(doenca.getCidPrincipal());
                }
            });
        }

        if (paciente.getAlergias() != null) {
            paciente.getAlergias().forEach(alergia -> {
                if (alergia.getAlergia() != null) {
                    Hibernate.initialize(alergia.getAlergia());
                }
            });
        }

        if (paciente.getMedicacoes() != null) {
            paciente.getMedicacoes().forEach(medicacao -> {
                if (medicacao.getMedicacao() != null) {
                    Hibernate.initialize(medicacao.getMedicacao());

                    if (medicacao.getMedicacao().getIdentificacao() != null) {
                        Hibernate.initialize(medicacao.getMedicacao().getIdentificacao());
                    }
                    if (medicacao.getMedicacao().getFabricanteEntity() != null) {
                        Hibernate.initialize(medicacao.getMedicacao().getFabricanteEntity());
                    }
                }
            });
        }

        PacienteResponse response = pacienteMapper.toResponse(paciente);
        calcularIdade(response);
        return response;
    }

    private PacienteSimplificadoResponse converterParaSimplificado(Paciente paciente) {
        return PacienteSimplificadoResponse.builder()
                .id(paciente.getId())
                .createdAt(paciente.getCreatedAt())
                .updatedAt(paciente.getUpdatedAt())
                .active(paciente.getActive())
                .nomeCompleto(paciente.getNomeCompleto())
                .cpf(paciente.getCpf())
                .rg(paciente.getRg())
                .cns(paciente.getCns())
                .dataNascimento(paciente.getDataNascimento())
                .sexo(paciente.getSexo())
                .estadoCivil(paciente.getEstadoCivil())
                .telefone(paciente.getTelefone())
                .email(paciente.getEmail())
                .nomeMae(paciente.getNomeMae())
                .nomePai(paciente.getNomePai())
                .responsavelNome(paciente.getResponsavelNome())
                .responsavelCpf(paciente.getResponsavelCpf())
                .responsavelTelefone(paciente.getResponsavelTelefone())
                .numeroCarteirinha(paciente.getNumeroCarteirinha())
                .dataValidadeCarteirinha(paciente.getDataValidadeCarteirinha())
                .observacoes(paciente.getObservacoes())
                .racaCor(paciente.getRacaCor())
                .nacionalidade(paciente.getNacionalidade())
                .paisNascimento(paciente.getPaisNascimento())
                .naturalidade(paciente.getNaturalidade())
                .municipioNascimentoIbge(paciente.getMunicipioNascimentoIbge())
                .escolaridade(paciente.getEscolaridade())
                .ocupacaoProfissao(paciente.getOcupacaoProfissao())
                .situacaoRua(paciente.getSituacaoRua())
                .statusPaciente(paciente.getStatusPaciente())
                .dataObito(paciente.getDataObito())
                .causaObitoCid10(paciente.getCausaObitoCid10())
                .cartaoSusAtivo(paciente.getCartaoSusAtivo())
                .dataAtualizacaoCns(paciente.getDataAtualizacaoCns())
                .tipoAtendimentoPreferencial(paciente.getTipoAtendimentoPreferencial())
                .origemCadastro(paciente.getOrigemCadastro())
                .nomeSocial(paciente.getNomeSocial())
                .identidadeGenero(paciente.getIdentidadeGenero())
                .orientacaoSexual(paciente.getOrientacaoSexual())
                .possuiDeficiencia(paciente.getPossuiDeficiencia())
                .tipoDeficiencia(paciente.getTipoDeficiencia())
                .cnsValidado(paciente.getCnsValidado())
                .tipoCns(paciente.getTipoCns())
                .acompanhadoPorEquipeEsf(paciente.getAcompanhadoPorEquipeEsf())
                .build();
    }

    private PacienteSimplificadoResponse converterProjecaoParaSimplificado(
            com.upsaude.repository.projection.PacienteSimplificadoProjection projecao) {
        return PacienteSimplificadoResponse.builder()
                .id(projecao.getId())
                .createdAt(projecao.getCreatedAt())
                .updatedAt(projecao.getUpdatedAt())
                .active(projecao.getActive())
                .nomeCompleto(projecao.getNomeCompleto())
                .cpf(projecao.getCpf())
                .rg(projecao.getRg())
                .cns(projecao.getCns())
                .dataNascimento(projecao.getDataNascimento())
                .sexo(projecao.getSexo())
                .estadoCivil(projecao.getEstadoCivil())
                .telefone(projecao.getTelefone())
                .email(projecao.getEmail())
                .nomeMae(projecao.getNomeMae())
                .nomePai(projecao.getNomePai())
                .responsavelNome(projecao.getResponsavelNome())
                .responsavelCpf(projecao.getResponsavelCpf())
                .responsavelTelefone(projecao.getResponsavelTelefone())
                .numeroCarteirinha(projecao.getNumeroCarteirinha())
                .dataValidadeCarteirinha(projecao.getDataValidadeCarteirinha())
                .observacoes(projecao.getObservacoes())
                .racaCor(projecao.getRacaCor())
                .nacionalidade(projecao.getNacionalidade())
                .paisNascimento(projecao.getPaisNascimento())
                .naturalidade(projecao.getNaturalidade())
                .municipioNascimentoIbge(projecao.getMunicipioNascimentoIbge())
                .escolaridade(projecao.getEscolaridade())
                .ocupacaoProfissao(projecao.getOcupacaoProfissao())
                .situacaoRua(projecao.getSituacaoRua())
                .statusPaciente(projecao.getStatusPaciente())
                .dataObito(projecao.getDataObito())
                .causaObitoCid10(projecao.getCausaObitoCid10())
                .cartaoSusAtivo(projecao.getCartaoSusAtivo())
                .dataAtualizacaoCns(projecao.getDataAtualizacaoCns())
                .tipoAtendimentoPreferencial(projecao.getTipoAtendimentoPreferencial())
                .origemCadastro(projecao.getOrigemCadastro())
                .nomeSocial(projecao.getNomeSocial())
                .identidadeGenero(projecao.getIdentidadeGenero())
                .orientacaoSexual(projecao.getOrientacaoSexual())
                .possuiDeficiencia(projecao.getPossuiDeficiencia())
                .tipoDeficiencia(projecao.getTipoDeficiencia())
                .cnsValidado(projecao.getCnsValidado())
                .tipoCns(projecao.getTipoCns())
                .acompanhadoPorEquipeEsf(projecao.getAcompanhadoPorEquipeEsf())
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponse> listar(Pageable pageable) {
        log.debug("Listando pacientes paginados. Página: {}, Tamanho: {}",  pageable.getPageNumber(), pageable.getPageSize());

        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);

        return pacientes.map(paciente -> {
            PacienteResponse response = pacienteMapper.toResponse(paciente);
            calcularIdade(response);
            return response;
        });
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PacienteSimplificadoResponse> listarSimplificado(Pageable pageable) {
        log.debug("Listando pacientes simplificados paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<com.upsaude.repository.projection.PacienteSimplificadoProjection> projecoes =
                pacienteRepository.findAllSimplificado(pageable);

        return projecoes.map(this::converterProjecaoParaSimplificado);
    }

    @Override
    @Transactional
    @CachePut(value = "paciente", key = "#id")
    public PacienteResponse atualizar(UUID id, PacienteRequest request) {
        log.debug("Atualizando paciente. ID: {} - Cache será atualizado automaticamente", id);

        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        ValidadorHelper.validarCpfUnicoPaciente(id, request.getCpf(), pacienteRepository);
        ValidadorHelper.validarEmailUnicoPaciente(id, request.getEmail(), pacienteRepository);
        ValidadorHelper.validarCnsUnicoPaciente(id, request.getCns(), pacienteRepository);
        ValidadorHelper.validarRgUnicoPaciente(id, request.getRg(), pacienteRepository);

        atualizarDadosPaciente(pacienteExistente, request);

        processarRelacionamentos(pacienteExistente, request);

        Paciente pacienteAtualizado = pacienteRepository.save(pacienteExistente);
        log.info("Paciente atualizado com sucesso. ID: {} - Cache será atualizado automaticamente", pacienteAtualizado.getId());

        processarRelacionamentosPorId(pacienteAtualizado, request);

        PacienteResponse response = pacienteMapper.toResponse(pacienteAtualizado);
        calcularIdade(response);
        log.debug("Paciente atualizado no cache com chave: {}", id);
        return response;
    }

    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void excluir(UUID id) {
        inativar(id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void inativar(UUID id) {
        log.debug("Inativando paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(paciente.getActive())) {
            throw new BadRequestException("Paciente já está inativo");
        }

        paciente.setActive(false);
        pacienteRepository.save(paciente);
        log.info("Paciente inativado com sucesso. ID: {}", id);
    }

    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void deletarPermanentemente(UUID id) {
        log.debug("Deletando paciente permanentemente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        pacienteRepository.delete(paciente);
        log.info("Paciente deletado permanentemente do banco de dados. ID: {}", id);
    }

    private void atualizarDadosPaciente(Paciente paciente, PacienteRequest request) {
        pacienteMapper.updateFromRequest(request, paciente);
    }

    private void processarRelacionamentos(Paciente paciente, PacienteRequest request) {
        log.debug("Processando relacionamentos do paciente");

        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenio())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            paciente.setConvenio(convenio);
        }

        processarRelacionamentoOneToOne(
            request.getDadosSociodemograficos(),
            dadosSociodemograficosRepository,
            dados -> {
                dados.setPaciente(paciente);
                paciente.setDadosSociodemograficos(dados);
            },
            "Dados sociodemográficos"
        );

        processarRelacionamentoOneToOne(
            request.getDadosClinicosBasicos(),
            dadosClinicosBasicosRepository,
            dados -> {
                dados.setPaciente(paciente);
                paciente.setDadosClinicosBasicos(dados);
            },
            "Dados clínicos básicos"
        );

        processarRelacionamentoOneToOne(
            request.getResponsavelLegal(),
            responsavelLegalRepository,
            responsavel -> {
                responsavel.setPaciente(paciente);
                paciente.setResponsavelLegal(responsavel);
            },
            "Responsável legal"
        );

        processarRelacionamentoOneToOne(
            request.getLgpdConsentimento(),
            lgpdConsentimentoRepository,
            consentimento -> {
                consentimento.setPaciente(paciente);
                paciente.setLgpdConsentimento(consentimento);
            },
            "Consentimento LGPD"
        );

        processarRelacionamentoOneToOne(
            request.getIntegracaoGov(),
            integracaoGovRepository,
            integracao -> {
                integracao.setPaciente(paciente);
                paciente.setIntegracaoGov(integracao);
            },
            "Integração governamental"
        );

        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
    }

    private void processarRelacionamentosPorId(Paciente paciente, PacienteRequest request) {
        log.debug("Processando relacionamentos por ID do paciente: {}", paciente.getId());

        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar relacionamentos.");
        }

        if (request.getDoencas() != null && !request.getDoencas().isEmpty()) {
            log.debug("Processando {} doença(s) para o paciente", request.getDoencas().size());
            for (UUID doencaId : request.getDoencas()) {
                try {
                    DoencasPacienteSimplificadoRequest doencaRequest = DoencasPacienteSimplificadoRequest.builder()
                            .paciente(paciente.getId())
                            .tenant(tenant.getId())
                            .doenca(doencaId)
                            .build();
                    doencasPacienteService.criarSimplificado(doencaRequest);
                    log.debug("Doença {} associada ao paciente {}", doencaId, paciente.getId());
                } catch (BadRequestException | NotFoundException e) {
                    log.warn("Erro ao associar doença {} ao paciente {}. Erro: {}",
                        doencaId, paciente.getId(), e.getMessage());
                    throw e;
                } catch (RuntimeException e) {
                    log.error("Erro inesperado ao associar doença {} ao paciente {}. Exception: {}",
                        doencaId, paciente.getId(), e.getClass().getSimpleName(), e);
                    throw new BadRequestException("Erro ao associar doença " + doencaId + ": " + e.getMessage(), e);
                }
            }
        }

        if (request.getAlergias() != null && !request.getAlergias().isEmpty()) {
            log.debug("Processando {} alergia(s) para o paciente", request.getAlergias().size());
            for (UUID alergiaId : request.getAlergias()) {
                try {
                    AlergiasPacienteSimplificadoRequest alergiaRequest = AlergiasPacienteSimplificadoRequest.builder()
                            .paciente(paciente.getId())
                            .tenant(tenant.getId())
                            .alergia(alergiaId)
                            .build();
                    alergiasPacienteService.criarSimplificado(alergiaRequest);
                    log.debug("Alergia {} associada ao paciente {}", alergiaId, paciente.getId());
                } catch (BadRequestException | NotFoundException e) {
                    log.warn("Erro ao associar alergia {} ao paciente {}. Erro: {}",
                        alergiaId, paciente.getId(), e.getMessage());
                    throw e;
                } catch (RuntimeException e) {
                    log.error("Erro inesperado ao associar alergia {} ao paciente {}. Exception: {}",
                        alergiaId, paciente.getId(), e.getClass().getSimpleName(), e);
                    throw new BadRequestException("Erro ao associar alergia " + alergiaId + ": " + e.getMessage(), e);
                }
            }
        }

        if (request.getDeficiencias() != null && !request.getDeficiencias().isEmpty()) {
            log.debug("Processando {} deficiência(s) para o paciente", request.getDeficiencias().size());
            for (UUID deficienciaId : request.getDeficiencias()) {
                try {
                    DeficienciasPacienteSimplificadoRequest deficienciaRequest = DeficienciasPacienteSimplificadoRequest.builder()
                            .paciente(paciente.getId())
                            .tenant(tenant.getId())
                            .deficiencia(deficienciaId)
                            .build();
                    deficienciasPacienteService.criarSimplificado(deficienciaRequest);
                    log.debug("Deficiência {} associada ao paciente {}", deficienciaId, paciente.getId());
                } catch (BadRequestException | NotFoundException e) {
                    log.warn("Erro ao associar deficiência {} ao paciente {}. Erro: {}",
                        deficienciaId, paciente.getId(), e.getMessage());
                    throw e;
                } catch (RuntimeException e) {
                    log.error("Erro inesperado ao associar deficiência {} ao paciente {}. Exception: {}",
                        deficienciaId, paciente.getId(), e.getClass().getSimpleName(), e);
                    throw new BadRequestException("Erro ao associar deficiência " + deficienciaId + ": " + e.getMessage(), e);
                }
            }
        }

        if (request.getMedicacoes() != null && !request.getMedicacoes().isEmpty()) {
            log.debug("Processando {} medicação(ões) para o paciente", request.getMedicacoes().size());
            for (UUID medicacaoId : request.getMedicacoes()) {
                try {
                    MedicacaoPacienteSimplificadoRequest medicacaoRequest = MedicacaoPacienteSimplificadoRequest.builder()
                            .paciente(paciente.getId())
                            .tenant(tenant.getId())
                            .medicacao(medicacaoId)
                            .build();
                    medicacaoPacienteService.criarSimplificado(medicacaoRequest);
                    log.debug("Medicação {} associada ao paciente {}", medicacaoId, paciente.getId());
                } catch (BadRequestException | NotFoundException e) {
                    log.warn("Erro ao associar medicação {} ao paciente {}. Erro: {}",
                        medicacaoId, paciente.getId(), e.getMessage());
                    throw e;
                } catch (RuntimeException e) {
                    log.error("Erro inesperado ao associar medicação {} ao paciente {}. Exception: {}",
                        medicacaoId, paciente.getId(), e.getClass().getSimpleName(), e);
                    throw new BadRequestException("Erro ao associar medicação " + medicacaoId + ": " + e.getMessage(), e);
                }
            }
        }

        log.debug("Relacionamentos por ID processados com sucesso.");
    }

    private <T> void processarRelacionamentoOneToOne(
            UUID uuid,
            org.springframework.data.jpa.repository.JpaRepository<T, UUID> repository,
            java.util.function.Consumer<T> sincronizador,
            String nomeEntidade) {

        if (uuid != null) {
            T entidade = repository.findById(uuid)
                    .orElseThrow(() -> new NotFoundException(nomeEntidade + " não encontrado com ID: " + uuid));
            sincronizador.accept(entidade);
        }
    }

    private void calcularIdade(PacienteResponse response) {
        if (response == null) {
            return;
        }

        if (response.getDataNascimento() != null) {
            LocalDate dataNascimento = response.getDataNascimento();
            LocalDate hoje = LocalDate.now();
            int idade = Period.between(dataNascimento, hoje).getYears();
            response.setIdade(idade);
        } else {
            response.setIdade(null);
        }
    }

}
