package com.upsaude.service.impl;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Medicos;
import com.upsaude.entity.Paciente;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuarioEstabelecimento;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.mapper.UsuariosSistemaMapper;
import com.upsaude.repository.EstabelecimentosRepository;
import com.upsaude.repository.MedicosRepository;
import com.upsaude.repository.PacienteRepository;
import com.upsaude.repository.ProfissionaisSaudeRepository;
import com.upsaude.repository.TenantRepository;
import com.upsaude.repository.UsuarioEstabelecimentoRepository;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.UsuariosSistemaService;
import org.springframework.web.multipart.MultipartFile;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação do serviço de gerenciamento de UsuariosSistema.
 *
 * @author UPSaúde
 */
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
    private final SupabaseStorageService supabaseStorageService;
    private final com.upsaude.integration.supabase.SupabaseAuthService supabaseAuthService;

    @Override
    @Transactional
    public UsuariosSistemaResponse criar(UsuariosSistemaRequest request) {
        log.debug("Criando novo usuariossistema");

        validarDadosBasicos(request);

        UsuariosSistema usuariosSistema = usuariosSistemaMapper.fromRequest(request);
        usuariosSistema.setActive(true);
        
        // Setar tenant
        if (request.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(request.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenantId()));
            usuariosSistema.setTenant(tenant);
        }
        
        // Setar relacionamentos se fornecidos
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
        
        // Criar vínculos com estabelecimentos (novo formato com papel)
        if (request.getEstabelecimentos() != null && !request.getEstabelecimentos().isEmpty()) {
            criarVinculosComPapel(usuariosSistemaSalvo, request.getEstabelecimentos());
        } else if (request.getEstabelecimentosIds() != null && !request.getEstabelecimentosIds().isEmpty()) {
            // Fallback para compatibilidade (deprecated)
            criarVinculosEstabelecimentos(usuariosSistemaSalvo, request.getEstabelecimentosIds());
        }

        return enrichResponseWithEntity(usuariosSistemaSalvo);
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
    public Page<UsuariosSistemaResponse> listar(Pageable pageable) {
        log.debug("Listando UsuariosSistemas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<UsuariosSistema> usuariosSistemas = usuariosSistemaRepository.findAll(pageable);
        return usuariosSistemas.map(us -> enrichResponse(usuariosSistemaMapper.toResponse(us)));
    }

    @Override
    @Transactional
    public UsuariosSistemaResponse atualizar(UUID id, UsuariosSistemaRequest request) {
        log.debug("Atualizando usuariossistema. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        validarDadosBasicos(request);

        UsuariosSistema usuariosSistemaExistente = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        atualizarDadosUsuariosSistema(usuariosSistemaExistente, request);

        UsuariosSistema usuariosSistemaAtualizado = usuariosSistemaRepository.save(usuariosSistemaExistente);
        log.info("UsuariosSistema atualizado com sucesso. ID: {}", usuariosSistemaAtualizado.getId());

        // Atualizar vínculos de estabelecimentos (novo formato com papel)
        if (request.getEstabelecimentos() != null && !request.getEstabelecimentos().isEmpty()) {
            atualizarVinculosComPapel(usuariosSistemaAtualizado, request.getEstabelecimentos());
        } else if (request.getEstabelecimentosIds() != null) {
            // Fallback para compatibilidade (deprecated)
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

        // ETAPA 1: Deletar UsuariosSistema e seus vínculos do banco (CASCADE vai deletar vínculos automaticamente)
        usuariosSistemaRepository.delete(usuariosSistema);
        log.info("ETAPA 1: UsuariosSistema e vínculos deletados PERMANENTEMENTE. ID: {}", id);

        // ETAPA 2: Deletar User do Supabase Auth via API
        try {
            supabaseAuthService.deleteUser(userId);
            log.info("ETAPA 2: User deletado PERMANENTEMENTE do Supabase Auth. UserID: {}", userId);
        } catch (Exception e) {
            log.error("Erro ao deletar User do Supabase Auth. UserID: {}", userId, e);
            // Não lançar exceção - UsuariosSistema já foi deletado
            log.warn("UsuariosSistema foi deletado mas User não foi removido do Supabase (pode não existir)");
        }
    }

    private void validarDadosBasicos(UsuariosSistemaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do usuariossistema são obrigatórios");
        }
        if (request.getUserId() == null) {
            throw new BadRequestException("ID do usuário Supabase é obrigatório");
        }
        if (request.getAdminTenant() == null) {
            throw new BadRequestException("Campo adminTenant é obrigatório");
        }
        // Se não for admin do tenant, deve ter pelo menos um vínculo com estabelecimento
        // (validação será feita no controller ao criar vínculos)
    }

    private void atualizarDadosUsuariosSistema(UsuariosSistema usuariosSistema, UsuariosSistemaRequest request) {
        // Atualizar campos diretamente sem usar BeanUtils.copyProperties
        // Isso evita problemas com coleções gerenciadas pelo JPA (cascade="all-delete-orphan")
        
        if (request.getNomeExibicao() != null) {
            usuariosSistema.setNomeExibicao(request.getNomeExibicao());
        }
        
        if (request.getUsername() != null) {
            usuariosSistema.setUsername(request.getUsername());
        }
        
        if (request.getFotoUrl() != null) {
            usuariosSistema.setFotoUrl(request.getFotoUrl());
        }
        
        if (request.getAdminTenant() != null) {
            usuariosSistema.setAdminTenant(request.getAdminTenant());
        }
        
        if (request.getTipoVinculo() != null) {
            usuariosSistema.setTipoVinculo(request.getTipoVinculo());
        }
        
        // Atualizar tenant se fornecido
        if (request.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(request.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenantId()));
            usuariosSistema.setTenant(tenant);
        }
        
        // Atualizar relacionamentos se fornecidos
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
        
        // IMPORTANTE: NÃO modificar estabelecimentosVinculados aqui
        // Essa coleção é gerenciada separadamente no método atualizarVinculosEstabelecimentos
    }

    /**
     * Cria vínculos entre o usuário e os estabelecimentos com seus respectivos papéis.
     * NOVO FORMATO: Inclui o papel (tipoUsuario) de cada vínculo.
     */
    private void criarVinculosComPapel(UsuariosSistema usuario, List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {
        for (UsuariosSistemaRequest.EstabelecimentoVinculoRequest vinculoRequest : vinculos) {
            // Verificar se já existe vínculo
            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(usuario.getUserId(), vinculoRequest.getEstabelecimentoId())
                    .ifPresentOrElse(
                            vinculoExistente -> {
                                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {
                                    // Reativar vínculo existente e atualizar papel
                                    vinculoExistente.setActive(true);
                                    vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                                    log.debug("Vínculo reativado com papel {} para usuário {} e estabelecimento {}", 
                                            vinculoRequest.getTipoUsuario(), usuario.getId(), vinculoRequest.getEstabelecimentoId());
                                }
                            },
                            () -> {
                                // Criar novo vínculo
                                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(vinculoRequest.getEstabelecimentoId())
                                        .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado com ID: " + vinculoRequest.getEstabelecimentoId()));

                                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                                vinculo.setUsuario(usuario);
                                vinculo.setEstabelecimento(estabelecimento);
                                vinculo.setTenant(usuario.getTenant());
                                vinculo.setTipoUsuario(vinculoRequest.getTipoUsuario()); // ← DEFINIR O PAPEL
                                vinculo.setActive(true);

                                usuarioEstabelecimentoRepository.save(vinculo);
                                log.debug("Vínculo criado com papel {} para usuário {} e estabelecimento {}", 
                                        vinculoRequest.getTipoUsuario(), usuario.getId(), vinculoRequest.getEstabelecimentoId());
                            }
                    );
        }
    }

    /**
     * Cria vínculos entre o usuário e os estabelecimentos informados.
     * DEPRECATED: Usar criarVinculosComPapel() com o papel especificado.
     */
    @Deprecated
    private void criarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {
        for (UUID estabelecimentoId : estabelecimentosIds) {
            // Verificar se já existe vínculo
            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(usuario.getUserId(), estabelecimentoId)
                    .ifPresentOrElse(
                            vinculoExistente -> {
                                if (Boolean.FALSE.equals(vinculoExistente.getActive())) {
                                    // Reativar vínculo existente
                                    vinculoExistente.setActive(true);
                                    usuarioEstabelecimentoRepository.save(vinculoExistente);
                                    log.debug("Vínculo reativado para usuário {} e estabelecimento {}", usuario.getId(), estabelecimentoId);
                                }
                            },
                            () -> {
                                // Criar novo vínculo
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

    /**
     * Atualiza os vínculos de estabelecimentos com seus respectivos papéis.
     * NOVO FORMATO: Inclui o papel (tipoUsuario) de cada vínculo.
     */
    private void atualizarVinculosComPapel(UsuariosSistema usuario, List<UsuariosSistemaRequest.EstabelecimentoVinculoRequest> vinculos) {
        // Buscar vínculos existentes
        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository
                .findByUsuarioUserId(usuario.getUserId());
        
        List<UUID> novosIds = vinculos.stream()
                .map(UsuariosSistemaRequest.EstabelecimentoVinculoRequest::getEstabelecimentoId)
                .collect(java.util.stream.Collectors.toList());
        
        // Desativar vínculos que não estão mais na lista
        for (UsuarioEstabelecimento vinculo : vinculosExistentes) {
            if (Boolean.TRUE.equals(vinculo.getActive()) && 
                !novosIds.contains(vinculo.getEstabelecimento().getId())) {
                vinculo.setActive(false);
                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo desativado para estabelecimento {}", vinculo.getEstabelecimento().getId());
            }
        }
        
        // Criar ou atualizar vínculos
        for (UsuariosSistemaRequest.EstabelecimentoVinculoRequest vinculoRequest : vinculos) {
            usuarioEstabelecimentoRepository
                    .findByUsuarioUserIdAndEstabelecimentoId(usuario.getUserId(), vinculoRequest.getEstabelecimentoId())
                    .ifPresentOrElse(
                            vinculoExistente -> {
                                // Atualizar papel do vínculo existente
                                vinculoExistente.setActive(true);
                                vinculoExistente.setTipoUsuario(vinculoRequest.getTipoUsuario());
                                usuarioEstabelecimentoRepository.save(vinculoExistente);
                                log.debug("Vínculo atualizado com papel {} para estabelecimento {}", 
                                        vinculoRequest.getTipoUsuario(), vinculoRequest.getEstabelecimentoId());
                            },
                            () -> {
                                // Criar novo vínculo
                                Estabelecimentos estabelecimento = estabelecimentosRepository.findById(vinculoRequest.getEstabelecimentoId())
                                        .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado"));
                                
                                UsuarioEstabelecimento vinculo = new UsuarioEstabelecimento();
                                vinculo.setUsuario(usuario);
                                vinculo.setEstabelecimento(estabelecimento);
                                vinculo.setTenant(usuario.getTenant());
                                vinculo.setTipoUsuario(vinculoRequest.getTipoUsuario()); // ← DEFINIR O PAPEL
                                vinculo.setActive(true);
                                
                                usuarioEstabelecimentoRepository.save(vinculo);
                                log.debug("Novo vínculo criado com papel {} para estabelecimento {}", 
                                        vinculoRequest.getTipoUsuario(), vinculoRequest.getEstabelecimentoId());
                            }
                    );
        }
    }

    /**
     * Atualiza os vínculos entre o usuário e os estabelecimentos informados.
     * DEPRECATED: Usar atualizarVinculosComPapel() com o papel especificado.
     */
    @Deprecated
    private void atualizarVinculosEstabelecimentos(UsuariosSistema usuario, List<UUID> estabelecimentosIds) {
        // Buscar vínculos ativos existentes
        List<UsuarioEstabelecimento> vinculosExistentes = usuarioEstabelecimentoRepository
                .findByUsuarioUserId(usuario.getUserId());

        List<UUID> idsExistentes = vinculosExistentes.stream()
                .filter(v -> Boolean.TRUE.equals(v.getActive()))
                .map(v -> v.getEstabelecimento().getId())
                .collect(Collectors.toList());

        // Desativar vínculos que não estão mais na lista
        for (UsuarioEstabelecimento vinculo : vinculosExistentes) {
            if (Boolean.TRUE.equals(vinculo.getActive()) && 
                !estabelecimentosIds.contains(vinculo.getEstabelecimento().getId())) {
                vinculo.setActive(false);
                usuarioEstabelecimentoRepository.save(vinculo);
                log.debug("Vínculo desativado para usuário {} e estabelecimento {}", usuario.getId(), vinculo.getEstabelecimento().getId());
            }
        }

        // Criar novos vínculos
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

        // Deletar foto antiga se existir
        if (usuario.getFotoUrl() != null && !usuario.getFotoUrl().isEmpty()) {
            supabaseStorageService.deletarFotoUsuario(usuario.getFotoUrl());
        }

        // Fazer upload da nova foto
        String fotoUrl = supabaseStorageService.uploadFotoUsuario(file, usuario.getUserId());

        // Atualizar URL da foto no banco
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

    /**
     * Enriquece o Response com informações adicionais do tenant, tipo de usuário, email e vínculos.
     * Versão otimizada que recebe a entidade diretamente.
     */
    private UsuariosSistemaResponse enrichResponseWithEntity(UsuariosSistema usuario) {
        UsuariosSistemaResponse response = usuariosSistemaMapper.toResponse(usuario);
        
        // Dados do Tenant
        if (usuario.getTenant() != null) {
            response.setTenantId(usuario.getTenant().getId());
            response.setTenantNome(usuario.getTenant().getNome());
            response.setTenantSlug(usuario.getTenant().getSlug());
        }
        
        // Buscar email do Supabase Auth
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
            } catch (Exception e) {
                log.error("Erro ao buscar email do Supabase para userId: {}", usuario.getUserId(), e);
            }
        }
        
        // Buscar vínculos com estabelecimentos
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
        
        // Determinar tipo de usuário baseado em vínculos (retorna ENUM)
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
    
    /**
     * Enriquece o Response quando só temos o Response (para listagens).
     * Busca a entidade para pegar dados completos.
     */
    private UsuariosSistemaResponse enrichResponse(UsuariosSistemaResponse response) {
        UsuariosSistema usuario = usuariosSistemaRepository.findById(response.getId())
                .orElse(null);
        
        if (usuario != null) {
            return enrichResponseWithEntity(usuario);
        }
        
        return response;
    }
}
