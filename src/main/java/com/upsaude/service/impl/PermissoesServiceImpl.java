package com.upsaude.service.impl;

import com.upsaude.api.request.PermissoesRequest;
import com.upsaude.api.response.PermissoesResponse;
import com.upsaude.entity.Permissoes;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PermissoesMapper;
import com.upsaude.repository.PermissoesRepository;
import com.upsaude.service.PermissoesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Permissoes.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissoesServiceImpl implements PermissoesService {

    private final PermissoesRepository permissoesRepository;
    private final PermissoesMapper permissoesMapper;

    @Override
    @Transactional
    public PermissoesResponse criar(PermissoesRequest request) {
        log.debug("Criando novo permissoes");

        validarDadosBasicos(request);

        Permissoes permissoes = permissoesMapper.fromRequest(request);
        permissoes.setActive(true);

        Permissoes permissoesSalvo = permissoesRepository.save(permissoes);
        log.info("Permissoes criado com sucesso. ID: {}", permissoesSalvo.getId());

        return permissoesMapper.toResponse(permissoesSalvo);
    }

    @Override
    @Transactional
    public PermissoesResponse obterPorId(UUID id) {
        log.debug("Buscando permissoes por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do permissoes é obrigatório");
        }

        Permissoes permissoes = permissoesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permissoes não encontrado com ID: " + id));

        return permissoesMapper.toResponse(permissoes);
    }

    @Override
    public Page<PermissoesResponse> listar(Pageable pageable) {
        log.debug("Listando Permissoes paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Permissoes> permissoes = permissoesRepository.findAll(pageable);
        return permissoes.map(permissoesMapper::toResponse);
    }

    @Override
    @Transactional
    public PermissoesResponse atualizar(UUID id, PermissoesRequest request) {
        log.debug("Atualizando permissoes. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do permissoes é obrigatório");
        }

        validarDadosBasicos(request);

        Permissoes permissoesExistente = permissoesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permissoes não encontrado com ID: " + id));

        atualizarDadosPermissoes(permissoesExistente, request);

        Permissoes permissoesAtualizado = permissoesRepository.save(permissoesExistente);
        log.info("Permissoes atualizado com sucesso. ID: {}", permissoesAtualizado.getId());

        return permissoesMapper.toResponse(permissoesAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo permissoes. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do permissoes é obrigatório");
        }

        Permissoes permissoes = permissoesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Permissoes não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(permissoes.getActive())) {
            throw new BadRequestException("Permissoes já está inativo");
        }

        permissoes.setActive(false);
        permissoesRepository.save(permissoes);
        log.info("Permissoes excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(PermissoesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do permissoes são obrigatórios");
        }
    }

        private void atualizarDadosPermissoes(Permissoes permissoes, PermissoesRequest request) {
        Permissoes permissoesAtualizado = permissoesMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = permissoes.getId();
        com.upsaude.entity.Tenant tenantOriginal = permissoes.getTenant();
        Boolean activeOriginal = permissoes.getActive();
        java.time.OffsetDateTime createdAtOriginal = permissoes.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(permissoesAtualizado, permissoes);
        
        // Restaura campos de controle
        permissoes.setId(idOriginal);
        permissoes.setTenant(tenantOriginal);
        permissoes.setActive(activeOriginal);
        permissoes.setCreatedAt(createdAtOriginal);
    }
}
