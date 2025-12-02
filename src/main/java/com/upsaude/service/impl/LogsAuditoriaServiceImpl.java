package com.upsaude.service.impl;

import com.upsaude.api.request.LogsAuditoriaRequest;
import com.upsaude.api.response.LogsAuditoriaResponse;
import com.upsaude.entity.LogsAuditoria;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.LogsAuditoriaMapper;
import com.upsaude.repository.LogsAuditoriaRepository;
import com.upsaude.service.LogsAuditoriaService;
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
 * Implementação do serviço de gerenciamento de LogsAuditoria.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class LogsAuditoriaServiceImpl implements LogsAuditoriaService {

    private final LogsAuditoriaRepository logsAuditoriaRepository;
    private final LogsAuditoriaMapper logsAuditoriaMapper;

    @Override
    @Transactional
    @CacheEvict(value = "logsauditoria", allEntries = true)
    public LogsAuditoriaResponse criar(LogsAuditoriaRequest request) {
        log.debug("Criando novo logsauditoria");

        validarDadosBasicos(request);

        LogsAuditoria logsAuditoria = logsAuditoriaMapper.fromRequest(request);
        logsAuditoria.setActive(true);

        LogsAuditoria logsAuditoriaSalvo = logsAuditoriaRepository.save(logsAuditoria);
        log.info("LogsAuditoria criado com sucesso. ID: {}", logsAuditoriaSalvo.getId());

        return logsAuditoriaMapper.toResponse(logsAuditoriaSalvo);
    }

    @Override
    @Transactional
    @Cacheable(value = "logsauditoria", key = "#id")
    public LogsAuditoriaResponse obterPorId(UUID id) {
        log.debug("Buscando logsauditoria por ID: {} (cache miss)", id);
        if (id == null) {
            throw new BadRequestException("ID do logsauditoria é obrigatório");
        }

        LogsAuditoria logsAuditoria = logsAuditoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LogsAuditoria não encontrado com ID: " + id));

        return logsAuditoriaMapper.toResponse(logsAuditoria);
    }

    @Override
    public Page<LogsAuditoriaResponse> listar(Pageable pageable) {
        log.debug("Listando LogsAuditorias paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<LogsAuditoria> logsAuditorias = logsAuditoriaRepository.findAll(pageable);
        return logsAuditorias.map(logsAuditoriaMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "logsauditoria", key = "#id")
    public LogsAuditoriaResponse atualizar(UUID id, LogsAuditoriaRequest request) {
        log.debug("Atualizando logsauditoria. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do logsauditoria é obrigatório");
        }

        validarDadosBasicos(request);

        LogsAuditoria logsAuditoriaExistente = logsAuditoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LogsAuditoria não encontrado com ID: " + id));

        atualizarDadosLogsAuditoria(logsAuditoriaExistente, request);

        LogsAuditoria logsAuditoriaAtualizado = logsAuditoriaRepository.save(logsAuditoriaExistente);
        log.info("LogsAuditoria atualizado com sucesso. ID: {}", logsAuditoriaAtualizado.getId());

        return logsAuditoriaMapper.toResponse(logsAuditoriaAtualizado);
    }

    @Override
    @Transactional
    @CacheEvict(value = "logsauditoria", key = "#id")
    public void excluir(UUID id) {
        log.debug("Excluindo logsauditoria. ID: {}", id);

        if (id == null) {
            throw new BadRequestException("ID do logsauditoria é obrigatório");
        }

        LogsAuditoria logsAuditoria = logsAuditoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LogsAuditoria não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(logsAuditoria.getActive())) {
            throw new BadRequestException("LogsAuditoria já está inativo");
        }

        logsAuditoria.setActive(false);
        logsAuditoriaRepository.save(logsAuditoria);
        log.info("LogsAuditoria excluído (desativado) com sucesso. ID: {}", id);
    }

    private void validarDadosBasicos(LogsAuditoriaRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do logsauditoria são obrigatórios");
        }
    }

        private void atualizarDadosLogsAuditoria(LogsAuditoria logsAuditoria, LogsAuditoriaRequest request) {
        LogsAuditoria logsAuditoriaAtualizado = logsAuditoriaMapper.fromRequest(request);
        
        // Preserva campos de controle
        java.util.UUID idOriginal = logsAuditoria.getId();
        com.upsaude.entity.Tenant tenantOriginal = logsAuditoria.getTenant();
        Boolean activeOriginal = logsAuditoria.getActive();
        java.time.OffsetDateTime createdAtOriginal = logsAuditoria.getCreatedAt();
        
        // Copia todas as propriedades do objeto atualizado
        BeanUtils.copyProperties(logsAuditoriaAtualizado, logsAuditoria);
        
        // Restaura campos de controle
        logsAuditoria.setId(idOriginal);
        logsAuditoria.setTenant(tenantOriginal);
        logsAuditoria.setActive(activeOriginal);
        logsAuditoria.setCreatedAt(createdAtOriginal);
    }
}
