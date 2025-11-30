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
    public DeficienciasResponse criar(DeficienciasRequest request) {
        log.debug("Criando nova deficiência");

        validarDadosBasicos(request);

        Deficiencias deficiencias = deficienciasMapper.fromRequest(request);
        deficiencias.setActive(true);

        Deficiencias deficienciasSalvo = deficienciasRepository.save(deficiencias);
        log.info("Deficiência criada com sucesso. ID: {}", deficienciasSalvo.getId());

        return deficienciasMapper.toResponse(deficienciasSalvo);
    }

    @Override
    @Transactional
    public DeficienciasResponse obterPorId(UUID id) {
        log.debug("Buscando deficiência por ID: {}", id);

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
    public DeficienciasResponse atualizar(UUID id, DeficienciasRequest request) {
        log.debug("Atualizando deficiência. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID da deficiência é obrigatório");
        }

        validarDadosBasicos(request);

        Deficiencias deficienciasExistente = deficienciasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Deficiência não encontrada com ID: " + id));

        atualizarDadosDeficiencias(deficienciasExistente, request);

        Deficiencias deficienciasAtualizado = deficienciasRepository.save(deficienciasExistente);
        log.info("Deficiência atualizada com sucesso. ID: {}", deficienciasAtualizado.getId());

        return deficienciasMapper.toResponse(deficienciasAtualizado);
    }

    @Override
    @Transactional
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
        if (request.getPermanente() == null) {
            throw new BadRequestException("Campo permanente é obrigatório");
        }
        if (request.getAcompanhamentoContinuo() == null) {
            throw new BadRequestException("Campo acompanhamentoContinuo é obrigatório");
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

