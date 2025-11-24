package com.upsaude.service.impl;

import com.upsaude.api.request.DoencasCronicasRequest;
import com.upsaude.api.response.DoencasCronicasResponse;
import com.upsaude.entity.DoencasCronicas;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.DoencasCronicasMapper;
import com.upsaude.repository.DoencasCronicasRepository;
import com.upsaude.service.DoencasCronicasService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de DoencasCronicas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DoencasCronicasServiceImpl implements DoencasCronicasService {

    private final DoencasCronicasRepository doencasCronicasRepository;
    private final DoencasCronicasMapper doencasCronicasMapper;

    @Override
    @Transactional
    public DoencasCronicasResponse criar(DoencasCronicasRequest request) {
        log.debug("Criando novo doencascronicas");

        validarDadosBasicos(request);

        DoencasCronicas doencasCronicas = doencasCronicasMapper.fromRequest(request);
        doencasCronicas.setActive(true);

        DoencasCronicas doencasCronicasSalvo = doencasCronicasRepository.save(doencasCronicas);
        log.info("DoencasCronicas criado com sucesso. ID: {}", doencasCronicasSalvo.getId());

        return doencasCronicasMapper.toResponse(doencasCronicasSalvo);
    }

    @Override
    @Transactional
    public DoencasCronicasResponse obterPorId(UUID id) {
        log.debug("Buscando doencascronicas por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do doencascronicas é obrigatório");
        }

        DoencasCronicas doencasCronicas = doencasCronicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DoencasCronicas não encontrado com ID: " + id));

        return doencasCronicasMapper.toResponse(doencasCronicas);
    }

    @Override
    public Page<DoencasCronicasResponse> listar(Pageable pageable) {
        log.debug("Listando DoencasCronicas paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<DoencasCronicas> doencasCronicas = doencasCronicasRepository.findAll(pageable);
        return doencasCronicas.map(doencasCronicasMapper::toResponse);
    }

    @Override
    @Transactional
    public DoencasCronicasResponse atualizar(UUID id, DoencasCronicasRequest request) {
        log.debug("Atualizando doencascronicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do doencascronicas é obrigatório");
        }

        validarDadosBasicos(request);

        DoencasCronicas doencasCronicasExistente = doencasCronicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DoencasCronicas não encontrado com ID: " + id));

        atualizarDadosDoencasCronicas(doencasCronicasExistente, request);

        DoencasCronicas doencasCronicasAtualizado = doencasCronicasRepository.save(doencasCronicasExistente);
        log.info("DoencasCronicas atualizado com sucesso. ID: {}", doencasCronicasAtualizado.getId());

        return doencasCronicasMapper.toResponse(doencasCronicasAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo doencascronicas. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do doencascronicas é obrigatório");
        }

        DoencasCronicas doencasCronicas = doencasCronicasRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("DoencasCronicas não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(doencasCronicas.getActive())) {
            throw new BadRequestException("DoencasCronicas já está inativo");
        }

        doencasCronicas.setActive(false);
        doencasCronicasRepository.save(doencasCronicas);
        log.info("DoencasCronicas excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(DoencasCronicasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do doencascronicas são obrigatórios");
        }
    }

        private void atualizarDadosDoencasCronicas(DoencasCronicas doencasCronicas, DoencasCronicasRequest request) {
        DoencasCronicas doencasCronicasAtualizado = doencasCronicasMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = doencasCronicas.getId();
        com.upsaude.entity.Tenant tenantOriginal = doencasCronicas.getTenant();
        Boolean activeOriginal = doencasCronicas.getActive();
        java.time.OffsetDateTime createdAtOriginal = doencasCronicas.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(doencasCronicasAtualizado, doencasCronicas);
        
        // Restaura campos de controle
        doencasCronicas.setId(idOriginal);
        doencasCronicas.setTenant(tenantOriginal);
        doencasCronicas.setActive(activeOriginal);
        doencasCronicas.setCreatedAt(createdAtOriginal);
    }
}
