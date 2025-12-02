package com.upsaude.service.impl;

import com.upsaude.api.request.PapeisRequest;
import com.upsaude.api.response.PapeisResponse;
import com.upsaude.entity.Papeis;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.PapeisMapper;
import com.upsaude.repository.PapeisRepository;
import com.upsaude.service.PapeisService;
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
 * Implementação do serviço de gerenciamento de Papeis.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PapeisServiceImpl implements PapeisService {

    private final PapeisRepository papeisRepository;
    private final PapeisMapper papeisMapper;

    @Override
    @Transactional
    @CacheEvict(value = "papeis", allEntries = true)
    public PapeisResponse criar(PapeisRequest request) {
        log.debug("Criando novo papeis");

        validarDadosBasicos(request);

        Papeis papeis = papeisMapper.fromRequest(request);
        papeis.setActive(true);

        Papeis papeisSalvo = papeisRepository.save(papeis);
        log.info("Papeis criado com sucesso. ID: {}", papeisSalvo.getId());

        return papeisMapper.toResponse(papeisSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "papeis", key = "#id")
    public PapeisResponse obterPorId(UUID id) {
        log.debug("Buscando papeis por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do papeis é obrigatório");
        }

        Papeis papeis = papeisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Papeis não encontrado com ID: " + id));

        return papeisMapper.toResponse(papeis);
    }

    @Override
    public Page<PapeisResponse> listar(Pageable pageable) {
        log.debug("Listando Papeis paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Papeis> papeis = papeisRepository.findAll(pageable);
        return papeis.map(papeisMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "papeis", key = "#id")
    public PapeisResponse atualizar(UUID id, PapeisRequest request) {
        log.debug("Atualizando papeis. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do papeis é obrigatório");
        }

        validarDadosBasicos(request);

        Papeis papeisExistente = papeisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Papeis não encontrado com ID: " + id));

        atualizarDadosPapeis(papeisExistente, request);

        Papeis papeisAtualizado = papeisRepository.save(papeisExistente);
        log.info("Papeis atualizado com sucesso. ID: {}", papeisAtualizado.getId());

        return papeisMapper.toResponse(papeisAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "papeis", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo papeis. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do papeis é obrigatório");
        }

        Papeis papeis = papeisRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Papeis não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(papeis.getActive())) {
            throw new BadRequestException("Papeis já está inativo");
        }

        papeis.setActive(false);
        papeisRepository.save(papeis);
        log.info("Papeis excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(PapeisRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do papeis são obrigatórios");
        }
    }

    private void atualizarDadosPapeis(Papeis papeis, PapeisRequest request) {
        Papeis papeisAtualizado = papeisMapper.fromRequest(request);
        
        // Preserva campos de controle (Papeis não tem tenant)
        java.util.UUID idOriginal = papeis.getId();
        Boolean activeOriginal = papeis.getActive();
        java.time.OffsetDateTime createdAtOriginal = papeis.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(papeisAtualizado, papeis);
        
        // Restaura campos de controle
        papeis.setId(idOriginal);
        papeis.setActive(activeOriginal);
        papeis.setCreatedAt(createdAtOriginal);
    }
}
