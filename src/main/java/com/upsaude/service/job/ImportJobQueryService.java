package com.upsaude.service.job;

import com.upsaude.api.response.sistema.importacao.ImportJobErrorResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobStatusResponse;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface ImportJobQueryService {

    ImportJobResponse obterPorId(UUID jobId, UUID tenantId);

    Page<ImportJobResponse> listarPorTenant(UUID tenantId, Pageable pageable);

    Page<ImportJobResponse> listarPorTenantEStatus(UUID tenantId, ImportJobStatusEnum status, Pageable pageable);

    Page<ImportJobResponse> listarPorTenantETipo(UUID tenantId, ImportJobTipoEnum tipo, Pageable pageable);

    ImportJobStatusResponse obterStatus(UUID jobId, UUID tenantId);

    Page<ImportJobErrorResponse> listarErrosPorJob(UUID jobId, UUID tenantId, Pageable pageable);

    long contarErrosPorJob(UUID jobId, UUID tenantId);

    ImportJobResponse reprocessarJob(UUID jobId, UUID tenantId);

    List<ImportJobResponse> reprocessarJobsPorTipoECompetencia(
        ImportJobTipoEnum tipo,
        String competenciaAno,
        String competenciaMes,
        UUID tenantId
    );
}

