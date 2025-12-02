package com.upsaude.service.impl;

import com.upsaude.api.request.AlergiasRequest;
import com.upsaude.api.response.AlergiasResponse;
import com.upsaude.entity.Alergias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.AlergiasMapper;
import com.upsaude.repository.AlergiasRepository;
import com.upsaude.service.AlergiasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Alergias.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlergiasServiceImpl implements AlergiasService {

    private final AlergiasRepository alergiasRepository;
    private final AlergiasMapper alergiasMapper;

    @Override
    @Transactional
    @CacheEvict(value = "alergias", allEntries = true)
    public AlergiasResponse criar(AlergiasRequest request) {
        log.debug("Criando novo alergias");

        validarDadosBasicos(request);

        Alergias alergias = alergiasMapper.fromRequest(request);
        alergias.setActive(true);

        Alergias alergiasSalvo = alergiasRepository.save(alergias);
        log.info("Alergias criado com sucesso. ID: {}", alergiasSalvo.getId());

        return alergiasMapper.toResponse(alergiasSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "alergias", key = "#id")
    public AlergiasResponse obterPorId(UUID id) {
        log.debug("Buscando alergias por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID do alergias é obrigatório");
        }

        Alergias alergias = alergiasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alergias não encontrado com ID: " + id));

        return alergiasMapper.toResponse(alergias);
    }

    @Override
    public Page<AlergiasResponse> listar(Pageable pageable) {
        log.debug("Listando Alergias paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Alergias> alergias = alergiasRepository.findAll(pageable);
        return alergias.map(alergiasMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "alergias", key = "#id")
    public AlergiasResponse atualizar(UUID id, AlergiasRequest request) {
        log.debug("Atualizando alergias. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do alergias é obrigatório");
        }

        validarDadosBasicos(request);

        Alergias alergiasExistente = alergiasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alergias não encontrado com ID: " + id));

        atualizarDadosAlergias(alergiasExistente, request);

        Alergias alergiasAtualizado = alergiasRepository.save(alergiasExistente);
        log.info("Alergias atualizado com sucesso. ID: {}", alergiasAtualizado.getId());

        return alergiasMapper.toResponse(alergiasAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "alergias", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo alergias. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do alergias é obrigatório");
        }

        Alergias alergias = alergiasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Alergias não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(alergias.getActive())) {
            throw new BadRequestException("Alergias já está inativo");
        }

        alergias.setActive(false);
        alergiasRepository.save(alergias);
        log.info("Alergias excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(AlergiasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do alergias são obrigatórios");
        }
    }

    private void atualizarDadosAlergias(Alergias alergias, AlergiasRequest request) {
        Alergias alergiasAtualizado = alergiasMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = alergias.getId();
        Boolean activeOriginal = alergias.getActive();
        java.time.OffsetDateTime createdAtOriginal = alergias.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(alergiasAtualizado, alergias);
        
        // Restaura campos de controle
        alergias.setId(idOriginal);
        alergias.setActive(activeOriginal);
        alergias.setCreatedAt(createdAtOriginal);
    }
}
