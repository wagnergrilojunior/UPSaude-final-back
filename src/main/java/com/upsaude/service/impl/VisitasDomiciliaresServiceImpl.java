package com.upsaude.service.impl;

import com.upsaude.api.request.VisitasDomiciliaresRequest;
import com.upsaude.api.response.VisitasDomiciliaresResponse;
import com.upsaude.entity.VisitasDomiciliares;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.VisitasDomiciliaresMapper;
import com.upsaude.repository.VisitasDomiciliaresRepository;
import com.upsaude.service.VisitasDomiciliaresService;
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

@Slf4j
@Service
@RequiredArgsConstructor
public class VisitasDomiciliaresServiceImpl implements VisitasDomiciliaresService {

    private final VisitasDomiciliaresRepository visitasDomiciliaresRepository;
    private final VisitasDomiciliaresMapper visitasDomiciliaresMapper;

    @Override
    @Transactional
    @CacheEvict(value = "visitasdomiciliares", allEntries = true)
    public VisitasDomiciliaresResponse criar(VisitasDomiciliaresRequest request) {
        log.debug("Criando novo visitasdomiciliares");

        VisitasDomiciliares visitasDomiciliares = visitasDomiciliaresMapper.fromRequest(request);
        visitasDomiciliares.setActive(true);

        VisitasDomiciliares visitasDomiciliaresSalvo = visitasDomiciliaresRepository.save(visitasDomiciliares);
        log.info("VisitasDomiciliares criado com sucesso. ID: {}", visitasDomiciliaresSalvo.getId());

        return visitasDomiciliaresMapper.toResponse(visitasDomiciliaresSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "visitasdomiciliares", key = "#id")
    public VisitasDomiciliaresResponse obterPorId(UUID id) {
        log.debug("Buscando visitasdomiciliares por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do visitasdomiciliares é obrigatório");
        }

        VisitasDomiciliares visitasDomiciliares = visitasDomiciliaresRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("VisitasDomiciliares não encontrado com ID: " + id));

        return visitasDomiciliaresMapper.toResponse(visitasDomiciliares);
    }

    @Override
    public Page<VisitasDomiciliaresResponse> listar(Pageable pageable) {
        log.debug("Listando VisitasDomiciliares paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<VisitasDomiciliares> visitasDomiciliares = visitasDomiciliaresRepository.findAll(pageable);
        return visitasDomiciliares.map(visitasDomiciliaresMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "visitasdomiciliares", key = "#id")
    public VisitasDomiciliaresResponse atualizar(UUID id, VisitasDomiciliaresRequest request) {
        log.debug("Atualizando visitasdomiciliares. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do visitasdomiciliares é obrigatório");
        }

        VisitasDomiciliares visitasDomiciliaresExistente = visitasDomiciliaresRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("VisitasDomiciliares não encontrado com ID: " + id));

        atualizarDadosVisitasDomiciliares(visitasDomiciliaresExistente, request);

        VisitasDomiciliares visitasDomiciliaresAtualizado = visitasDomiciliaresRepository.save(visitasDomiciliaresExistente);
        log.info("VisitasDomiciliares atualizado com sucesso. ID: {}", visitasDomiciliaresAtualizado.getId());

        return visitasDomiciliaresMapper.toResponse(visitasDomiciliaresAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "visitasdomiciliares", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo visitasdomiciliares. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do visitasdomiciliares é obrigatório");
        }

        VisitasDomiciliares visitasDomiciliares = visitasDomiciliaresRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("VisitasDomiciliares não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(visitasDomiciliares.getActive())) {
            throw new BadRequestException("VisitasDomiciliares já está inativo");
        }

        visitasDomiciliares.setActive(false);
        visitasDomiciliaresRepository.save(visitasDomiciliares);
        log.info("VisitasDomiciliares excluído (desativado) com sucesso. ID: {}", id);
    }

        private void atualizarDadosVisitasDomiciliares(VisitasDomiciliares visitasDomiciliares, VisitasDomiciliaresRequest request) {
        VisitasDomiciliares visitasDomiciliaresAtualizado = visitasDomiciliaresMapper.fromRequest(request);

        java.util.UUID idOriginal = visitasDomiciliares.getId();
        com.upsaude.entity.Tenant tenantOriginal = visitasDomiciliares.getTenant();
        Boolean activeOriginal = visitasDomiciliares.getActive();
        java.time.OffsetDateTime createdAtOriginal = visitasDomiciliares.getCreatedAt();

        BeanUtils.copyProperties(visitasDomiciliaresAtualizado, visitasDomiciliares);

        visitasDomiciliares.setId(idOriginal);
        visitasDomiciliares.setTenant(tenantOriginal);
        visitasDomiciliares.setActive(activeOriginal);
        visitasDomiciliares.setCreatedAt(createdAtOriginal);
    }
}
