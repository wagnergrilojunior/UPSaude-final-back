package com.upsaude.service.impl;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.request.DeficienciasPacienteSimplificadoRequest;
import com.upsaude.api.request.DoencasPacienteSimplificadoRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.entity.*;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.ConflictException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PacienteMapper;
import com.upsaude.mapper.EnderecoMapper;
import com.upsaude.repository.*;
import com.upsaude.service.PacienteService;
import com.upsaude.service.AlergiasPacienteService;
import com.upsaude.service.DeficienciasPacienteService;
import com.upsaude.service.DoencasPacienteService;
import com.upsaude.service.MedicacaoPacienteService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.Hibernate;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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
    private final UsuariosSistemaRepository usuariosSistemaRepository;
    
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

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @CacheEvict(value = "paciente", allEntries = true)
    public PacienteResponse criar(PacienteRequest request) {
        log.debug("Criando novo paciente: {}", request.getNomeCompleto());

        validarDadosBasicos(request);
        validarCPFUnico(null, request.getCpf());

        Paciente paciente = pacienteMapper.fromRequest(request);
        paciente.setActive(true);

        // Processar relacionamentos na ordem correta ANTES de salvar o paciente
        processarRelacionamentos(paciente, request);

        Paciente pacienteSalvo = pacienteRepository.save(paciente);
        log.info("Paciente criado com sucesso. ID: {}", pacienteSalvo.getId());

        // Processar listas de IDs de doenças, alergias, deficiências e medicações após salvar o paciente
        processarRelacionamentosPorId(pacienteSalvo, request);

        return pacienteMapper.toResponse(pacienteSalvo);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @Cacheable(value = "paciente", key = "#id")
    public PacienteResponse obterPorId(UUID id) {
        log.debug("Buscando paciente por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

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

        return pacienteMapper.toResponse(paciente);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PacienteResponse> listar(Pageable pageable) {
        log.debug("Listando pacientes paginados. Página: {}, Tamanho: {}", 
                pageable.getPageNumber(), pageable.getPageSize());

        // Busca pacientes paginados
        Page<Paciente> pacientes = pacienteRepository.findAll(pageable);
        
        // Inicializa as coleções lazy dentro da transação para evitar LazyInitializationException
        // Isso força o Hibernate a carregar os endereços antes de fechar a sessão
        pacientes.getContent().forEach(paciente -> {
            Hibernate.initialize(paciente.getEnderecos());
            // Inicializa outras coleções lazy se necessário
            Hibernate.initialize(paciente.getDoencas());
            Hibernate.initialize(paciente.getAlergias());
            Hibernate.initialize(paciente.getDeficiencias());
            Hibernate.initialize(paciente.getMedicacoes());
        });
        
        return pacientes.map(pacienteMapper::toResponse);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    @CacheEvict(value = "paciente", key = "#id")
    public PacienteResponse atualizar(UUID id, PacienteRequest request) {
        log.debug("Atualizando paciente. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        validarDadosBasicos(request);

        Paciente pacienteExistente = pacienteRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + id));

        validarCPFUnico(id, request.getCpf());

        atualizarDadosPaciente(pacienteExistente, request);
        
        // Processar relacionamentos na ordem correta ANTES de salvar o paciente
        processarRelacionamentos(pacienteExistente, request);

        Paciente pacienteAtualizado = pacienteRepository.save(pacienteExistente);
        log.info("Paciente atualizado com sucesso. ID: {}", pacienteAtualizado.getId());

        // Processar listas de IDs de doenças, alergias, deficiências e medicações após salvar o paciente
        processarRelacionamentosPorId(pacienteAtualizado, request);

        return pacienteMapper.toResponse(pacienteAtualizado);
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

    /**
     * Valida os dados básicos do paciente.
     *
     * @param request dados do paciente a serem validados
     * @throws BadRequestException se os dados forem inválidos
     */
    private void validarDadosBasicos(PacienteRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do paciente são obrigatórios");
        }

        if (!StringUtils.hasText(request.getNomeCompleto())) {
            throw new BadRequestException("Nome completo é obrigatório");
        }

        if (request.getNomeCompleto().length() > 255) {
            throw new BadRequestException("Nome completo deve ter no máximo 255 caracteres");
        }

        if (StringUtils.hasText(request.getCpf()) && !request.getCpf().matches("^\\d{11}$")) {
            throw new BadRequestException("CPF deve conter exatamente 11 dígitos numéricos");
        }

        if (StringUtils.hasText(request.getCns()) && !request.getCns().matches("^\\d{15}$")) {
            throw new BadRequestException("CNS deve conter exatamente 15 dígitos numéricos");
        }
    }

    /**
     * Valida se o CPF é único no sistema.
     *
     * @param pacienteId ID do paciente atual (null se for criação)
     * @param cpf CPF a ser validado
     * @throws ConflictException se já existir outro paciente com o mesmo CPF
     */
    private void validarCPFUnico(UUID pacienteId, String cpf) {
        if (!StringUtils.hasText(cpf)) {
            return;
        }

        Optional<Paciente> pacienteExistente = pacienteRepository.findByCpf(cpf);

        if (pacienteExistente.isPresent()) {
            Paciente pacienteEncontrado = pacienteExistente.get();

            // Se for atualização, verifica se o CPF pertence a outro paciente
            if (pacienteId != null && !pacienteEncontrado.getId().equals(pacienteId)) {
                throw new ConflictException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }

            // Se for criação, sempre lança exceção se encontrar CPF
            if (pacienteId == null) {
                throw new ConflictException("Já existe um paciente cadastrado com o CPF: " + cpf);
            }
        }
    }

    /**
     * Atualiza os dados do paciente com base no request.
     *
     * @param paciente paciente existente a ser atualizado
     * @param request dados atualizados
     */
    private void atualizarDadosPaciente(Paciente paciente, PacienteRequest request) {
        Paciente pacienteAtualizado = pacienteMapper.fromRequest(request);

        paciente.setNomeCompleto(pacienteAtualizado.getNomeCompleto());
        paciente.setCpf(pacienteAtualizado.getCpf());
        paciente.setRg(pacienteAtualizado.getRg());
        paciente.setCns(pacienteAtualizado.getCns());
        paciente.setDataNascimento(pacienteAtualizado.getDataNascimento());
        paciente.setSexo(pacienteAtualizado.getSexo());
        paciente.setEstadoCivil(pacienteAtualizado.getEstadoCivil());
        paciente.setTelefone(pacienteAtualizado.getTelefone());
        paciente.setEmail(pacienteAtualizado.getEmail());
        paciente.setNomeMae(pacienteAtualizado.getNomeMae());
        paciente.setNomePai(pacienteAtualizado.getNomePai());
        paciente.setResponsavelNome(pacienteAtualizado.getResponsavelNome());
        paciente.setResponsavelCpf(pacienteAtualizado.getResponsavelCpf());
        paciente.setResponsavelTelefone(pacienteAtualizado.getResponsavelTelefone());
        paciente.setNumeroCarteirinha(pacienteAtualizado.getNumeroCarteirinha());
        paciente.setDataValidadeCarteirinha(pacienteAtualizado.getDataValidadeCarteirinha());
        paciente.setObservacoes(pacienteAtualizado.getObservacoes());
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

        // ENDEREÇOS (OneToMany) - Criar endereços e associar ao paciente
        if (request.getEnderecos() != null && !request.getEnderecos().isEmpty()) {
            // Obtém o tenant do usuário autenticado (obrigatório para Endereco que estende BaseEntity)
            Tenant tenant = obterTenantDoUsuarioAutenticado();
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
                    
                    enderecos.add(endereco);
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
        Tenant tenant = obterTenantDoUsuarioAutenticado();
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
                    log.error("Erro ao associar doença {} ao paciente {}: {}", doencaId, paciente.getId(), e.getMessage());
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
                    log.error("Erro ao associar alergia {} ao paciente {}: {}", alergiaId, paciente.getId(), e.getMessage());
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
                    log.error("Erro ao associar deficiência {} ao paciente {}: {}", deficienciaId, paciente.getId(), e.getMessage());
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
                    log.error("Erro ao associar medicação {} ao paciente {}: {}", medicacaoId, paciente.getId(), e.getMessage());
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
     * Obtém o tenant do usuário autenticado.
     * 
     * @return Tenant do usuário autenticado ou null se não encontrado
     */
    private Tenant obterTenantDoUsuarioAutenticado() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication == null || !authentication.isAuthenticated()) {
                log.warn("Usuário não autenticado. Não é possível obter tenant.");
                return null;
            }

            // Obtém o userId do token JWT
            UUID userId = null;
            Object details = authentication.getDetails();
            if (details instanceof com.upsaude.integration.supabase.SupabaseAuthResponse.User) {
                com.upsaude.integration.supabase.SupabaseAuthResponse.User user = 
                    (com.upsaude.integration.supabase.SupabaseAuthResponse.User) details;
                userId = user.getId();
                log.debug("UserId obtido do SupabaseAuthResponse.User: {}", userId);
            } else if (authentication.getPrincipal() instanceof String) {
                try {
                    userId = UUID.fromString(authentication.getPrincipal().toString());
                    log.debug("UserId obtido do Principal (String): {}", userId);
                } catch (IllegalArgumentException e) {
                    log.warn("Principal não é um UUID válido: {}", authentication.getPrincipal());
                    return null;
                }
            } else {
                log.warn("Tipo de Principal não reconhecido: {}", authentication.getPrincipal() != null ? authentication.getPrincipal().getClass().getName() : "null");
            }

            if (userId != null) {
                java.util.Optional<com.upsaude.entity.UsuariosSistema> usuarioOpt = usuariosSistemaRepository.findByUserId(userId);
                if (usuarioOpt.isPresent()) {
                    com.upsaude.entity.UsuariosSistema usuario = usuarioOpt.get();
                    Tenant tenant = usuario.getTenant();
                    if (tenant != null) {
                        log.debug("Tenant obtido com sucesso: {} (ID: {})", tenant.getNome(), tenant.getId());
                        return tenant;
                    } else {
                        log.warn("Usuário encontrado mas sem tenant associado. UserId: {}", userId);
                    }
                } else {
                    log.warn("Usuário não encontrado no sistema. UserId: {}", userId);
                }
            } else {
                log.warn("Não foi possível obter userId do contexto de autenticação");
            }
        } catch (Exception e) {
            log.error("Erro ao obter tenant do usuário autenticado", e);
        }
        return null;
    }
}

