package com.upsaude.service.impl.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFinanceiraRequest;
import com.upsaude.api.response.financeiro.CompetenciaFinanceiraResponse;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.exception.InternalServerErrorException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.service.api.financeiro.CompetenciaFinanceiraService;
import com.upsaude.service.api.support.financeiro.competencia.CompetenciaFinanceiraCreator;
import com.upsaude.service.api.support.financeiro.competencia.CompetenciaFinanceiraDomainService;
import com.upsaude.service.api.support.financeiro.competencia.CompetenciaFinanceiraResponseBuilder;
import com.upsaude.service.api.support.financeiro.competencia.CompetenciaFinanceiraUpdater;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompetenciaFinanceiraServiceImpl implements CompetenciaFinanceiraService {

    private final CompetenciaFinanceiraRepository repository;
    private final CompetenciaFinanceiraCreator creator;
    private final CompetenciaFinanceiraUpdater updater;
    private final CompetenciaFinanceiraResponseBuilder responseBuilder;
    private final CompetenciaFinanceiraDomainService domainService;

    @Override
    @Transactional
    public CompetenciaFinanceiraResponse criar(CompetenciaFinanceiraRequest request) {
        log.debug("Criando competência financeira. Request: {}", request);
        CompetenciaFinanceira saved = creator.criar(request);
        return responseBuilder.build(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public CompetenciaFinanceiraResponse obterPorId(UUID id) {
        log.debug("Buscando competência financeira por ID: {}", id);
        CompetenciaFinanceira entity = repository.findById(id)
                .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada com ID: " + id));
        return responseBuilder.build(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<CompetenciaFinanceiraResponse> listar(Pageable pageable) {
        log.debug("Listando competências financeiras. Pageable: {}", pageable);
        return repository.findAll(pageable).map(responseBuilder::build);
    }

    @Override
    @Transactional
    public CompetenciaFinanceiraResponse atualizar(UUID id, CompetenciaFinanceiraRequest request) {
        log.debug("Atualizando competência financeira. ID: {}, Request: {}", id, request);
        CompetenciaFinanceira updated = updater.atualizar(id, request);
        return responseBuilder.build(updated);
    }

    @Override
    @Transactional
    public void excluir(UUID id) {
        log.debug("Excluindo competência financeira. ID: {}", id);
        try {
            CompetenciaFinanceira entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada com ID: " + id));
            domainService.validarPodeDeletar(entity);
            repository.delete(entity);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao excluir CompetenciaFinanceira. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao excluir competência financeira", e);
        }
    }

    @Override
    @Transactional
    public void inativar(UUID id) {
        log.debug("Inativando competência financeira. ID: {}", id);
        try {
            CompetenciaFinanceira entity = repository.findById(id)
                    .orElseThrow(() -> new NotFoundException("Competência financeira não encontrada com ID: " + id));
            domainService.validarPodeInativar(entity);
            entity.setActive(false);
            repository.save(entity);
        } catch (DataAccessException e) {
            log.error("Erro de acesso a dados ao inativar CompetenciaFinanceira. Exception: {}", e.getClass().getSimpleName(), e);
            throw new InternalServerErrorException("Erro ao inativar competência financeira", e);
        }
    }
}

