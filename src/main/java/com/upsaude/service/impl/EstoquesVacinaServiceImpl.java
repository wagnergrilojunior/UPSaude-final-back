package com.upsaude.service.impl;
import com.upsaude.entity.sistema.Tenant;

import com.upsaude.api.request.vacina.EstoquesVacinaRequest;
import com.upsaude.api.response.vacina.EstoquesVacinaResponse;
import com.upsaude.entity.vacina.EstoquesVacina;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EstoquesVacinaMapper;
import com.upsaude.repository.saude_publica.vacina.EstoquesVacinaRepository;
import com.upsaude.service.vacina.EstoquesVacinaService;
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
public class EstoquesVacinaServiceImpl implements EstoquesVacinaService {

    private final EstoquesVacinaRepository estoquesVacinaRepository;
    private final EstoquesVacinaMapper estoquesVacinaMapper;

    @Override
    @Transactional
    @CacheEvict(value = "estoquesvacina", allEntries = true)
    public EstoquesVacinaResponse criar(EstoquesVacinaRequest request) {
        log.debug("Criando novo estoquesvacina");

        EstoquesVacina estoquesVacina = estoquesVacinaMapper.fromRequest(request);
        estoquesVacina.setActive(true);

        EstoquesVacina estoquesVacinaSalvo = estoquesVacinaRepository.save(estoquesVacina);
        log.info("EstoquesVacina criado com sucesso. ID: {}", estoquesVacinaSalvo.getId());

        return estoquesVacinaMapper.toResponse(estoquesVacinaSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "estoquesvacina", key = "#id")
    public EstoquesVacinaResponse obterPorId(UUID id) {
        log.debug("Buscando estoquesvacina por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do estoquesvacina é obrigatório");
        }

        EstoquesVacina estoquesVacina = estoquesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EstoquesVacina não encontrado com ID: " + id));

        return estoquesVacinaMapper.toResponse(estoquesVacina);
    }

    @Override
    public Page<EstoquesVacinaResponse> listar(Pageable pageable) {
        log.debug("Listando EstoquesVacinas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<EstoquesVacina> estoquesVacinas = estoquesVacinaRepository.findAll(pageable);
        return estoquesVacinas.map(estoquesVacinaMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "estoquesvacina", key = "#id")
    public EstoquesVacinaResponse atualizar(UUID id, EstoquesVacinaRequest request) {
        log.debug("Atualizando estoquesvacina. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estoquesvacina é obrigatório");
        }

        EstoquesVacina estoquesVacinaExistente = estoquesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EstoquesVacina não encontrado com ID: " + id));

        atualizarDadosEstoquesVacina(estoquesVacinaExistente, request);

        EstoquesVacina estoquesVacinaAtualizado = estoquesVacinaRepository.save(estoquesVacinaExistente);
        log.info("EstoquesVacina atualizado com sucesso. ID: {}", estoquesVacinaAtualizado.getId());

        return estoquesVacinaMapper.toResponse(estoquesVacinaAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "estoquesvacina", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo estoquesvacina. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estoquesvacina é obrigatório");
        }

        EstoquesVacina estoquesVacina = estoquesVacinaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("EstoquesVacina não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(estoquesVacina.getActive())) {
            throw new BadRequestException("EstoquesVacina já está inativo");
        }

        estoquesVacina.setActive(false);
        estoquesVacinaRepository.save(estoquesVacina);
        log.info("EstoquesVacina excluído (desativado) com sucesso. ID: {}", id);
    }

        private void atualizarDadosEstoquesVacina(EstoquesVacina estoquesVacina, EstoquesVacinaRequest request) {
        EstoquesVacina estoquesVacinaAtualizado = estoquesVacinaMapper.fromRequest(request);

        java.util.UUID idOriginal = estoquesVacina.getId();
        com.upsaude.entity.Tenant tenantOriginal = estoquesVacina.getTenant();
        Boolean activeOriginal = estoquesVacina.getActive();
        java.time.OffsetDateTime createdAtOriginal = estoquesVacina.getCreatedAt();

        BeanUtils.copyProperties(estoquesVacinaAtualizado, estoquesVacina);

        estoquesVacina.setId(idOriginal);
        estoquesVacina.setTenant(tenantOriginal);
        estoquesVacina.setActive(activeOriginal);
        estoquesVacina.setCreatedAt(createdAtOriginal);
    }
}
