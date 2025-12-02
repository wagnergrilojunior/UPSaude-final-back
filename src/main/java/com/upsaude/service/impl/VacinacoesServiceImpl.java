package com.upsaude.service.impl;

import com.upsaude.api.request.VacinacoesRequest;
import com.upsaude.api.response.VacinacoesResponse;
import com.upsaude.entity.Vacinacoes;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.VacinacoesMapper;
import com.upsaude.repository.VacinacoesRepository;
import com.upsaude.service.VacinacoesService;
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
 * Implementação do serviço de gerenciamento de Vacinacoes.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class VacinacoesServiceImpl implements VacinacoesService {

    private final VacinacoesRepository vacinacoesRepository;
    private final VacinacoesMapper vacinacoesMapper;

    @Override
    @Transactional
    @CacheEvict(value = "vacinacoes", allEntries = true)
    public VacinacoesResponse criar(VacinacoesRequest request) {
        log.debug("Criando novo vacinacoes");

        validarDadosBasicos(request);

        Vacinacoes vacinacoes = vacinacoesMapper.fromRequest(request);
        vacinacoes.setActive(true);

        Vacinacoes vacinacoesSalvo = vacinacoesRepository.save(vacinacoes);
        log.info("Vacinacoes criado com sucesso. ID: {}", vacinacoesSalvo.getId());

        return vacinacoesMapper.toResponse(vacinacoesSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "vacinacoes", key = "#id")
    public VacinacoesResponse obterPorId(UUID id) {
        log.debug("Buscando vacinacoes por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do vacinacoes é obrigatório");
        }

        Vacinacoes vacinacoes = vacinacoesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacinacoes não encontrado com ID: " + id));

        return vacinacoesMapper.toResponse(vacinacoes);
    }

    @Override
    public Page<VacinacoesResponse> listar(Pageable pageable) {
        log.debug("Listando Vacinacoes paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Vacinacoes> vacinacoes = vacinacoesRepository.findAll(pageable);
        return vacinacoes.map(vacinacoesMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "vacinacoes", key = "#id")
    public VacinacoesResponse atualizar(UUID id, VacinacoesRequest request) {
        log.debug("Atualizando vacinacoes. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vacinacoes é obrigatório");
        }

        validarDadosBasicos(request);

        Vacinacoes vacinacoesExistente = vacinacoesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacinacoes não encontrado com ID: " + id));

        atualizarDadosVacinacoes(vacinacoesExistente, request);

        Vacinacoes vacinacoesAtualizado = vacinacoesRepository.save(vacinacoesExistente);
        log.info("Vacinacoes atualizado com sucesso. ID: {}", vacinacoesAtualizado.getId());

        return vacinacoesMapper.toResponse(vacinacoesAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "vacinacoes", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo vacinacoes. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do vacinacoes é obrigatório");
        }

        Vacinacoes vacinacoes = vacinacoesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Vacinacoes não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(vacinacoes.getActive())) {
            throw new BadRequestException("Vacinacoes já está inativo");
        }

        vacinacoes.setActive(false);
        vacinacoesRepository.save(vacinacoes);
        log.info("Vacinacoes excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(VacinacoesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do vacinacoes são obrigatórios");
        }
    }

        private void atualizarDadosVacinacoes(Vacinacoes vacinacoes, VacinacoesRequest request) {
        Vacinacoes vacinacoesAtualizado = vacinacoesMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = vacinacoes.getId();
        com.upsaude.entity.Tenant tenantOriginal = vacinacoes.getTenant();
        Boolean activeOriginal = vacinacoes.getActive();
        java.time.OffsetDateTime createdAtOriginal = vacinacoes.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(vacinacoesAtualizado, vacinacoes);
        
        // Restaura campos de controle
        vacinacoes.setId(idOriginal);
        vacinacoes.setTenant(tenantOriginal);
        vacinacoes.setActive(activeOriginal);
        vacinacoes.setCreatedAt(createdAtOriginal);
    }
}
