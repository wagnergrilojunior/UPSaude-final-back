package com.upsaude.service.impl;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import com.upsaude.entity.Estabelecimentos;
import com.upsaude.entity.Tenant;
import com.upsaude.entity.UsuarioEstabelecimento;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.integration.supabase.SupabaseStorageService;
import com.upsaude.mapper.UsuariosSistemaMapper;
import com.upsaude.repository.EstabelecimentosRepository;
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
    private final SupabaseStorageService supabaseStorageService;

    @Override
    @Transactional
    @CacheEvict(value = "usuariossistema", allEntries = true)
    public UsuariosSistemaResponse criar(UsuariosSistemaRequest request) {
        log.debug("Criando novo usuariossistema");

        validarDadosBasicos(request);

        // Buscar tenant
        Tenant tenant = tenantRepository.findById(request.getTenantId())
                .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenantId()));

        UsuariosSistema usuariosSistema = usuariosSistemaMapper.fromRequest(request);
        usuariosSistema.setTenant(tenant);
        usuariosSistema.setActive(true);

        UsuariosSistema usuariosSistemaSalvo = usuariosSistemaRepository.save(usuariosSistema);
        log.info("UsuariosSistema criado com sucesso. ID: {}", usuariosSistemaSalvo.getId());

        // Criar vínculos com estabelecimentos
        if (request.getEstabelecimentosIds() != null && !request.getEstabelecimentosIds().isEmpty()) {
            criarVinculosEstabelecimentos(usuariosSistemaSalvo, request.getEstabelecimentosIds());
        }

        return usuariosSistemaMapper.toResponse(usuariosSistemaSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "usuariossistema", key = "#id")
    public UsuariosSistemaResponse obterPorId(UUID id) {
        log.debug("Buscando usuariossistema por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema usuariosSistema = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        return usuariosSistemaMapper.toResponse(usuariosSistema);
    }

    @Override
    public Page<UsuariosSistemaResponse> listar(Pageable pageable) {
        log.debug("Listando UsuariosSistemas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<UsuariosSistema> usuariosSistemas = usuariosSistemaRepository.findAll(pageable);
        return usuariosSistemas.map(usuariosSistemaMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "usuariossistema", key = "#id")
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

        // Atualizar vínculos com estabelecimentos
        if (request.getEstabelecimentosIds() != null) {
            atualizarVinculosEstabelecimentos(usuariosSistemaAtualizado, request.getEstabelecimentosIds());
        }

        return usuariosSistemaMapper.toResponse(usuariosSistemaAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "usuariossistema", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo usuariossistema. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuariossistema é obrigatório");
        }

        UsuariosSistema usuariosSistema = usuariosSistemaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosSistema não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(usuariosSistema.getActive())) {
            throw new BadRequestException("UsuariosSistema já está inativo");
        }

        usuariosSistema.setActive(false);
        usuariosSistemaRepository.save(usuariosSistema);
        log.info("UsuariosSistema excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(UsuariosSistemaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do usuariossistema são obrigatórios");
        }
        if (request.getUserId() == null) {
            throw new BadRequestException("ID do usuário Supabase é obrigatório");
        }
        if (request.getTenantId() == null) {
            throw new BadRequestException("ID do tenant é obrigatório");
        }
        if (request.getTipoUsuario() == null) {
            throw new BadRequestException("Tipo de usuário é obrigatório");
        }
        if (request.getEstabelecimentosIds() == null || request.getEstabelecimentosIds().isEmpty()) {
            throw new BadRequestException("É necessário vincular o usuário a pelo menos um estabelecimento.");
        }
    }

        private void atualizarDadosUsuariosSistema(UsuariosSistema usuariosSistema, UsuariosSistemaRequest request) {
        // Buscar tenant se foi informado
        if (request.getTenantId() != null) {
            Tenant tenant = tenantRepository.findById(request.getTenantId())
                    .orElseThrow(() -> new NotFoundException("Tenant não encontrado com ID: " + request.getTenantId()));
            usuariosSistema.setTenant(tenant);
        }
        
        UsuariosSistema usuariosSistemaAtualizado = usuariosSistemaMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = usuariosSistema.getId();
        Tenant tenantOriginal = usuariosSistema.getTenant();
        Boolean activeOriginal = usuariosSistema.getActive();
        java.time.OffsetDateTime createdAtOriginal = usuariosSistema.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(usuariosSistemaAtualizado, usuariosSistema);
        
        // Restaura campos de controle
        usuariosSistema.setId(idOriginal);
        usuariosSistema.setTenant(tenantOriginal != null ? tenantOriginal : usuariosSistema.getTenant());
        usuariosSistema.setActive(activeOriginal);
        usuariosSistema.setCreatedAt(createdAtOriginal);
    }

    /**
     * Cria vínculos entre o usuário e os estabelecimentos informados.
     */
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
     * Atualiza os vínculos entre o usuário e os estabelecimentos informados.
     * Remove vínculos que não estão mais na lista e cria novos vínculos.
     */
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
    @CacheEvict(value = "usuariossistema", key = "#id")
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
    @CacheEvict(value = "usuariossistema", key = "#id")
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
}
