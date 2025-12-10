package com.upsaude.service.impl;

import com.upsaude.api.request.LogsAuditoriaRequest;
import com.upsaude.api.response.LogsAuditoriaResponse;
import com.upsaude.entity.LogsAuditoria;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.LogsAuditoriaMapper;
import com.upsaude.repository.LogsAuditoriaRepository;
import com.upsaude.service.LogsAuditoriaService;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
        log.debug("Criando novo LogsAuditoria");
        
        if (request == null) {
            log.warn("Request nulo recebido para criação de LogsAuditoria");
            throw new BadRequestException("Dados do log de auditoria são obrigatórios");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

        LogsAuditoria logsAuditoria = logsAuditoriaMapper.fromRequest(request);
        logsAuditoria.setActive(true);

        LogsAuditoria logsAuditoriaSalvo = logsAuditoriaRepository.save(logsAuditoria);
        log.info("LogsAuditoria criado com sucesso. ID: {}", logsAuditoriaSalvo.getId());

        return logsAuditoriaMapper.toResponse(logsAuditoriaSalvo);
    }

    @Override
    @Transactional(readOnly = true)
    @Cacheable(value = "logsauditoria", key = "#id")
    public LogsAuditoriaResponse obterPorId(UUID id) {
        log.debug("Buscando LogsAuditoria por ID: {} (cache miss)", id);
        
        if (id == null) {
            log.warn("ID nulo recebido para busca de LogsAuditoria");
            throw new BadRequestException("ID do log de auditoria é obrigatório");
        }

        LogsAuditoria logsAuditoria = logsAuditoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LogsAuditoria não encontrado com ID: " + id));

        log.debug("LogsAuditoria encontrado. ID: {}", id);
        return logsAuditoriaMapper.toResponse(logsAuditoria);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<LogsAuditoriaResponse> listar(Pageable pageable) {
        log.debug("Listando LogsAuditorias paginados. Página: {}, Tamanho: {}",
                pageable.getPageNumber(), pageable.getPageSize());

        Page<LogsAuditoria> logsAuditorias = logsAuditoriaRepository.findAll(pageable);
        log.debug("Listagem de LogsAuditorias concluída. Total de elementos: {}", logsAuditorias.getTotalElements());
        return logsAuditorias.map(logsAuditoriaMapper::toResponse);
    }

    @Override
    @Transactional
    @CacheEvict(value = "logsauditoria", key = "#id")
    public LogsAuditoriaResponse atualizar(UUID id, LogsAuditoriaRequest request) {
        log.debug("Atualizando LogsAuditoria. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para atualização de LogsAuditoria");
            throw new BadRequestException("ID do log de auditoria é obrigatório");
        }
        
        if (request == null) {
            log.warn("Request nulo recebido para atualização de LogsAuditoria. ID: {}", id);
            throw new BadRequestException("Dados do log de auditoria são obrigatórios");
        }

        // Validação de dados básicos é feita automaticamente pelo Bean Validation no Request

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
        log.debug("Excluindo LogsAuditoria. ID: {}", id);

        if (id == null) {
            log.warn("ID nulo recebido para exclusão de LogsAuditoria");
            throw new BadRequestException("ID do log de auditoria é obrigatório");
        }

        LogsAuditoria logsAuditoria = logsAuditoriaRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("LogsAuditoria não encontrado com ID: " + id));

        if (Boolean.FALSE.equals(logsAuditoria.getActive())) {
            log.warn("Tentativa de excluir LogsAuditoria já inativo. ID: {}", id);
            throw new BadRequestException("LogsAuditoria já está inativo");
        }

        logsAuditoria.setActive(false);
        logsAuditoriaRepository.save(logsAuditoria);
        log.info("LogsAuditoria excluído (desativado) com sucesso. ID: {}", id);
    }

    // Validações de dados básicos foram movidas para o Request usando Bean Validation
    // (@NotNull, @NotBlank, @Pattern, etc). Isso garante validação automática no Controller
    // e retorno de erro 400 padronizado via ApiExceptionHandler.

    private void atualizarDadosLogsAuditoria(LogsAuditoria logsAuditoria, LogsAuditoriaRequest request) {
        // Usar mapper para atualizar campos básicos
        logsAuditoriaMapper.updateFromRequest(request, logsAuditoria);
    }
}
