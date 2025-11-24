package com.upsaude.service.impl;

import com.upsaude.api.request.UsuariosSistemaRequest;
import com.upsaude.api.response.UsuariosSistemaResponse;
import com.upsaude.entity.UsuariosSistema;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.UsuariosSistemaMapper;
import com.upsaude.repository.UsuariosSistemaRepository;
import com.upsaude.service.UsuariosSistemaService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    @Override
    @Transactional
    public UsuariosSistemaResponse criar(UsuariosSistemaRequest request) {
        log.debug("Criando novo usuariossistema");

        validarDadosBasicos(request);

        UsuariosSistema usuariosSistema = usuariosSistemaMapper.fromRequest(request);
        usuariosSistema.setActive(true);

        UsuariosSistema usuariosSistemaSalvo = usuariosSistemaRepository.save(usuariosSistema);
        log.info("UsuariosSistema criado com sucesso. ID: {}", usuariosSistemaSalvo.getId());

        return usuariosSistemaMapper.toResponse(usuariosSistemaSalvo);
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

        return usuariosSistemaMapper.toResponse(usuariosSistemaAtualizado);
    }

    @Override
    @Transactional
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
    }

        private void atualizarDadosUsuariosSistema(UsuariosSistema usuariosSistema, UsuariosSistemaRequest request) {
        UsuariosSistema usuariosSistemaAtualizado = usuariosSistemaMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = usuariosSistema.getId();
        com.upsaude.entity.Tenant tenantOriginal = usuariosSistema.getTenant();
        Boolean activeOriginal = usuariosSistema.getActive();
        java.time.OffsetDateTime createdAtOriginal = usuariosSistema.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(usuariosSistemaAtualizado, usuariosSistema);
        
        // Restaura campos de controle
        usuariosSistema.setId(idOriginal);
        usuariosSistema.setTenant(tenantOriginal);
        usuariosSistema.setActive(activeOriginal);
        usuariosSistema.setCreatedAt(createdAtOriginal);
    }
}
