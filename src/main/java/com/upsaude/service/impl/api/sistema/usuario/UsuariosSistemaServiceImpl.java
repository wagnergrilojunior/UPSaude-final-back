package com.upsaude.service.impl.api.sistema.usuario;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.persistence.criteria.Predicate;
import java.util.ArrayList;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.upsaude.api.request.sistema.usuario.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.usuario.UsuariosSistemaResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.auth.User;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.mapper.sistema.usuario.UsuariosSistemaMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.estabelecimento.UsuarioEstabelecimentoRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.sistema.multitenancy.TenantRepository;
import com.upsaude.repository.sistema.auth.UserRepository;
import com.upsaude.repository.sistema.usuario.UsuariosSistemaRepository;
import com.upsaude.service.api.sistema.usuario.UsuariosSistemaService;
import com.upsaude.service.api.sistema.usuario.UserService;
import com.upsaude.api.request.sistema.usuario.UserRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosSistemaServiceImpl implements UsuariosSistemaService {

    private final UsuariosSistemaRepository usuariosSistemaRepository;
    private final UsuariosSistemaMapper usuariosSistemaMapper;
    private final UsuarioEstabelecimentoRepository usuarioEstabelecimentoRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final TenantRepository tenantRepository;
    private final MedicosRepository medicosRepository;
    private final ProfissionaisSaudeRepository profissionaisSaudeRepository;
    private final PacienteRepository pacienteRepository;
    private final UserRepository userRepository;
    private final SupabaseStorageService supabaseStorageService;
    private final com.upsaude.integration.supabase.SupabaseAuthService supabaseAuthService;
    private final UserService userService;

    @Override
    @Transactional
    public UsuariosSistemaResponse criar(UsuariosSistemaRequest request) {
        log.debug("Criando novo usuariossistema");

        try {
            if (request.getEmail() == null || request.getEmail().trim().isEmpty()) {
                throw new BadRequestException("Email é obrigatório para criar usuário");
            }

            log.debug("Criando User no Supabase Auth primeiro");
            UserRequest userRequestCriar = UserRequest.builder()
                    .email(request.getEmail())
                    .build();

            com.upsaude.api.response.sistema.usuario.UserResponse userResponse = userService.criar(userRequestCriar);
            UUID userIdFinal = userResponse.getId();
            log.info("User criado com sucesso no Supabase Auth. ID: {}", userIdFinal);

            if (request.getSenha() != null && !request.getSenha().trim().isEmpty()) {
                log.debug("Atualizando senha do User recém-criado");
                UserRequest userRequestAtualizar = UserRequest.builder()
                        .email(request.getEmail())
                        .senha(request.getSenha())
                        .build();
                userService.atualizar(userIdFinal, userRequestAtualizar);
                log.info("Senha do User atualizada com sucesso");
            }

            User user = userRepository.findById(userIdFinal)
                    .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + userIdFinal));

            validarEmailUnico(null, user != null ? user.getEmail() : null, userIdFinal);
            validarUsernameUnico(null,
                    request.getDadosIdentificacao() != null ? request.getDadosIdentificacao().getUsername() : null);

            UsuariosSistema usuariosSistema = usuariosSistemaMapper.fromRequest(request);
            usuariosSistema.setAtivo(true);
            usuariosSistema.setUser(user);

            if (request.getTenantId() != null) {
                Tenant tenant = tenantRepository.findById(request.getTenantId())
                        .orElseThrow(
                                () -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenantId()));
                usuariosSistema.setTenant(tenant);
            }

            if (request.getMedico() != null) {
                Medicos medico = medicosRepository.findById(request.getMedico())
                        .orElseThrow(
                                () -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
                usuariosSistema.setMedico(medico);
            }

            if (request.getProfissionalSaude() != null) {
                ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalSaude())
                        .orElseThrow(() -> new NotFoundException(
                                "Profissional de saúde não encontrado com ID: " + request.getProfissionalSaude()));
                usuariosSistema.setProfissionalSaude(profissional);
            }

            if (request.getPaciente() != null) {
                Paciente paciente = pacienteRepository.findById(request.getPaciente())
                        .orElseThrow(() -> new NotFoundException(
                                "Paciente não encontrado com ID: " + request.getPaciente()));
                usuariosSistema.setPaciente(paciente);
            }

            UsuariosSistema usuariosSistemaSalvo = usuariosSistemaRepository.save(usuariosSistema);
            log.info("UsuariosSistema criado com sucesso. ID: {}", usuariosSistemaSalvo.getId());

            if (request.getEstabelecimentos() != null && !request.getEstabelecimentos().isEmpty()) {
                criarVinculosComPapel(usuariosSistemaSalvo, request.getEstabelecimentos());
            } else if (request.getEstabelecimentosIds() != null && !request.getEstabelecimentosIds().isEmpty()) {

                criarVinculosEstabelecimentos(usuariosSistemaSalvo, request.getEstabelecimentosIds());
            }

            return enrichResponseWithEntity(usuariosSistemaSalvo);
        } catch (BadRequestException | NotFoundException e) {
            log.warn("Erro de validação ao criar UsuariosSistema. Erro: {}", e.getMessage());
            throw e;
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao criar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(),
                    e);
            throw new InternalServerErrorException("Erro ao persistir UsuariosSistema", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao criar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    @Override
    @Transactional
    public UsuariosSistemaResponse obterPorId(UUID id) {
        log.debug("Buscando usuariossistema por ID: {}", id);
        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema usuariosSistema = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        return enrichResponseWithEntity(usuariosSistema);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<UsuariosSistemaResponse> listar(Pageable pageable) {
        log.debug("Listando UsuariosSistemas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        try {
            Pageable adjustedPageable = ajustarPageableParaCamposEmbeddados(pageable);

            Specification<UsuariosSistema> spec = (root, query, cb) -> {
                List<Predicate> predicates = new ArrayList<>();

                return cb.and(predicates.toArray(new Predicate[0]));
            };

            Page<UsuariosSistema> usuariosSistemas = usuariosSistemaRepository.findAll(spec, adjustedPageable);

            usuariosSistemas.getContent().forEach(us -> {
                Hibernate.initialize(us.getEstabelecimentosVinculados());
            });

            log.debug("Listagem de usuários do sistema concluída. Total de elementos: {}",
                    usuariosSistemas.getTotalElements());
            return usuariosSistemas.map(us -> enrichResponse(usuariosSistemaMapper.toResponse(us)));
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(),
                    e);
            throw new InternalServerErrorException("Erro ao listar UsuariosSistema", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
    }

    private Pageable ajustarPageableParaCamposEmbeddados(Pageable pageable) {
        if (pageable.getSort().isEmpty()) {
            return pageable;
        }

        Sort adjustedSort = pageable.getSort().stream()
                .map(order -> {
                    String property = order.getProperty();

                    if ("username".equals(property)) {
                        property = "dadosIdentificacao." + property;
                    }

                else if ("nomeExibicao".equals(property) || "fotoUrl".equals(property)) {
                        property = "dadosExibicao." + property;
                    }

                else if ("adminTenant".equals(property) || "adminSistema".equals(property)) {
                        property = "configuracao." + property;
                    }
                    return new Sort.Order(order.getDirection(), property);
                })
                .collect(Collectors.collectingAndThen(Collectors.toList(), orders -> {
                    if (orders.isEmpty()) {
                        return Sort.unsorted();
                    }
                    return Sort.by(orders);
                }));

        return PageRequest.of(pageable.getPageNumber(), pageable.getPageSize(), adjustedSort);
    }

    @Override
    @Transactional
    public UsuariosSistemaResponse atualizar(UUID id, UsuariosSistemaRequest request) {
        log.debug("Atualizando usuariossistema. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema usuariosSistemaExistente = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        User userAtualizado = usuariosSistemaExistente.getUser();

        if (userAtualizado == null) {
            throw new BadRequestException("Não é possível atualizar: usuário não possui User vinculado");
        }

        UUID userIdParaAtualizar = userAtualizado.getId();

        boolean precisaAtualizarUser = false;
        if (request.getEmail() != null && !request.getEmail().trim().isEmpty()) {
            if (!request.getEmail().equals(userAtualizado.getEmail())) {
                precisaAtualizarUser = true;
            }
        }
        if (request.getSenha() != null && !request.getSenha().trim().isEmpty()) {
            precisaAtualizarUser = true;
        }

        if (precisaAtualizarUser) {
            log.debug("Atualizando User no Supabase Auth primeiro");
            UserRequest userRequest = UserRequest.builder()
                    .email(request.getEmail())
                    .senha(request.getSenha())
                    .build();

            com.upsaude.api.response.sistema.usuario.UserResponse userResponse = userService
                    .atualizar(userIdParaAtualizar, userRequest);
            log.info("User atualizado com sucesso no Supabase Auth. ID: {}", userResponse.getId());

            userAtualizado = userRepository.findById(userIdParaAtualizar)
                    .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + userIdParaAtualizar));
        }

        validarEmailUnico(id, userAtualizado != null ? userAtualizado.getEmail() : null, userIdParaAtualizar);
        validarUsernameUnico(id,
                request.getDadosIdentificacao() != null ? request.getDadosIdentificacao().getUsername() : null);

        atualizarDadosUsuariosSistema(usuariosSistemaExistente, request);

        if (userAtualizado != null) {
            usuariosSistemaExistente.setUser(userAtualizado);
        }

        UsuariosSistema usuariosSistemaAtualizado = usuariosSistemaRepository.save(usuariosSistemaExistente);
        log.info("UsuariosSistema atualizado com sucesso. ID: {}", usuariosSistemaAtualizado.getId());

        if (request.getEstabelecimentos() != null && !request.getEstabelecimentos().isEmpty()) {
            atualizarVinculosComPapel(usuariosSistemaAtualizado, request.getEstabelecimentos());
        } else if (request.getEstabelecimentosIds() != null) {

            atualizarVinculosEstabelecimentos(usuariosSistemaAtualizado, request.getEstabelecimentosIds());
        }

        return enrichResponseWithEntity(usuariosSistemaAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.info("Excluindo usuário PERMANENTEMENTE em 2 etapas. UsuariosSistema ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema usuariosSistema = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        UUID userId = usuariosSistema.getUser() != null ? usuariosSistema.getUser().getId() : null;

        usuariosSistemaRepository.delete(usuariosSistema);
        log.info("ETAPA 1: UsuariosSistema e vínculos deletados PERMANENTEMENTE. ID: {}", id);

        try {
            supabaseAuthService.deleteUser(userId);
            log.info("ETAPA 2: User deletado PERMANENTEMENTE do Supabase Auth. UserID: {}", userId);
        } catch (Exception e) {
            log.error("Erro ao deletar User do Supabase Auth. UserID: {}, Exception: {}", userId,
                    e.getClass().getName(), e);

            log.warn("UsuariosSistema foi deletado mas User não foi removido do Supabase (pode não existir)");
        }
    }

    private void validarEmailUnico(UUID usuariosSistemaId, String email, UUID userId) {
        if (!StringUtils.hasText(email)) {
            return;
        }

        Optional<User> userExistente = userRepository.findByEmail(email);

        if (userExistente.isPresent()) {
            User userEncontrado = userExistente.get();

            if (usuariosSistemaId != null) {
                UsuariosSistema usuariosSistemaExistente = usuariosSistemaRepository.findById(usuariosSistemaId)
                        .orElseThrow(() -> new NotFoundException(
                                "UsuariosSistema não encontrado com ID: " + usuariosSistemaId));

                if (usuariosSistemaExistente.getUser() == null
                        || !userEncontrado.getId().equals(usuariosSistemaExistente.getUser().getId())) {
                    throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
                }
            } else {

                if (userId != null) {

                    Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository.findByUserId(userId);
                    if (usuariosSistemaExistente.isPresent()) {
                        throw new BadRequestException(
                                "Já existe um usuário do sistema cadastrado com este userId: " + userId);
                    }

                    if (!userEncontrado.getId().equals(userId)) {
                        throw new BadRequestException("O email informado pertence a outro usuário. Email: " + email);
                    }

                    log.debug(
                            "Email {} existe em auth.users e corresponde ao userId {}, mas ainda não existe em usuarios_sistema. Permitindo cadastro.",
                            email, userId);
                } else {

                    Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository
                            .findByUserId(userEncontrado.getId());
                    if (usuariosSistemaExistente.isPresent()) {
                        throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
                    }

                    log.debug("Email {} existe em auth.users mas não existe em usuarios_sistema. Permitindo cadastro.",
                            email);
                }
            }
        }
    }

    private void validarUsernameUnico(UUID usuariosSistemaId, String username) {
        if (!StringUtils.hasText(username)) {
            return;
        }

        Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository.findByUsername(username);

        if (usuariosSistemaExistente.isPresent()) {
            UsuariosSistema usuarioEncontrado = usuariosSistemaExistente.get();

            if (usuariosSistemaId != null && !usuarioEncontrado.getId().equals(usuariosSistemaId)) {
                throw new BadRequestException("Já existe um usuário cadastrado com o username: " + username);
            }

            if (usuariosSistemaId == null) {
                throw new BadRequestException("Já existe um usuário cadastrado com o username: " + username);
            }
        }
    }

    private void atualizarDadosUsuariosSistema(UsuariosSistema usuariosSistema, UsuariosSistemaRequest request) {

        usuariosSistemaMapper.updateFromRequest(request, usuariosSistema);
        // Atualizar objetos embeddados preservando valores existentes
        usuariosSistemaMapper.updateEmbeddablesFromRequest(request, usuariosSistema);

        if (request.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(request.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenantId()));
            usuariosSistema.setTenant(tenant);
        }

        if (request.getMedico() != null) {
            Medicos medico = medicosRepository.findById(request.getMedico())
                    .orElseThrow(() -> new NotFoundException("Médico não encontrado com ID: " + request.getMedico()));
            usuariosSistema.setMedico(medico);
        }

        if (request.getProfissionalSaude() != null) {
            ProfissionaisSaude profissional = profissionaisSaudeRepository.findById(request.getProfissionalSaude())
                    .orElseThrow(() -> new NotFoundException(
                            "Profissional de saúde não encontrado com ID: " + request.getProfissionalSaude()));
            usuariosSistema.setProfissionalSaude(profissional);
        }

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(
                            () -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            usuariosSistema.setPaciente(paciente);
        }

    }

    private void criarVinculosComPapel(UsuariosSistema usuario,
            List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {
        for (UsuariosSistemaRequest.EstabelecimentoVinculoRequest vinculoRequest : vinculos) {

            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(
                            usuario.getUser() != null ? usuario.getUser().getId() : null,
                            vinculoRequest.getEstabelecimentoId())
                    .ifPresentOrElse(
                            vinculoExistente -> {
                                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {

                                    vinculoExistente.setActive(true);
                                    vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                                    log.debug("Vínculo reativado com papel {} para usuário {} e estabelecimento {}",
                                            vinculoRequest.getTipoUsuario(), usuario.getId(),
                                            vinculoRequest.getEstabelecimentoId());
                                }
                            },
                            () -> {

                                Estabelecimentos estabelecimento = estabelecimentosRepository
                                        .findById(vinculoRequest.getEstabelecimentoId())
                                        .orElseThrow(
                                                () -> new NotFoundException("Estabelecimento não encontrado com ID: "
                                                        + vinculoRequest.getEstabelecimentoId()));

                                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                                vinculo.setUsuario(usuario);
                                vinculo.setEstabelecimento(estabelecimento);
                                vinculo.setTenant(usuario.getTenant());
                                vinculo.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                vinculo.setActive(true);

                                usuarioEstabelecimentoRepository.save(vinculo);
                                log.debug("Vínculo criado com papel {} para usuário {} e estabelecimento {}",
                                        vinculoRequest.getTipoUsuario(), usuario.getId(),
                                        vinculoRequest.getEstabelecimentoId());
                            });
        }
    }

    @Deprecated
    private void criarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {
        for (UUID estabelecimentoId : estabelecimentosIds) {

            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(
                            usuario.getUser() != null ? usuario.getUser().getId() : null, estabelecimentoId)
                    .ifPresentOrElse(
                            vinculoExistente -> {
                                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {

                                    vinculoExistente.setActive(true);
                                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                                    log.debug("Vínculo reativado para usuário {} e estabelecimento {}", usuario.getId(),
                                            estabelecimentoId);
                                }
                            },
                            () -> {

                                Estabelecimentos estabelecimento = estabelecimentosRepository
                                        .findById(estabelecimentoId)
                                        .orElseThrow(() -> new NotFoundException(
                                                "Estabelecimento não encontrado com ID: " + estabelecimentoId));

                                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                                vinculo.setUsuario(usuario);
                                vinculo.setEstabelecimento(estabelecimento);
                                vinculo.setTenant(usuario.getTenant());
                                vinculo.setActive(true);

                                usuarioEstabelecimentoRepository.save(vinculo);
                                log.debug("Vínculo criado para usuário {} e estabelecimento {}", usuario.getId(),
                                        estabelecimentoId);
                            });
        }
    }

    private void atualizarVinculosComPapel(UsuariosSistema usuario,
            List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {

        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository
                .findByUsuarioUserId(usuario.getUser() != null ? usuario.getUser().getId() : null);

        List<UUID> novosIds = vinculos.stream()
                .map(UsuariosSistemaRequest.EstabelecimentoVinculoRequest::getEstabelecimentoId)
                .collect(java.util.stream.Collectors.toList());

        for (UsuarioEstabelecimento vinculo : vinculosExistentes) {
            if (Boolean.TRUE.equals(vinculo.getActive()) &&
                    !novosIds.contains(vinculo.getEstabelecimento().getId())) {
                vinculo.setActive(false);
                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo desativado para estabelecimento {}", vinculo.getEstabelecimento().getId());
            }
        }

        for (UsuariosSistemaRequest.EstabelecimentoVinculoRequest vinculoRequest : vinculos) {
            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(
                            usuario.getUser() != null ? usuario.getUser().getId() : null,
                            vinculoRequest.getEstabelecimentoId())
                    .ifPresentOrElse(
                            vinculoExistente -> {

                                vinculoExistente.setActive(true);
                                vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                usuarioEstabelecimentoRepository.save(vinculoExistente);
                                log.debug("Vínculo atualizado com papel {} para estabelecimento {}",
                                        vinculoRequest.getTipoUsuario(), vinculoRequest.getEstabelecimentoId());
                            },
                            () -> {

                                Estabelecimentos estabelecimento = estabelecimentosRepository
                                        .findById(vinculoRequest.getEstabelecimentoId())
                                        .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado"));

                                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                                vinculo.setUsuario(usuario);
                                vinculo.setEstabelecimento(estabelecimento);
                                vinculo.setTenant(usuario.getTenant());
                                vinculo.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                vinculo.setActive(true);

                                usuarioEstabelecimentoRepository.save(vinculo);
                                log.debug("Novo vínculo criado com papel {} para estabelecimento {}",
                                        vinculoRequest.getTipoUsuario(), vinculoRequest.getEstabelecimentoId());
                            });
        }
    }

    @Deprecated
    private void atualizarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {

        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository
                .findByUsuarioUserId(usuario.getUser() != null ? usuario.getUser().getId() : null);

        List<UUID> idsExistentes = vinculosExistentes.stream()
                .filter(v -> Boolean.TRUE.equals(v.getActive()))
                .map(v -> v.getEstabelecimento().getId())
                .collect(Collectors.toList());

        for (UsuarioEstabelecimento vinculo : vinculosExistentes) {
            if (Boolean.TRUE.equals(vinculo.getActive()) &&
                    !estabelecimentosIds.contains(vinculo.getEstabelecimento().getId())) {
                vinculo.setActive(false);
                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo desativado para usuário {} e estabelecimento {}", usuario.getId(),
                        vinculo.getEstabelecimento().getId());
            }
        }

        List<UUID> idsParaCriar = estabelecimentosIds.stream()
                .filter(id -> !idsExistentes.contains(id))
                .collect(Collectors.toList());

        criarVinculosEstabelecimentos(usuario, idsParaCriar);
    }

    @Override
    @Transactional
    public String uploadFoto(UUID id, MultipartFile file) {
        log.debug("Fazendo upload de foto para usuário: {}", id);

        UsuariosSistema usuario = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (usuario.getUser() == null || usuario.getUser().getId() == null) {
            throw new BadRequestException(
                    "Usuário não possui User vinculado. É necessário criar/atualizar o usuário com email antes de fazer upload de foto.");
        }

        if (usuario.getDadosExibicao() != null && usuario.getDadosExibicao().getFotoUrl() != null
                && !usuario.getDadosExibicao().getFotoUrl().isEmpty()) {
            supabaseStorageService.deletarFotoUsuario(usuario.getDadosExibicao().getFotoUrl());
        }

        String fotoUrl = supabaseStorageService.uploadFotoUsuario(file, usuario.getUser().getId());

        if (usuario.getDadosExibicao() == null) {
            usuario.setDadosExibicao(new com.upsaude.entity.embeddable.DadosExibicaoUsuario());
        }
        usuario.getDadosExibicao().setFotoUrl(fotoUrl);
        usuariosSistemaRepository.save(usuario);

        log.info("Foto enviada com sucesso para usuário {}. URL: {}", id, fotoUrl);
        return fotoUrl;
    }

    @Override
    public String obterFotoUrl(UUID id) {
        log.debug("Buscando URL da foto para usuário: {}", id);

        UsuariosSistema usuario = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        return usuario.getDadosExibicao() != null ? usuario.getDadosExibicao().getFotoUrl() : null;
    }

    @Override
    @Transactional
    public void deletarFoto(UUID id) {
        log.debug("Deletando foto do usuário: {}", id);

        UsuariosSistema usuario = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (usuario.getDadosExibicao() != null && usuario.getDadosExibicao().getFotoUrl() != null
                && !usuario.getDadosExibicao().getFotoUrl().isEmpty()) {
            supabaseStorageService.deletarFotoUsuario(usuario.getDadosExibicao().getFotoUrl());
            usuario.getDadosExibicao().setFotoUrl(null);
            usuariosSistemaRepository.save(usuario);
            log.info("Foto deletada com sucesso para usuário: {}", id);
        } else {
            log.debug("Usuário {} não possui foto para deletar", id);
        }
    }

    @Override
    @Transactional
    public void trocarSenha(UUID id, String novaSenha) {
        log.debug("Trocando senha do usuário: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuário do sistema é obrigatório");
        }

        if (novaSenha == null || novaSenha.trim().isEmpty()) {
            throw new BadRequestException("Nova senha é obrigatória");
        }

        UsuariosSistema usuario = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (usuario.getUser() == null || usuario.getUser().getId() == null) {
            throw new BadRequestException("Usuário não possui User vinculado. Não é possível trocar a senha.");
        }

        UUID userId = usuario.getUser().getId();
        String email = usuario.getUser().getEmail();

        if (email == null || email.trim().isEmpty()) {
            log.warn("Email do User não encontrado. Tentando buscar do Supabase Auth");
            try {
                com.upsaude.integration.supabase.SupabaseAuthResponse.User supabaseUser = supabaseAuthService
                        .getUserById(userId);
                if (supabaseUser != null && supabaseUser.getEmail() != null) {
                    email = supabaseUser.getEmail();
                } else {
                    throw new BadRequestException("Não foi possível obter o email do usuário no Supabase Auth");
                }
            } catch (Exception e) {
                log.error("Erro ao buscar email do Supabase Auth para userId: {}", userId, e);
                throw new BadRequestException("Não foi possível obter o email do usuário");
            }
        }

        log.debug("Atualizando senha do User no Supabase Auth. UserID: {}", userId);
        UserRequest userRequest = UserRequest.builder()
                .email(email)
                .senha(novaSenha)
                .build();

        userService.atualizar(userId, userRequest);
        log.info("Senha trocada com sucesso para o usuário do sistema ID: {}, UserID: {}", id, userId);
    }

    private UsuariosSistemaResponse enrichResponseWithEntity(UsuariosSistema usuario) {
        // Garantir que os objetos embeddados sejam inicializados
        if (usuario.getDadosIdentificacao() != null) {
            org.hibernate.Hibernate.initialize(usuario.getDadosIdentificacao());
        }
        if (usuario.getDadosExibicao() != null) {
            org.hibernate.Hibernate.initialize(usuario.getDadosExibicao());
        }
        if (usuario.getConfiguracao() != null) {
            org.hibernate.Hibernate.initialize(usuario.getConfiguracao());
        }

        UsuariosSistemaResponse response = usuariosSistemaMapper.toResponse(usuario);

        // Garantir que os dados de identificação sejam incluídos na resposta
        if (response.getDadosIdentificacao() == null && usuario.getDadosIdentificacao() != null) {
            response.setDadosIdentificacao(
                    com.upsaude.api.response.embeddable.DadosIdentificacaoUsuarioResponse.builder()
                            .username(usuario.getDadosIdentificacao().getUsername())
                            .cpf(usuario.getDadosIdentificacao().getCpf())
                            .build());
        } else if (response.getDadosIdentificacao() != null && usuario.getDadosIdentificacao() != null) {
            // Garantir que o CPF seja incluído mesmo se o mapper não incluiu
            if (response.getDadosIdentificacao().getCpf() == null && usuario.getDadosIdentificacao().getCpf() != null) {
                response.getDadosIdentificacao().setCpf(usuario.getDadosIdentificacao().getCpf());
            }
            if (response.getDadosIdentificacao().getUsername() == null
                    && usuario.getDadosIdentificacao().getUsername() != null) {
                response.getDadosIdentificacao().setUsername(usuario.getDadosIdentificacao().getUsername());
            }
        }

        if (usuario.getTenant() != null) {
            response.setTenantId(usuario.getTenant().getId());
            response.setTenantNome(usuario.getTenant().getDadosIdentificacao() != null
                    ? usuario.getTenant().getDadosIdentificacao().getNome()
                    : null);
            response.setTenantSlug(null);
        }

        if (usuario.getProfissionalSaude() != null) {
            var profissional = usuario.getProfissionalSaude();
            org.hibernate.Hibernate.initialize(profissional);
            if (profissional.getDadosPessoaisBasicos() != null) {
                org.hibernate.Hibernate.initialize(profissional.getDadosPessoaisBasicos());
            }
            if (profissional.getContato() != null) {
                org.hibernate.Hibernate.initialize(profissional.getContato());
            }
            response.setProfissionalSaude(
                    com.upsaude.api.response.sistema.usuario.ProfissionalSaudeSimplificadoResponse.builder()
                            .id(profissional.getId())
                            .nome(profissional.getDadosPessoaisBasicos() != null
                                    ? profissional.getDadosPessoaisBasicos().getNomeCompleto()
                                    : null)
                            .email(profissional.getContato() != null ? profissional.getContato().getEmail() : null)
                            .build());
        }

        if (usuario.getMedico() != null) {
            var medico = usuario.getMedico();
            org.hibernate.Hibernate.initialize(medico);
            if (medico.getDadosPessoaisBasicos() != null) {
                org.hibernate.Hibernate.initialize(medico.getDadosPessoaisBasicos());
            }
            if (medico.getContato() != null) {
                org.hibernate.Hibernate.initialize(medico.getContato());
            }
            response.setMedico(com.upsaude.api.response.sistema.usuario.MedicoSimplificadoResponse.builder()
                    .id(medico.getId())
                    .nome(medico.getDadosPessoaisBasicos() != null ? medico.getDadosPessoaisBasicos().getNomeCompleto()
                            : null)
                    .email(medico.getContato() != null ? medico.getContato().getEmail() : null)
                    .build());
        }

        if (usuario.getPaciente() != null) {
            var paciente = usuario.getPaciente();
            org.hibernate.Hibernate.initialize(paciente);
            if (paciente.getContatos() != null) {
                org.hibernate.Hibernate.initialize(paciente.getContatos());
            }
            String emailPaciente = paciente.getContatos() != null ? paciente.getContatos().stream()
                    .filter(c -> c.getTipo() == TipoContatoEnum.EMAIL)
                    .map(c -> c.getEmail())
                    .filter(e -> e != null && !e.trim().isEmpty())
                    .findFirst().orElse(null) : null;
            response.setPaciente(com.upsaude.api.response.sistema.usuario.PacienteSimplificadoResponse.builder()
                    .id(paciente.getId())
                    .nome(paciente.getNomeCompleto())
                    .email(emailPaciente)
                    .build());
        }

        if (usuario.getUser() != null) {
            var user = usuario.getUser();
            org.hibernate.Hibernate.initialize(user);
            String nomeUsuario = usuario.getDadosExibicao() != null ? usuario.getDadosExibicao().getNomeExibicao()
                    : null;
            if (nomeUsuario == null && usuario.getDadosIdentificacao() != null) {
                nomeUsuario = usuario.getDadosIdentificacao().getUsername();
            }
            response.setUsuario(com.upsaude.api.response.sistema.usuario.UsuarioSimplificadoResponse.builder()
                    .id(user.getId())
                    .nome(nomeUsuario)
                    .email(user.getEmail())
                    .build());
        }

        List<UsuarioEstabelecimento> vinculos = usuarioEstabelecimentoRepository
                .findByUsuarioUserId(usuario.getUser() != null ? usuario.getUser().getId() : null);
        if (vinculos != null && !vinculos.isEmpty()) {
            List<UsuariosSistemaResponse.EstabelecimentoVinculoSimples> vinculosResponse = vinculos.stream()
                    .filter(v -> Boolean.TRUE.equals(v.getActive()))
                    .map(v -> UsuariosSistemaResponse.EstabelecimentoVinculoSimples.builder()
                            .id(v.getId())
                            .estabelecimentoId(v.getEstabelecimento().getId())
                            .estabelecimentoNome(v.getEstabelecimento().getDadosIdentificacao() != null
                                    ? v.getEstabelecimento().getDadosIdentificacao().getNome()
                                    : null)
                            .tipoUsuario(v.getTipoUsuario())
                            .active(v.getActive())
                            .build())
                    .collect(Collectors.toList());
            response.setEstabelecimentosVinculados(vinculosResponse);
            log.debug("Vínculos de estabelecimentos carregados: {} ativos", vinculosResponse.size());
        }

        if (usuario.getConfiguracao() != null && Boolean.TRUE.equals(usuario.getConfiguracao().getAdminTenant())) {
            response.setTipoUsuario(com.upsaude.enums.TipoUsuarioSistemaEnum.ADMIN_TENANT);
        } else if (usuario.getMedico() != null) {
            response.setTipoUsuario(com.upsaude.enums.TipoUsuarioSistemaEnum.MEDICO);
        } else if (usuario.getProfissionalSaude() != null) {
            response.setTipoUsuario(com.upsaude.enums.TipoUsuarioSistemaEnum.PROFISSIONAL_SAUDE);
        } else if (usuario.getPaciente() != null) {
            response.setTipoUsuario(com.upsaude.enums.TipoUsuarioSistemaEnum.PACIENTE);
        } else {
            response.setTipoUsuario(null);
        }

        return response;
    }

    private UsuariosSistemaResponse enrichResponse(UsuariosSistemaResponse response) {
        UsuariosSistema usuario = usuariosSistemaRepository.findById(response.getId())
                .orElse(null);

        if (usuario != null) {
            return enrichResponseWithEntity(usuario);
        }

        return response;
    }
}
