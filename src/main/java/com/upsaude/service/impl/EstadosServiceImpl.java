package com.upsaude.service.impl;

import com.upsaude.api.request.EstadosRequest;
import com.upsaude.api.response.EstadosResponse;
import com.upsaude.entity.Estados;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.EstadosMapper;
import com.upsaude.repository.EstadosRepository;
import com.upsaude.service.EstadosService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Estados.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EstadosServiceImpl implements EstadosService {

    private final EstadosRepository estadosRepository;
    private final EstadosMapper estadosMapper;

    @Override
    @Transactional
    public EstadosResponse criar(EstadosRequest request) {
        log.debug("Criando novo estados");

        validarDadosBasicos(request);

        Estados estados = estadosMapper.fromRequest(request);
        estados.setActive(true);

        Estados estadosSalvo = estadosRepository.save(estados);
        log.info("Estados criado com sucesso. ID: {}", estadosSalvo.getId());

        return estadosMapper.toResponse(estadosSalvo);
    }

    @Override
    @Transactional
    public EstadosResponse obterPorId(UUID id) {
        log.debug("Buscando estados por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estados é obrigatório");
        }

        Estados estados = estadosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estados não encontrado com ID: " + id));

        return estadosMapper.toResponse(estados);
    }

    @Override
    public Page<EstadosResponse> listar(Pageable pageable) {
        log.debug("Listando Estados paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Estados> estados = estadosRepository.findAll(pageable);
        return estados.map(estadosMapper::toResponse);
    }

    @Override
    @Transactional
    public EstadosResponse atualizar(UUID id, EstadosRequest request) {
        log.debug("Atualizando estados. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estados é obrigatório");
        }

        validarDadosBasicos(request);

        Estados estadosExistente = estadosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estados não encontrado com ID: " + id));

        atualizarDadosEstados(estadosExistente, request);

        Estados estadosAtualizado = estadosRepository.save(estadosExistente);
        log.info("Estados atualizado com sucesso. ID: {}", estadosAtualizado.getId());

        return estadosMapper.toResponse(estadosAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo estados. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do estados é obrigatório");
        }

        Estados estados = estadosRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Estados não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(estados.getActive())) {
            throw new BadRequestException("Estados já está inativo");
        }

        estados.setActive(false);
        estadosRepository.save(estados);
        log.info("Estados excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(EstadosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do estados são obrigatórios");
        }
    }

    private void atualizarDadosEstados(Estados estados, EstadosRequest request) {
        Estados estadosAtualizado = estadosMapper.fromRequest(request);
        
        // Preserva campos de controle (Estados não tem tenant)
        java.util.UUID idOriginal = estados.getId();
        Boolean activeOriginal = estados.getActive();
        java.time.OffsetDateTime createdAtOriginal = estados.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(estadosAtualizado, estados);
        
        // Restaura campos de controle
        estados.setId(idOriginal);
        estados.setActive(activeOriginal);
        estados.setCreatedAt(createdAtOriginal);
    }
}
