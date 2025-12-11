package com.upsaude.service.impl;

import com.upsaude.api.request.ProcedimentosOdontologicosRequest;
import com.upsaude.api.response.ProcedimentosOdontologicosResponse;
import com.upsaude.entity.ProcedimentosOdontologicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ProcedimentosOdontologicosMapper;
import com.upsaude.repository.ProcedimentosOdontologicosRepository;
import com.upsaude.service.ProcedimentosOdontologicosService;
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
public class ProcedimentosOdontologicosServiceImpl implements ProcedimentosOdontologicosService {

    private final ProcedimentosOdontologicosRepository procedimentosOdontologicosRepository;
    private final ProcedimentosOdontologicosMapper procedimentosOdontologicosMapper;

    @Override
    @Transactional
    @CacheEvict(value = "procedimentosodontologicos", allEntries = true)
    public ProcedimentosOdontologicosResponse criar(ProcedimentosOdontologicosRequest request) {
        log.debug("Criando novo procedimentosodontologicos");

        ProcedimentosOdontologicos procedimentosOdontologicos = procedimentosOdontologicosMapper.fromRequest(request);
        procedimentosOdontologicos.setActive(true);

        ProcedimentosOdontologicos procedimentosOdontologicosSalvo = procedimentosOdontologicosRepository.save(procedimentosOdontologicos);
        log.info("ProcedimentosOdontologicos criado com sucesso. ID: {}", procedimentosOdontologicosSalvo.getId());

        return procedimentosOdontologicosMapper.toResponse(procedimentosOdontologicosSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "procedimentosodontologicos", key = "#id")
    public ProcedimentosOdontologicosResponse obterPorId(UUID id) {
        log.debug("Buscando procedimentosodontologicos por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do procedimentosodontologicos é obrigatório");
        }

        ProcedimentosOdontologicos procedimentosOdontologicos = procedimentosOdontologicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProcedimentosOdontologicos não encontrado com ID: " + id));

        return procedimentosOdontologicosMapper.toResponse(procedimentosOdontologicos);
    }

    @Override
    public Page<ProcedimentosOdontologicosResponse> listar(Pageable pageable) {
        log.debug("Listando ProcedimentosOdontologicos paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<ProcedimentosOdontologicos> procedimentosOdontologicos = procedimentosOdontologicosRepository.findAll(pageable);
        return procedimentosOdontologicos.map(procedimentosOdontologicosMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "procedimentosodontologicos", key = "#id")
    public ProcedimentosOdontologicosResponse atualizar(UUID id, ProcedimentosOdontologicosRequest request) {
        log.debug("Atualizando procedimentosodontologicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do procedimentosodontologicos é obrigatório");
        }

        ProcedimentosOdontologicos procedimentosOdontologicosExistente = procedimentosOdontologicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProcedimentosOdontologicos não encontrado com ID: " + id));

        atualizarDadosProcedimentosOdontologicos(procedimentosOdontologicosExistente, request);

        ProcedimentosOdontologicos procedimentosOdontologicosAtualizado = procedimentosOdontologicosRepository.save(procedimentosOdontologicosExistente);
        log.info("ProcedimentosOdontologicos atualizado com sucesso. ID: {}", procedimentosOdontologicosAtualizado.getId());

        return procedimentosOdontologicosMapper.toResponse(procedimentosOdontologicosAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "procedimentosodontologicos", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo procedimentosodontologicos. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do procedimentosodontologicos é obrigatório");
        }

        ProcedimentosOdontologicos procedimentosOdontologicos = procedimentosOdontologicosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ProcedimentosOdontologicos não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(procedimentosOdontologicos.getActive())) {
            throw new BadRequestException("ProcedimentosOdontologicos já está inativo");
        }

        procedimentosOdontologicos.setActive(false);
        procedimentosOdontologicosRepository.save(procedimentosOdontologicos);
        log.info("ProcedimentosOdontologicos excluído (desativado) com sucesso. ID: {}", id);
    }

        private void atualizarDadosProcedimentosOdontologicos(ProcedimentosOdontologicos procedimentosOdontologicos, ProcedimentosOdontologicosRequest request) {
        ProcedimentosOdontologicos procedimentosOdontologicosAtualizado = procedimentosOdontologicosMapper.fromRequest(request);

        java.util.UUID idOriginal = procedimentosOdontologicos.getId();
        com.upsaude.entity.Tenant tenantOriginal = procedimentosOdontologicos.getTenant();
        Boolean activeOriginal = procedimentosOdontologicos.getActive();
        java.time.OffsetDateTime createdAtOriginal = procedimentosOdontologicos.getCreatedAt();

        BeanUtils.copyProperties(procedimentosOdontologicosAtualizado, procedimentosOdontologicos);

        procedimentosOdontologicos.setId(idOriginal);
        procedimentosOdontologicos.setTenant(tenantOriginal);
        procedimentosOdontologicos.setActive(activeOriginal);
        procedimentosOdontologicos.setCreatedAt(createdAtOriginal);
    }
}
