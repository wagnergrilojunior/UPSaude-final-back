package com.upsaude.service.impl;

import com.upsaude.api.request.UsuariosPerfisRequest;
import com.upsaude.api.response.UsuariosPerfisResponse;
import com.upsaude.entity.UsuariosPerfis;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.UsuariosPerfisMapper;
import com.upsaude.repository.UsuariosPerfisRepository;
import com.upsaude.service.UsuariosPerfisService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de UsuariosPerfis.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class UsuariosPerfisServiceImpl implements UsuariosPerfisService {

    private final UsuariosPerfisRepository usuariosPerfisRepository;
    private final UsuariosPerfisMapper usuariosPerfisMapper;

    @Override
    @Transactional
    @CacheEvict(value = "usuariosperfis", allEntries = true)
    public UsuariosPerfisResponse criar(UsuariosPerfisRequest request) {
        log.debug("Criando novo usuariosperfis");

        validarDadosBasicos(request);

        UsuariosPerfis usuariosPerfis = usuariosPerfisMapper.fromRequest(request);
        usuariosPerfis.setActive(true);

        UsuariosPerfis usuariosPerfisSalvo = usuariosPerfisRepository.save(usuariosPerfis);
        log.info("UsuariosPerfis criado com sucesso. ID: {}", usuariosPerfisSalvo.getId());

        return usuariosPerfisMapper.toResponse(usuariosPerfisSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "usuariosperfis", key = "#id")
    public UsuariosPerfisResponse obterPorId(UUID id) {
        log.debug("Buscando usuariosperfis por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do usuariosperfis é obrigatório");
        }

        UsuariosPerfis usuariosPerfis = usuariosPerfisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosPerfis não encontrado com ID: " + id));

        return usuariosPerfisMapper.toResponse(usuariosPerfis);
    }

    @Override
    public Page<UsuariosPerfisResponse> listar(Pageable pageable) {
        log.debug("Listando UsuariosPerfis paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<UsuariosPerfis> usuariosPerfis = usuariosPerfisRepository.findAll(pageable);
        return usuariosPerfis.map(usuariosPerfisMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "usuariosperfis", key = "#id")
    public UsuariosPerfisResponse atualizar(UUID id, UsuariosPerfisRequest request) {
        log.debug("Atualizando usuariosperfis. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuariosperfis é obrigatório");
        }

        validarDadosBasicos(request);

        UsuariosPerfis usuariosPerfisExistente = usuariosPerfisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosPerfis não encontrado com ID: " + id));

        atualizarDadosUsuariosPerfis(usuariosPerfisExistente, request);

        UsuariosPerfis usuariosPerfisAtualizado = usuariosPerfisRepository.save(usuariosPerfisExistente);
        log.info("UsuariosPerfis atualizado com sucesso. ID: {}", usuariosPerfisAtualizado.getId());

        return usuariosPerfisMapper.toResponse(usuariosPerfisAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "usuariosperfis", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo usuariosperfis. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do usuariosperfis é obrigatório");
        }

        UsuariosPerfis usuariosPerfis = usuariosPerfisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("UsuariosPerfis não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(usuariosPerfis.getActive())) {
            throw new BadRequestException("UsuariosPerfis já está inativo");
        }

        usuariosPerfis.setActive(false);
        usuariosPerfisRepository.save(usuariosPerfis);
        log.info("UsuariosPerfis excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(UsuariosPerfisRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do usuariosperfis são obrigatórios");
        }
    }

        private void atualizarDadosUsuariosPerfis(UsuariosPerfis usuariosPerfis, UsuariosPerfisRequest request) {
        UsuariosPerfis usuariosPerfisAtualizado = usuariosPerfisMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = usuariosPerfis.getId();
        com.upsaude.entity.Tenant tenantOriginal = usuariosPerfis.getTenant();
        Boolean activeOriginal = usuariosPerfis.getActive();
        java.time.OffsetDateTime createdAtOriginal = usuariosPerfis.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(usuariosPerfisAtualizado, usuariosPerfis);
        
        // Restaura campos de controle
        usuariosPerfis.setId(idOriginal);
        usuariosPerfis.setTenant(tenantOriginal);
        usuariosPerfis.setActive(activeOriginal);
        usuariosPerfis.setCreatedAt(createdAtOriginal);
    }
}
