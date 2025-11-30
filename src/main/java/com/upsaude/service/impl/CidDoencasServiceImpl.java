package com.upsaude.service.impl;

import com.upsaude.api.request.CidDoencasRequest;
import com.upsaude.api.response.CidDoencasResponse;
import com.upsaude.entity.CidDoencas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.CidDoencasMapper;
import com.upsaude.repository.CidDoencasRepository;
import com.upsaude.service.CidDoencasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de CidDoencas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CidDoencasServiceImpl implements CidDoencasService {

    private final CidDoencasRepository cidDoencasRepository;
    private final CidDoencasMapper cidDoencasMapper;

    @Override
    @Transactional
    public CidDoencasResponse criar(CidDoencasRequest request) {
        log.debug("Criando novo ciddoencas");

        validarDadosBasicos(request);

        CidDoencas cidDoencas = cidDoencasMapper.fromRequest(request);
        cidDoencas.setActive(true);

        CidDoencas cidDoencasSalvo = cidDoencasRepository.save(cidDoencas);
        log.info("CidDoencas criado com sucesso. ID: {}", cidDoencasSalvo.getId());

        return cidDoencasMapper.toResponse(cidDoencasSalvo);
    }

    @Override
    @Transactional
    public CidDoencasResponse obterPorId(UUID id) {
        log.debug("Buscando ciddoencas por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do ciddoencas é obrigatório");
        }

        CidDoencas cidDoencas = cidDoencasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CidDoencas não encontrado com ID: " + id));

        return cidDoencasMapper.toResponse(cidDoencas);
    }

    @Override
    public Page<CidDoencasResponse> listar(Pageable pageable) {
        log.debug("Listando CidDoencas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<CidDoencas> cidDoencas = cidDoencasRepository.findAll(pageable);
        return cidDoencas.map(cidDoencasMapper::toResponse);
    }

    @Override
    @Transactional
    public CidDoencasResponse atualizar(UUID id, CidDoencasRequest request) {
        log.debug("Atualizando ciddoencas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do ciddoencas é obrigatório");
        }

        validarDadosBasicos(request);

        CidDoencas cidDoencasExistente = cidDoencasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CidDoencas não encontrado com ID: " + id));

        atualizarDadosCidDoencas(cidDoencasExistente, request);

        CidDoencas cidDoencasAtualizado = cidDoencasRepository.save(cidDoencasExistente);
        log.info("CidDoencas atualizado com sucesso. ID: {}", cidDoencasAtualizado.getId());

        return cidDoencasMapper.toResponse(cidDoencasAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo ciddoencas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do ciddoencas é obrigatório");
        }

        CidDoencas cidDoencas = cidDoencasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("CidDoencas não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(cidDoencas.getActive())) {
            throw new BadRequestException("CidDoencas já está inativo");
        }

        cidDoencas.setActive(false);
        cidDoencasRepository.save(cidDoencas);
        log.info("CidDoencas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(CidDoencasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do ciddoencas são obrigatórios");
        }
    }

    private void atualizarDadosCidDoencas(CidDoencas cidDoencas, CidDoencasRequest request) {
        CidDoencas cidDoencasAtualizado = cidDoencasMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = cidDoencas.getId();
        Boolean activeOriginal = cidDoencas.getActive();
        java.time.OffsetDateTime createdAtOriginal = cidDoencas.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(cidDoencasAtualizado, cidDoencas);
        
        // Restaura campos de controle
        cidDoencas.setId(idOriginal);
        cidDoencas.setActive(activeOriginal);
        cidDoencas.setCreatedAt(createdAtOriginal);
    }
}
