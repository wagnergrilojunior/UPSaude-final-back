package com.upsaude.service.impl;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.request.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.api.response.PacienteSimplificadoResponse;
import com.upsaude.entity.*;
import com.upsaude.enums.StatusPacienteEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PacienteMapper;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.repository.*;
import com.upsaude.service.PacienteService;
import com.upsaude.service.AlergiasPacienteService;
import com.upsaude.service.DeficienciasPacienteService;
import com.upsaude.service.DoencasPacienteService;
import com.upsaude.service.MedicacaoPacienteService;
import com.upsaude.service.EnderecoService;
import com.upsaude.service.TenantService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.CachePut;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Pacientes.
 * Responsável por aplicar regras de negócio e coordenar operações CRUD.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PacienteServiceImpl implements PacienteService {

    private final PacienteRepository pacienteRepository;
    private final PacienteMapper pacienteMapper;
    private final EnderecoMapper enderecoMapper;
    private final EstadosRepository estadosRepository;
    private final CidadesRepository cidadesRepository;
    
    // Repositories para entidades relacionadas
    private final ConvenioRepository convenioRepository;
    private final DadosSociodemograficosRepository dadosSociodemograficosRepository;
    private final DadosClinicosBasicosRepository dadosClinicosBasicosRepository;
    private final ResponsavelLegalRepository responsavelLegalRepository;
    private final LGPDConsentimentoRepository lgpdConsentimentoRepository;
    private final IntegracaoGovRepository integracaoGovRepository;
    
    // Services para criar relacionamentos simplificados
    private final AlergiasPacienteService alergiasPacienteService;
    private final DeficienciasPacienteService deficienciasPacienteService;
    private final DoencasPacienteService doencasPacienteService;
    private final MedicacaoPacienteService medicacaoPacienteService;
    private final EnderecoService enderecoService;
    private final TenantService tenantService;
    
    // Self-injection: injeta o próprio service (proxy) para evitar self-invocation
    // @Lazy evita dependência circular na inicialização
    // @Autowired é necessário porque @RequiredArgsConstructor não injeta campos com @Lazy
    @Autowired
    @Lazy
    private PacienteService self;

    /**
     * {@inheritDoc}
     * 
     * IMPORTANTE: Usa @CachePut para adicionar o paciente criado ao cache.
     * Não usa @CacheEvict com allEntries=true para evitar limpar todo o cache desnecessariamente.
     * O paciente criado será automaticamente adicionado ao cache com sua chave (ID).
     */
    @Override
    @Transactional
    @CachePut(value = "paciente", key = "#result.id")
    public PacienteResponse criar(PacienteRequest request) {
        log.debug("Criando novo paciente: {}", request != null ? request.getNomeCompleto() : "null");

        try {
            // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request
            
            // Validações de duplicatas antes de criar
            validarCpfUnico(null, request.getCpf());
            validarEmailUnico(null, request.getEmail());
            validarCnsUnico(null, request.getCns());
            validarRgUnico(null, request.getRg());

            Paciente paciente = pacienteMapper.fromRequest(request);
            paciente.setActive(true);
            
            // Garantir valores padrão para campos obrigatórios que podem ser null após mapeamento
            // O MapStruct pode sobrescrever valores padrão da entidade com null quando o campo não vem no request
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

            // Processar relacionamentos na ordem correta ANTES de salvar o paciente
            processarRelacionamentos(paciente, request);

            Paciente pacienteSalvo = pacienteRepository.save(paciente);
            log.info("Paciente criado com sucesso. ID: {} - Será adicionado ao cache automaticamente", pacienteSalvo.getId());

            // Processar listas de IDs de doenças, alergias, deficiências e medicações após salvar o paciente
            processarRelacionamentosPorId(pacienteSalvo, request);

            PacienteResponse response = pacienteMapper.toResponse(pacienteSalvo);
            calcularIdade(response);
            log.debug("Paciente criado adicionado ao cache com chave: {}", response.getId());
            return response;
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar paciente. Request: {}. Erro: {}", request, e.getMessage());
            throw e;
        } catch (Exception e) {
            log.error("Erro inesperado ao criar paciente. Request: {}, Exception: {}", request, e.getClass().getName(), e);
            throw e;
        }
    }

    /**
     * {@inheritDoc}
     * 
     * IMPORTANTE: O cache funciona porque quando este método é chamado externamente
     * (via controller), o Spring automaticamente usa o proxy AOP, ativando o @Cacheable.
     * 
     * O campo 'self' está disponível caso seja necessário chamar este método internamente
     * de outros métodos da mesma classe. Nesse caso, use this.self.obterPorId(id) para
     * garantir que o proxy seja usado e o cache funcione.
     */
    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "paciente", key = "#id")
    public PacienteResponse obterPorId(UUID id) {
        log.debug("Buscando paciente por ID: {} - Verificando cache primeiro", id);
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        // Se chegou aqui, significa que não estava no cache (cache miss)
        // O Spring Cache intercepta antes e só chama este método se não encontrar no cache
        log.debug("Cache MISS para paciente ID: {} - Buscando no banco de dados", id);
        
        Paciente paciente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        // Inicializa as coleções lazy dentro da transação para evitar LazyInitializationException
        // Isso força o Hibernate a carregar os relacionamentos antes de fechar a sessão
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
        
        // Inicializar objetos nested dentro dos relacionamentos se necessário
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
                    // Inicializar objetos nested da medicacao se necessário
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


    /**
     * Converte uma entidade Paciente para PacienteSimplificadoResponse.
     * Retorna apenas os dados básicos, sem relacionamentos.
     *
     * @param paciente entidade Paciente a ser convertida
     * @return PacienteSimplificadoResponse com dados básicos
     */
    /**
     * Converte entidade Paciente para resposta simplificada.
     * Usado quando já temos a entidade carregada.
     */
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

    /**
     * Converte projeção otimizada para resposta simplificada.
     * Usado na listagem para melhor performance, evitando carregar relacionamentos lazy.
     */
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

    /**
     * {@inheritDoc}
     * 
     * OTIMIZAÇÃO: Usa EntityGraph para carregar todos os relacionamentos necessários
     * em uma única query, evitando o problema N+1 queries.
     * O método findAll() do repository já está configurado com @EntityGraph que carrega:
     * - convenio
     * - enderecos
     * - doencas
     * - alergias
     * - deficiencias
     * - medicacoes
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponse> listar(Pageable pageable) {
        log.debug("Listando pacientes paginados. Página: {}, Tamanho: {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        // Busca pacientes paginados com relacionamentos carregados via EntityGraph
        // O EntityGraph garante que todos os relacionamentos sejam carregados em uma única query
        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
        
        return pacientes.map(paciente -> {
            PacienteResponse response = pacienteMapper.toResponse(paciente);
            calcularIdade(response);
            return response;
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PacienteSimplificadoResponse> listarSimplificado(Pageable pageable) {
        log.debug("Listando pacientes simplificados paginados. Página: {}, Tamanho: {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        // Usa projeção otimizada que seleciona apenas os campos necessários
        // sem carregar relacionamentos lazy, melhorando significativamente a performance
        Page<com.upsaude.repository.projection.PacienteSimplificadoProjection> projecoes = 
                pacienteRepository.findAllSimplificado(pageable);
        
        // Converte projeção para resposta simplificada
        return projecoes.map(this::converterProjecaoParaSimplificado);
    }

    /**
     * {@inheritDoc}
     * 
     * IMPORTANTE: Usa @CachePut para atualizar o cache com os dados atualizados.
     * Isso garante que o cache sempre tenha os dados mais recentes após atualização.
     */
    @Override
    @Transactional
    @CachePut(value = "paciente", key = "#id")
    public PacienteResponse atualizar(UUID id, PacienteRequest request) {
        log.debug("Atualizando paciente. ID: {} - Cache será atualizado automaticamente", id);

        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        // Validações de duplicatas antes de atualizar
        validarCpfUnico(id, request.getCpf());
        validarEmailUnico(id, request.getEmail());
        validarCnsUnico(id, request.getCns());
        validarRgUnico(id, request.getRg());

        atualizarDadosPaciente(pacienteExistente, request);
        
        // Processar relacionamentos na ordem correta ANTES de salvar o paciente
        processarRelacionamentos(pacienteExistente, request);

        Paciente pacienteAtualizado = pacienteRepository.save(pacienteExistente);
        log.info("Paciente atualizado com sucesso. ID: {} - Cache será atualizado automaticamente", pacienteAtualizado.getId());

        // Processar listas de IDs de doenças, alergias, deficiências e medicações após salvar o paciente
        processarRelacionamentosPorId(pacienteAtualizado, request);

        PacienteResponse response = pacienteMapper.toResponse(pacienteAtualizado);
        calcularIdade(response);
        log.debug("Paciente atualizado no cache com chave: {}", id);
        return response;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo paciente. ID: {}", id);

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
        log.info("Paciente excluído (desativado) com sucesso. ID: {}", id);
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    /**
     * Valida se o CPF é único no sistema.
     *
     * @param pacienteId ID do paciente atual (null se for criação)
     * @param cpf CPF a ser validado
     * @throws BadRequestException se já existir outro paciente com o mesmo CPF
     */
    private void validarCpfUnico(UUID pacienteId, String cpf) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCpf(cpf);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            // Se for atualização, verifica se o CPF pertence a outro paciente
            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }

            // Se for criação, sempre lança exceção se encontrar CPF
            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }
        }
    }

    /**
     * Valida se o email é único no sistema.
     *
     * @param pacienteId ID do paciente atual (null se for criação)
     * @param email email a ser validado
     * @throws BadRequestException se já existir outro paciente com o mesmo email
     */
    private void validarEmailUnico(UUID pacienteId, String email) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByEmail(email);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            // Se for atualização, verifica se o email pertence a outro paciente
            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o email: " + email);
            }

            // Se for criação, sempre lança exceção se encontrar email
            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o email: " + email);
            }
        }
    }

    /**
     * Valida se o CNS (Cartão Nacional de Saúde) é único no sistema.
     *
     * @param pacienteId ID do paciente atual (null se for criação)
     * @param cns CNS a ser validado
     * @throws BadRequestException se já existir outro paciente com o mesmo CNS
     */
    private void validarCnsUnico(UUID pacienteId, String cns) {
        if (!StringUtils.hasText(cns)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCns(cns);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            // Se for atualização, verifica se o CNS pertence a outro paciente
            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CNS: " + cns);
            }

            // Se for criação, sempre lança exceção se encontrar CNS
            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o CNS: " + cns);
            }
        }
    }

    /**
     * Valida se o RG é único no sistema.
     *
     * @param pacienteId ID do paciente atual (null se for criação)
     * @param rg RG a ser validado
     * @throws BadRequestException se já existir outro paciente com o mesmo RG
     */
    private void validarRgUnico(UUID pacienteId, String rg) {
        if (!StringUtils.hasText(rg)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByRg(rg);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            // Se for atualização, verifica se o RG pertence a outro paciente
            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new BadRequestException("Já existe um paciente cadastrado com o RG: " + rg);
            }

            // Se for criação, sempre lança exceção se encontrar RG
            if (pacienteId == null) {
                throw new BadRequestException("Já existe um paciente cadastrado com o RG: " + rg);
            }
        }
    }

    /**
     * Atualiza os dados do paciente com base no request.
     * Usa o método updateFromRequest do mapper para atualizar todos os campos,
     * incluindo tipoCns, identidadeGenero e orientacaoSexual.
     *
     * @param paciente paciente existente a ser atualizado
     * @param request dados atualizados
     */
    private void atualizarDadosPaciente(Paciente paciente, PacienteRequest request) {
        // Usa o método updateFromRequest do mapper que atualiza todos os campos automaticamente
        // Isso garante que todos os campos do request sejam atualizados, incluindo:
        // - tipoCns
        // - identidadeGenero
        // - orientacaoSexual
        // - e todos os outros campos do PacienteRequest
        pacienteMapper.updateFromRequest(request, paciente);
        
        // Relacionamentos serão tratados no método processarRelacionamentos
    }

    /**
     * Processa relacionamentos do paciente de forma simplificada.
     * Com as anotações corretas do JPA (cascade, orphanRemoval), o Hibernate 
     * gerencia automaticamente a ordem de salvamento e integridade referencial.
     *
     * Responsabilidades deste método:
     * 1. Buscar entidades relacionadas existentes (apenas validação)
     * 2. Sincronizar relacionamentos bidirecionais
     * 3. O JPA/Hibernate cuida do resto (ordem, persistência, cascade)
     *
     * @param paciente entidade Paciente a ser processada
     * @param request dados do request com os UUIDs dos relacionamentos
     */
    private void processarRelacionamentos(Paciente paciente, PacienteRequest request) {
        log.debug("Processando relacionamentos do paciente");

        // CONVÊNIO (ManyToOne) - Buscar entidade existente
        if (request.getConvenio() != null) {
            Convenio convenio = convenioRepository.findById(request.getConvenio())
                    .orElseThrow(() -> new NotFoundException("Convênio não encontrado com ID: " + request.getConvenio()));
            paciente.setConvenio(convenio);
        }

        // RELACIONAMENTOS OneToOne - Sincronização bidirecional
        // O JPA com cascade=ALL e orphanRemoval=true gerencia tudo automaticamente
        
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

        // ENDEREÇOS (OneToMany) - Buscar endereços existentes ou criar novos para evitar duplicados
        if (request.getEnderecos() != null && !request.getEnderecos().isEmpty()) {
            // Obtém o tenant do usuário autenticado (obrigatório para Endereco que estende BaseEntity)
            Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
            if (tenant == null) {
                throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar endereços.");
            }
            
            List<Endereco> enderecos = new ArrayList<>();
            for (EnderecoRequest enderecoRequest : request.getEnderecos()) {
                // Só processa endereço se tiver logradouro ou número
                if (enderecoRequest.getLogradouro() != null || enderecoRequest.getNumero() != null) {
                    Endereco endereco = enderecoMapper.fromRequest(enderecoRequest);
                    endereco.setActive(true);
                    
                    // Define o tenant do endereço (obrigatório para BaseEntity)
                    endereco.setTenant(tenant);
                    
                    // Garante valores padrão para campos obrigatórios
                    if (endereco.getSemNumero() == null) {
                        endereco.setSemNumero(false);
                    }
                    
                    // Processa relacionamentos estado e cidade (opcionais)
                    if (enderecoRequest.getEstado() != null) {
                        Estados estado = estadosRepository.findById(enderecoRequest.getEstado())
                                .orElseThrow(() -> new NotFoundException("Estado não encontrado com ID: " + enderecoRequest.getEstado()));
                        endereco.setEstado(estado);
                    }

                    if (enderecoRequest.getCidade() != null) {
                        Cidades cidade = cidadesRepository.findById(enderecoRequest.getCidade())
                                .orElseThrow(() -> new NotFoundException("Cidade não encontrada com ID: " + enderecoRequest.getCidade()));
                        endereco.setCidade(cidade);
                    }
                    
                    // Usa findOrCreate para evitar duplicados - busca endereço existente ou cria novo
                    Endereco enderecoFinal = enderecoService.findOrCreate(endereco);
                    enderecos.add(enderecoFinal);
                    log.debug("Endereço processado. ID: {}", enderecoFinal.getId());
                }
            }
            if (!enderecos.isEmpty()) {
                paciente.setEnderecos(enderecos);
                log.debug("{} endereço(s) processado(s) para associação ao paciente", enderecos.size());
            }
        }

        log.debug("Relacionamentos processados. JPA gerenciará persistência automaticamente.");
    }

    /**
     * Processa relacionamentos de doenças, alergias, deficiências e medicações usando apenas IDs.
     * Cria os registros usando os métodos simplificados após o paciente ser salvo.
     *
     * @param paciente paciente já salvo no banco
     * @param request dados do request com as listas de IDs
     */
    private void processarRelacionamentosPorId(Paciente paciente, PacienteRequest request) {
        log.debug("Processando relacionamentos por ID do paciente: {}", paciente.getId());

        // Obtém o tenant do usuário autenticado (obrigatório para os relacionamentos)
        Tenant tenant = tenantService.obterTenantDoUsuarioAutenticado();
        if (tenant == null) {
            throw new BadRequestException("Não foi possível obter tenant do usuário autenticado. É necessário estar autenticado para criar relacionamentos.");
        }

        // Processa DOENÇAS
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
                } catch (Exception e) {
                    log.error("Erro ao associar doença {} ao paciente {}: {}, Exception: {}", 
                        doencaId, paciente.getId(), e.getMessage(), e.getClass().getName(), e);
                    throw new BadRequestException("Erro ao associar doença " + doencaId + ": " + e.getMessage());
                }
            }
        }

        // Processa ALERGIAS
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
                } catch (Exception e) {
                    log.error("Erro ao associar alergia {} ao paciente {}: {}, Exception: {}", 
                        alergiaId, paciente.getId(), e.getMessage(), e.getClass().getName(), e);
                    throw new BadRequestException("Erro ao associar alergia " + alergiaId + ": " + e.getMessage());
                }
            }
        }

        // Processa DEFICIÊNCIAS
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
                } catch (Exception e) {
                    log.error("Erro ao associar deficiência {} ao paciente {}: {}, Exception: {}", 
                        deficienciaId, paciente.getId(), e.getMessage(), e.getClass().getName(), e);
                    throw new BadRequestException("Erro ao associar deficiência " + deficienciaId + ": " + e.getMessage());
                }
            }
        }

        // Processa MEDICAÇÕES
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
                } catch (Exception e) {
                    log.error("Erro ao associar medicação {} ao paciente {}: {}, Exception: {}", 
                        medicacaoId, paciente.getId(), e.getMessage(), e.getClass().getName(), e);
                    throw new BadRequestException("Erro ao associar medicação " + medicacaoId + ": " + e.getMessage());
                }
            }
        }

        log.debug("Relacionamentos por ID processados com sucesso.");
    }

    /**
     * Método genérico para processar relacionamentos OneToOne.
     * Reduz duplicação de código e centraliza a lógica de sincronização.
     *
     * @param uuid ID da entidade relacionada
     * @param repository repository da entidade
     * @param sincronizador função que sincroniza o relacionamento bidirecional
     * @param nomeEntidade nome da entidade para mensagens de erro
     * @param <T> tipo da entidade
     */
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

    /**
     * Calcula a idade do paciente baseada na data de nascimento.
     * A idade é calculada como a diferença em anos entre a data de nascimento e a data atual.
     * Se a data de nascimento não estiver disponível, a idade será null.
     *
     * @param response PacienteResponse que terá a idade calculada
     */
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

