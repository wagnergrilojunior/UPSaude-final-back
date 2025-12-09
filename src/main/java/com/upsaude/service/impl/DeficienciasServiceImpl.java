package com.upsaude.service.impl;

import com.upsaude.api.request.DeficienciasRequest;
import com.upsaude.api.response.DeficienciasResponse;
import com.upsaude.entity.Deficiencias;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DeficienciasMapper;
import com.upsaude.repository.DeficienciasRepository;
import com.upsaude.service.DeficienciasService;
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
 * Implementação do serviço de gerenciamento de Deficiências.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DeficienciasServiceImpl implements DeficienciasService {

    private final DeficienciasRepository deficienciasRepository;
    private final DeficienciasMapper deficienciasMapper;

    @Override
    @Transactional
    @CacheEvict(value = "deficiencias", allEntries = true)
    public DeficienciasResponse criar(DeficienciasRequest request) {
        log.debug("Criando nova deficiência");

        validarDadosBasicos(request);
        validarDuplicidade(null, request);

        Deficiencias deficiencias = deficienciasMapper.fromRequest(request);
        deficiencias.setActive(true);

        Deficiencias deficienciasSalvo = deficienciasRepository.save(deficiencias);
        log.info("Deficiência criada com sucesso. ID: {}", deficienciasSalvo.getId());

        return deficienciasMapper.toResponse(deficienciasSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "deficiencias", key = "#id")
    public DeficienciasResponse obterPorId(UUID id) {
        log.debug("Buscando deficiência por ID: {} (cache miss)", id);

        if (id == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        Deficiencias deficiencias = deficienciasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + id));

        return deficienciasMapper.toResponse(deficiencias);
    }

    @Override
    public Page<DeficienciasResponse> listar(Pageable pageable) {
        log.debug("Listando deficiências paginadas. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Deficiencias> deficiencias = deficienciasRepository.findAll(pageable);
        return deficiencias.map(deficienciasMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "deficiencias", key = "#id")
    public DeficienciasResponse atualizar(UUID id, DeficienciasRequest request) {
        log.debug("Atualizando deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        validarDadosBasicos(request);

        Deficiencias deficienciasExistente = deficienciasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + id));

        validarDuplicidade(id, request);

        atualizarDadosDeficiencias(deficienciasExistente, request);

        Deficiencias deficienciasAtualizado = deficienciasRepository.save(deficienciasExistente);
        log.info("Deficiência atualizada com sucesso. ID: {}", deficienciasAtualizado.getId());

        return deficienciasMapper.toResponse(deficienciasAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "deficiencias", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        Deficiencias deficiencias = deficienciasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + id));

        if (Boolean.FALSE.equals(deficiencias.getActive())) {
            throw new BadRequestException("Deficiência já está inativa");
        }

        deficiencias.setActive(false);
        deficienciasRepository.save(deficiencias);
        log.info("Deficiência excluída (desativada) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DeficienciasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da deficiência são obrigatórios");
        }
        if (request.getNome() == null || request.getNome().trim().isEmpty()) {
            throw new BadRequestException("Nome da deficiência é obrigatório");
        }
        // permanente e acompanhamentoContinuo não fazem parte do Request
    }

    /**
     * Valida se já existe uma deficiência com o mesmo nome no banco de dados.
     * 
     * @param id ID da deficiência sendo atualizada (null para criação)
     * @param request dados da deficiência sendo cadastrada/atualizada
     * @throws BadRequestException se já existe uma deficiência com o mesmo nome
     */
    private void validarDuplicidade(UUID id, DeficienciasRequest request) {
        if (request == null) {
            return;
        }

        // Valida duplicidade do nome
        if (request.getNome() != null && !request.getNome().trim().isEmpty()) {
            boolean nomeDuplicado;
            if (id == null) {
                // Criação: verifica se existe qualquer registro com este nome
                nomeDuplicado = deficienciasRepository.existsByNome(request.getNome().trim());
            } else {
                // Atualização: verifica se existe outro registro (diferente do atual) com este nome
                nomeDuplicado = deficienciasRepository.existsByNomeAndIdNot(request.getNome().trim(), id);
            }

            if (nomeDuplicado) {
                log.warn("Tentativa de cadastrar/atualizar deficiência com nome duplicado. Nome: {}", request.getNome());
                throw new BadRequestException(
                    String.format("Já existe uma deficiência cadastrada com o nome '%s' no banco de dados", request.getNome())
                );
            }
        }
    }

    private void atualizarDadosDeficiencias(Deficiencias deficiencias, DeficienciasRequest request) {
        Deficiencias deficienciasAtualizado = deficienciasMapper.fromRequest(request);
        
        // Preserva campos de controle
        UUID idOriginal = deficiencias.getId();
        Boolean activeOriginal = deficiencias.getActive();
        java.time.OffsetDateTime createdAtOriginal = deficiencias.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(deficienciasAtualizado, deficiencias);
        
        // Restaura campos de controle
        deficiencias.setId(idOriginal);
        deficiencias.setActive(activeOriginal);
        deficiencias.setCreatedAt(createdAtOriginal);
    }
}

