package com.upsaude.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import org.hibernate.Hibernate;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.upsaude.api.request.sistema.UsuariosSistemaRequest;
import com.upsaude.api.response.sistema.UsuariosSistemaResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.entity.sistema.User;
import com.upsaude.entity.sistema.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.mapper.sistema.UsuariosSistemaMapper;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.estabelecimento.UsuarioEstabelecimentoRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.repository.profissional.ProfissionaisSaudeRepository;
import com.upsaude.repository.sistema.TenantRepository;
import com.upsaude.repository.sistema.UserRepository;
import com.upsaude.repository.sistema.UsuariosSistemaRepository;
import com.upsaude.service.sistema.UsuariosSistemaService;

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

    @Override
    @Transactional
    public UsuariosSistemaResponse criar(UsuariosSistemaRequest request) {
        log.debug("Criando novo usuariossistema");

        try {

            if (request.getUserId() != null) {
                User user = userRepository.findById(request.getUserId())
                        .orElseThrow(() -> new NotFoundException("User não encontrado com ID: " + request.getUserId()));

                if (request.getEmail() != null && user.getEmail() != null &&
                    !user.getEmail().equalsIgnoreCase(request.getEmail())) {
                    throw new BadRequestException("O email informado não corresponde ao userId fornecido. Email do userId: " + user.getEmail());
                }
            }

            validarEmailUnico(null, request.getEmail(), request.getUserId());
            validarUsernameUnico(null, request.getUsername());

            UsuariosSistema usuariosSistema = usuariosSistemaMapper.fromRequest(request);
            usuariosSistema.setActive(true);

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
                        .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissionalSaude()));
                usuariosSistema.setProfissionalSaude(profissional);
            }

            if (request.getPaciente() != null) {
                Paciente paciente = pacienteRepository.findById(request.getPaciente())
                        .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
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
            log.error("Erro de acesso a dados ao criar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(), e);
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
            Page<UsuariosSistema> usuariosSistemas = usuariosSistemaRepository.findAll(pageable);

            usuariosSistemas.getContent().forEach(us -> {
                Hibernate.initialize(us.getEstabelecimentosVinculados());
            });

            log.debug("Listagem de usuários do sistema concluída. Total de elementos: {}", usuariosSistemas.getTotalElements());
            return usuariosSistemas.map(us -> enrichResponse(usuariosSistemaMapper.toResponse(us)));
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao listar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao listar UsuariosSistema", e);
        } catch (RuntimeException e) {
            log.error("Erro inesperado ao listar UsuariosSistema. Exception: {}", e.getClass().getSimpleName(), e);
            throw e;
        }
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

        validarEmailUnico(id, request.getEmail(), request.getUserId());
        validarUsernameUnico(id, request.getUsername());

        atualizarDadosUsuariosSistema(usuariosSistemaExistente, request);

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

        UUID userId = usuariosSistema.getUserId();

        usuariosSistemaRepository.delete(usuariosSistema);
        log.info("ETAPA 1: UsuariosSistema e vínculos deletados PERMANENTEMENTE. ID: {}", id);

        try {
            supabaseAuthService.deleteUser(userId);
            log.info("ETAPA 2: User deletado PERMANENTEMENTE do Supabase Auth. UserID: {}", userId);
        } catch (Exception e) {
            log.error("Erro ao deletar User do Supabase Auth. UserID: {}, Exception: {}", userId, e.getClass().getName(), e);

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
                        .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + usuariosSistemaId));

                if (!userEncontrado.getId().equals(usuariosSistemaExistente.getUserId())) {
                    throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
                }
            } else {

                if (userId != null) {

                    Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository.findByUserId(userId);
                    if (usuariosSistemaExistente.isPresent()) {
                        throw new BadRequestException("Já existe um usuário do sistema cadastrado com este userId: " + userId);
                    }

                    if (!userEncontrado.getId().equals(userId)) {
                        throw new BadRequestException("O email informado pertence a outro usuário. Email: " + email);
                    }

                    log.debug("Email {} existe em auth.users e corresponde ao userId {}, mas ainda não existe em usuarios_sistema. Permitindo cadastro.", email, userId);
                } else {

                    Optional<UsuariosSistema> usuariosSistemaExistente = usuariosSistemaRepository.findByUserId(userEncontrado.getId());
                    if (usuariosSistemaExistente.isPresent()) {
                        throw new BadRequestException("Já existe um usuário cadastrado com o email: " + email);
                    }

                    log.debug("Email {} existe em auth.users mas não existe em usuarios_sistema. Permitindo cadastro.", email);
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
                    .orElseThrow(() -> new NotFoundException("Profissional de saúde não encontrado com ID: " + request.getProfissionalSaude()));
            usuariosSistema.setProfissionalSaude(profissional);
        }

        if (request.getPaciente() != null) {
            Paciente paciente = pacienteRepository.findById(request.getPaciente())
                    .orElseThrow(() -> new NotFoundException("Paciente não encontrado com ID: " + request.getPaciente()));
            usuariosSistema.setPaciente(paciente);
        }

    }

    private void criarVinculosComPapel(UsuariosSistema usuario, List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {
        for (UsuariosSistemaRequest.EstabelecimentoVinculoRequest vinculoRequest : vinculos) {

            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(usuario.getUserId(), vinculoRequest.getEstabelecimentoId())
                    .ifPresentOrElse(
                            vinculoExistente -> {
                                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {

                                    vinculoExistente.setActive(true);
                                    vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                                    log.debug("Vínculo reativado com papel {} para usuário {} e estabelecimento {}",
                                            vinculoRequest.getTipoUsuario(), usuario.getId(), vinculoRequest.getEstabelecimentoId());
                                }
                            },
                            () -> {

                                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(vinculoRequest.getEstabelecimentoId())
                                        .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + vinculoRequest.getEstabelecimentoId()));

                                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                                vinculo.setUsuario(usuario);
                                vinculo.setEstabelecimento(estabelecimento);
                                vinculo.setTenant(usuario.getTenant());
                                vinculo.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                vinculo.setActive(true);

                                usuarioEstabelecimentoRepository.save(vinculo);
                                log.debug("Vínculo criado com papel {} para usuário {} e estabelecimento {}",
                                        vinculoRequest.getTipoUsuario(), usuario.getId(), vinculoRequest.getEstabelecimentoId());
                            }
                    );
        }
    }

    @Deprecated
    private void criarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {
        for (UUID estabelecimentoId : estabelecimentosIds) {

            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(usuario.getUserId(), estabelecimentoId)
                    .ifPresentOrElse(
                            vinculoExistente -> {
                                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {

                                    vinculoExistente.setActive(true);
                                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                                    log.debug("Vínculo reativado para usuário {} e estabelecimento {}", usuario.getId(), estabelecimentoId);
                                }
                            },
                            () -> {

                                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(estabelecimentoId)
                                        .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + estabelecimentoId));

                                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                                vinculo.setUsuario(usuario);
                                vinculo.setEstabelecimento(estabelecimento);
                                vinculo.setTenant(usuario.getTenant());
                                vinculo.setActive(true);

                                usuarioEstabelecimentoRepository.save(vinculo);
                                log.debug("Vínculo criado para usuário {} e estabelecimento {}", usuario.getId(), estabelecimentoId);
                            }
                    );
        }
    }

    private void atualizarVinculosComPapel(UsuariosSistema usuario, List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {

        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository
                .findByUsuarioUserId(usuario.getUserId());

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
                    .findByUsuarioUserIdAndEstabelecimentoId(usuario.getUserId(), vinculoRequest.getEstabelecimentoId())
                    .ifPresentOrElse(
                            vinculoExistente -> {

                                vinculoExistente.setActive(true);
                                vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                usuarioEstabelecimentoRepository.save(vinculoExistente);
                                log.debug("Vínculo atualizado com papel {} para estabelecimento {}",
                                        vinculoRequest.getTipoUsuario(), vinculoRequest.getEstabelecimentoId());
                            },
                            () -> {

                                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(vinculoRequest.getEstabelecimentoId())
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
                            }
                    );
        }
    }

    @Deprecated
    private void atualizarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {

        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository
                .findByUsuarioUserId(usuario.getUserId());

        List<UUID> idsExistentes = vinculosExistentes.stream()
                .filter(v -> Boolean.TRUE.equals(v.getActive()))
                .map(v -> v.getEstabelecimento().getId())
                .collect(Collectors.toList());

        for (UsuarioEstabelecimento vinculo : vinculosExistentes) {
            if (Boolean.TRUE.equals(vinculo.getActive()) &&
                !estabelecimentosIds.contains(vinculo.getEstabelecimento().getId())) {
                vinculo.setActive(false);
                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo desativado para usuário {} e estabelecimento {}", usuario.getId(), vinculo.getEstabelecimento().getId());
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

        if (usuario.getFotoUrl() != null && !usuario.getFotoUrl().isEmpty()) {
            supabaseStorageService.deletarFotoUsuario(usuario.getFotoUrl());
        }

        String fotoUrl = supabaseStorageService.uploadFotoUsuario(file, usuario.getUserId());

        usuario.setFotoUrl(fotoUrl);
        usuariosSistemaRepository.save(usuario);

        log.info("Foto enviada com sucesso para usuário {}. URL: {}", id, fotoUrl);
        return fotoUrl;
    }

    @Override
    public String obterFotoUrl(UUID id) {
        log.debug("Buscando URL da foto para usuário: {}", id);

        UsuariosSistema usuario = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        return usuario.getFotoUrl();
    }

    @Override
    @Transactional
    public void deletarFoto(UUID id) {
        log.debug("Deletando foto do usuário: {}", id);

        UsuariosSistema usuario = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Usuário não encontrado com ID: " + id));

        if (usuario.getFotoUrl() != null && !usuario.getFotoUrl().isEmpty()) {
            supabaseStorageService.deletarFotoUsuario(usuario.getFotoUrl());
            usuario.setFotoUrl(null);
            usuariosSistemaRepository.save(usuario);
            log.info("Foto deletada com sucesso para usuário: {}", id);
        } else {
            log.debug("Usuário {} não possui foto para deletar", id);
        }
    }

    private UsuariosSistemaResponse enrichResponseWithEntity(UsuariosSistema usuario) {
        UsuariosSistemaResponse response = usuariosSistemaMapper.toResponse(usuario);

        if (usuario.getTenant() != null) {
            response.setTenantId(usuario.getTenant().getId());
            response.setTenantNome(usuario.getTenant().getNome());
            response.setTenantSlug(usuario.getTenant().getSlug());
        }

        if (usuario.getUserId() != null) {
            try {
                com.upsaude.integration.supabase.SupabaseAuthResponse.User supabaseUser =
                        supabaseAuthService.getUserById(usuario.getUserId());
                if (supabaseUser != null && supabaseUser.getEmail() != null) {
                    response.setEmail(supabaseUser.getEmail());
                    log.debug("Email do Supabase recuperado: {}", supabaseUser.getEmail());
                } else {
                    log.warn("Email não encontrado no Supabase para userId: {}", usuario.getUserId());
                }
            } catch (RuntimeException e) {
                log.error("Erro ao buscar email do Supabase para userId: {}, Exception: {}",
                    usuario.getUserId(), e.getClass().getSimpleName(), e);

            }
        }

        List<UsuarioEstabelecimento> vinculos = usuarioEstabelecimentoRepository.findByUsuarioUserId(usuario.getUserId());
        if (vinculos != null && !vinculos.isEmpty()) {
            List<UsuariosSistemaResponse.EstabelecimentoVinculoSimples> vinculosResponse = vinculos.stream()
                    .filter(v -> Boolean.TRUE.equals(v.getActive()))
                    .map(v -> UsuariosSistemaResponse.EstabelecimentoVinculoSimples.builder()
                            .id(v.getId())
                            .estabelecimentoId(v.getEstabelecimento().getId())
                            .estabelecimentoNome(v.getEstabelecimento().getNome())
                            .tipoUsuario(v.getTipoUsuario())
                            .active(v.getActive())
                            .build())
                    .collect(Collectors.toList());
            response.setEstabelecimentosVinculados(vinculosResponse);
            log.debug("Vínculos de estabelecimentos carregados: {} ativos", vinculosResponse.size());
        }

        if (Boolean.TRUE.equals(usuario.getAdminTenant())) {
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
