package com.upsaude.service.impl;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import com.upsaude.entity.Exames;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.ExamesMapper;
import com.upsaude.repository.ExamesRepository;
import com.upsaude.service.ExamesService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * Implementação do serviço de gerenciamento de Exames.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ExamesServiceImpl implements ExamesService {

    private final ExamesRepository examesRepository;
    private final ExamesMapper examesMapper;

    @Override
    @Transactional
    public ExamesResponse criar(ExamesRequest request) {
        log.debug("Criando novo exames");

        validarDadosBasicos(request);

        Exames exames = examesMapper.fromRequest(request);
        exames.setActive(true);

        Exames examesSalvo = examesRepository.save(exames);
        log.info("Exames criado com sucesso. ID: {}", examesSalvo.getId());

        return examesMapper.toResponse(examesSalvo);
    }

    @Override
    @Transactional
    public ExamesResponse obterPorId(UUID id) {
        log.debug("Buscando exames por ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exames é obrigatório");
        }

        Exames exames = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exames não encontrado com ID: " + id));

        return examesMapper.toResponse(exames);
    }

    @Override
    public Page<ExamesResponse> listar(Pageable pageable) {
        log.debug("Listando Exames paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<Exames> exames = examesRepository.findAll(pageable);
        return exames.map(examesMapper::toResponse);
    }

    @Override
    @Transactional
    public ExamesResponse atualizar(UUID id, ExamesRequest request) {
        log.debug("Atualizando exames. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exames é obrigatório");
        }

        validarDadosBasicos(request);

        Exames examesExistente = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exames não encontrado com ID: " + id));

        atualizarDadosExames(examesExistente, request);

        Exames examesAtualizado = examesRepository.save(examesExistente);
        log.info("Exames atualizado com sucesso. ID: {}", examesAtualizado.getId());

        return examesMapper.toResponse(examesAtualizado);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo exames. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do exames é obrigatório");
        }

        Exames exames = examesRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Exames não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(exames.getActive())) {
            throw new BadRequestException("Exames já está inativo");
        }

        exames.setActive(false);
        examesRepository.save(exames);
        log.info("Exames excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(ExamesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do exames são obrigatórios");
        }
    }

        private void atualizarDadosExames(Exames exames, ExamesRequest request) {
        Exames examesAtualizado = examesMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = exames.getId();
        com.upsaude.entity.Tenant tenantOriginal = exames.getTenant();
        Boolean activeOriginal = exames.getActive();
        java.time.OffsetDateTime createdAtOriginal = exames.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(examesAtualizado, exames);
        
        // Restaura campos de controle
        exames.setId(idOriginal);
        exames.setTenant(tenantOriginal);
        exames.setActive(activeOriginal);
        exames.setCreatedAt(createdAtOriginal);
    }
}
