package com.upsaude.service.impl.job;

import com.upsaude.api.response.sistema.importacao.ImportJobErrorResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobStatusResponse;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.mapper.sistema.importacao.ImportJobErrorMapper;
import com.upsaude.mapper.sistema.importacao.ImportJobMapper;
import com.upsaude.repository.sistema.importacao.ImportJobErrorRepository;
import com.upsaude.repository.sistema.importacao.ImportJobApiRepository;
import com.upsaude.service.job.ImportJobQueryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.time.OffsetDateTime;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementação do service de consulta de jobs de importação.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class ImportJobQueryServiceImpl implements ImportJobQueryService {

    // USO EXCLUSIVO API - Queries são operações HTTP, usa pool API
    private final ImportJobApiRepository importJobApiRepository;
    private final ImportJobErrorRepository importJobErrorRepository;
    private final ImportJobMapper importJobMapper;
    private final ImportJobErrorMapper importJobErrorMapper;

    @Override
    @Transactional(readOnly = true)
    public ImportJobResponse obterPorId(UUID jobId, UUID tenantId) {
        log.debug("Buscando job de importação por ID: {} para tenant: {}", jobId, tenantId);

        var job = importJobApiRepository.findByIdAndTenant_Id(jobId, tenantId)
                .orElseThrow(() -> new NotFoundException("Job de importação não encontrado com ID: " + jobId));

        return importJobMapper.toResponse(job);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImportJobResponse> listarPorTenant(UUID tenantId, Pageable pageable) {
        log.debug("Listando jobs de importação para tenant: {}, página: {}, tamanho: {}",
                tenantId, pageable.getPageNumber(), pageable.getPageSize());

        var jobsPage = importJobApiRepository.findByTenant_IdOrderByCreatedAtDesc(tenantId, pageable);

        List<ImportJobResponse> responses = jobsPage.getContent().stream()
                .map(importJobMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, jobsPage.getPageable(), jobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImportJobResponse> listarPorTenantEStatus(UUID tenantId, ImportJobStatusEnum status, Pageable pageable) {
        log.debug("Listando jobs de importação para tenant: {}, status: {}, página: {}, tamanho: {}",
                tenantId, status, pageable.getPageNumber(), pageable.getPageSize());

        var jobsPage = importJobApiRepository.findByTenant_IdAndStatus(tenantId, status, pageable);

        List<ImportJobResponse> responses = jobsPage.getContent().stream()
                .map(importJobMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, jobsPage.getPageable(), jobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImportJobResponse> listarPorTenantETipo(UUID tenantId, ImportJobTipoEnum tipo, Pageable pageable) {
        log.debug("Listando jobs de importação para tenant: {}, tipo: {}, página: {}, tamanho: {}",
                tenantId, tipo, pageable.getPageNumber(), pageable.getPageSize());

        var jobsPage = importJobApiRepository.findByTenant_IdAndTipo(tenantId, tipo, pageable);

        List<ImportJobResponse> responses = jobsPage.getContent().stream()
                .map(importJobMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, jobsPage.getPageable(), jobsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public ImportJobStatusResponse obterStatus(UUID jobId, UUID tenantId) {
        log.debug("Buscando status do job de importação: {} para tenant: {}", jobId, tenantId);

        var job = importJobApiRepository.findByIdAndTenant_Id(jobId, tenantId)
                .orElseThrow(() -> new NotFoundException("Job de importação não encontrado com ID: " + jobId));

        long totalErros = importJobErrorRepository.countByJob_Id(jobId);

        return importJobMapper.toStatusResponse(job, totalErros);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ImportJobErrorResponse> listarErrosPorJob(UUID jobId, UUID tenantId, Pageable pageable) {
        log.debug("Listando erros do job: {} para tenant: {}, página: {}, tamanho: {}",
                jobId, tenantId, pageable.getPageNumber(), pageable.getPageSize());

        // Verifica se o job existe e pertence ao tenant
        importJobApiRepository.findByIdAndTenant_Id(jobId, tenantId)
                .orElseThrow(() -> new NotFoundException("Job de importação não encontrado com ID: " + jobId));

        var errorsPage = importJobErrorRepository.findByJob_IdAndTenant_IdOrderByLinhaAsc(jobId, tenantId, pageable);

        List<ImportJobErrorResponse> responses = errorsPage.getContent().stream()
                .map(importJobErrorMapper::toResponse)
                .collect(Collectors.toList());

        return new PageImpl<>(responses, errorsPage.getPageable(), errorsPage.getTotalElements());
    }

    @Override
    @Transactional(readOnly = true)
    public long contarErrosPorJob(UUID jobId, UUID tenantId) {
        log.debug("Contando erros do job: {} para tenant: {}", jobId, tenantId);

        // Verifica se o job existe e pertence ao tenant
        importJobApiRepository.findByIdAndTenant_Id(jobId, tenantId)
                .orElseThrow(() -> new NotFoundException("Job de importação não encontrado com ID: " + jobId));

        return importJobErrorRepository.countByJob_Id(jobId);
    }

    @Override
    @Transactional
    public ImportJobResponse reprocessarJob(UUID jobId, UUID tenantId) {
        log.info("Reprocessando job {} para tenant {}", jobId, tenantId);

        var job = importJobApiRepository.findByIdAndTenant_Id(jobId, tenantId)
                .orElseThrow(() -> new NotFoundException("Job de importação não encontrado com ID: " + jobId));

        if (job.getStatus() != ImportJobStatusEnum.ERRO) {
            throw new BadRequestException("Apenas jobs com status ERRO podem ser reprocessados. Status atual: " + job.getStatus());
        }

        // Valida que o arquivo ainda existe no storage
        if (!StringUtils.hasText(job.getStorageBucket()) || !StringUtils.hasText(job.getStoragePath())) {
            throw new BadRequestException("Job não possui informações de storage válidas. Não é possível reprocessar.");
        }

        // Reseta o job para ENFILEIRADO
        job.setStatus(ImportJobStatusEnum.ENFILEIRADO);
        job.setAttempts(0); // Reseta tentativas
        job.setNextRunAt(OffsetDateTime.now()); // Processa imediatamente
        job.setLockedAt(null);
        job.setLockedBy(null);
        job.setHeartbeatAt(null);
        job.setErrorSummary(null);
        job.setErrorSampleJson(null);
        job.setStartedAt(null);
        job.setFinishedAt(null);
        job.setDurationMs(null);
        job.setCheckpointLinha(0L);
        job.setCheckpointByteOffset(null);
        job.setLinhasLidas(0L);
        job.setLinhasProcessadas(0L);
        job.setLinhasInseridas(0L);
        job.setLinhasErro(0L);
        job.setPercentualEstimado(null);

        var savedJob = importJobApiRepository.save(job);
        log.info("Job {} reprocessado com sucesso. Status alterado de ERRO para ENFILEIRADO", jobId);

        return importJobMapper.toResponse(savedJob);
    }
}

