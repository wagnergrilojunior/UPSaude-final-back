package com.upsaude.service.impl;

import java.util.Objects;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.upsaude.api.request.profissional.ConselhosProfissionaisRequest;
import com.upsaude.api.response.profissional.ConselhosProfissionaisResponse;
import com.upsaude.cache.CacheKeyUtil;
import com.upsaude.entity.profissional.ConselhosProfissionais;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.profissional.ConselhosProfissionaisMapper;
import com.upsaude.repository.profissional.ConselhosProfissionaisRepository;
import com.upsaude.service.profissional.ConselhosProfissionaisService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConselhosProfissionaisServiceImpl implements ConselhosProfissionaisService {

    private final ConselhosProfissionaisRepository conselhosProfissionaisRepository;
    private final ConselhosProfissionaisMapper conselhosProfissionaisMapper;

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSELHOS_PROFISSIONAIS, allEntries = true)
    public ConselhosProfissionaisResponse criar(ConselhosProfissionaisRequest request) {
        log.debug("Criando novo conselhosprofissionais");

        ConselhosProfissionais conselhosProfissionais = conselhosProfissionaisMapper.fromRequest(request);
        conselhosProfissionais.setActive(true);

        ConselhosProfissionais conselhosProfissionaisSalvo = conselhosProfissionaisRepository.save(conselhosProfissionais);
        log.info("ConselhosProfissionais criado com sucesso. ID: {}", conselhosProfissionaisSalvo.getId());

        return conselhosProfissionaisMapper.toResponse(conselhosProfissionaisSalvo);
    }

    @Override
    @Transactional
    @Cacheable(cacheNames = CacheKeyUtil.CACHE_CONSELHOS_PROFISSIONAIS, keyGenerator = "conselhosProfissionaisCacheKeyGenerator")
    public ConselhosProfissionaisResponse obterPorId(UUID id) {
        log.debug("Buscando conselhosprofissionais por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID do conselhosprofissionais é obrigatório");
        }

        ConselhosProfissionais conselhosProfissionais = conselhosProfissionaisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ConselhosProfissionais não encontrado com ID: " + id));

        return conselhosProfissionaisMapper.toResponse(conselhosProfissionais);
    }

    @Override
    public Page<ConselhosProfissionaisResponse> listar(Pageable pageable) {
        log.debug("Listando ConselhosProfissionais paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ConselhosProfissionais> conselhosProfissionais = conselhosProfissionaisRepository.findAll(pageable);
        return conselhosProfissionais.map(conselhosProfissionaisMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSELHOS_PROFISSIONAIS, keyGenerator = "conselhosProfissionaisCacheKeyGenerator")
    public ConselhosProfissionaisResponse atualizar(UUID id, ConselhosProfissionaisRequest request) {
        log.debug("Atualizando conselhosprofissionais. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do conselhosprofissionais é obrigatório");
        }

        ConselhosProfissionais conselhosProfissionaisExistente = conselhosProfissionaisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ConselhosProfissionais não encontrado com ID: " + id));

        atualizarDadosConselhosProfissionais(conselhosProfissionaisExistente, request);

        ConselhosProfissionais conselhosProfissionaisAtualizado = conselhosProfissionaisRepository.save(Objects.requireNonNull(conselhosProfissionaisExistente));
        log.info("ConselhosProfissionais atualizado com sucesso. ID: {}", conselhosProfissionaisAtualizado.getId());

        return conselhosProfissionaisMapper.toResponse(conselhosProfissionaisAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(cacheNames = CacheKeyUtil.CACHE_CONSELHOS_PROFISSIONAIS, keyGenerator = "conselhosProfissionaisCacheKeyGenerator")
    public void excluir(UUID id) {
        log.debug("Excluindo conselhosprofissionais. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do conselhosprofissionais é obrigatório");
        }

        ConselhosProfissionais conselhosProfissionais = conselhosProfissionaisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ConselhosProfissionais não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(conselhosProfissionais.getActive())) {
            throw new BadRequestException("ConselhosProfissionais já está inativo");
        }

        conselhosProfissionais.setActive(false);
        conselhosProfissionaisRepository.save(conselhosProfissionais);
        log.info("ConselhosProfissionais excluído (desativado) com sucesso. ID: {}", id);
    }

    private void atualizarDadosConselhosProfissionais(ConselhosProfissionais conselhosProfissionais, ConselhosProfissionaisRequest request) {
        ConselhosProfissionais conselhosProfissionaisAtualizado = Objects.requireNonNull(conselhosProfissionaisMapper.fromRequest(request));

        java.util.UUID idOriginal = conselhosProfissionais.getId();
        Boolean activeOriginal = conselhosProfissionais.getActive();
        java.time.OffsetDateTime createdAtOriginal = conselhosProfissionais.getCreatedAt();

        BeanUtils.copyProperties(conselhosProfissionaisAtualizado, Objects.requireNonNull(conselhosProfissionais));

        conselhosProfissionais.setId(idOriginal);
        conselhosProfissionais.setActive(activeOriginal);
        conselhosProfissionais.setCreatedAt(createdAtOriginal);
    }
}
