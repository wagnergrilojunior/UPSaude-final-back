package com.upsaude.service.job;

import com.upsaude.api.response.sistema.importacao.ImportJobErrorResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobResponse;
import com.upsaude.api.response.sistema.importacao.ImportJobStatusResponse;
import com.upsaude.enums.ImportJobStatusEnum;
import com.upsaude.enums.ImportJobTipoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Service para consultas de jobs de importação (somente leitura).
 */
public interface ImportJobQueryService {

    /**
     * Busca um job por ID, garantindo que pertence ao tenant informado.
     */
    ImportJobResponse obterPorId(UUID jobId, UUID tenantId);

    /**
     * Lista jobs de um tenant com paginação.
     */
    Page<ImportJobResponse> listarPorTenant(UUID tenantId, Pageable pageable);

    /**
     * Lista jobs de um tenant filtrando por status.
     */
    Page<ImportJobResponse> listarPorTenantEStatus(UUID tenantId, ImportJobStatusEnum status, Pageable pageable);

    /**
     * Lista jobs de um tenant filtrando por tipo.
     */
    Page<ImportJobResponse> listarPorTenantETipo(UUID tenantId, ImportJobTipoEnum tipo, Pageable pageable);

    /**
     * Obtém status simplificado de um job (usado para polling).
     */
    ImportJobStatusResponse obterStatus(UUID jobId, UUID tenantId);

    /**
     * Lista erros de um job com paginação.
     */
    Page<ImportJobErrorResponse> listarErrosPorJob(UUID jobId, UUID tenantId, Pageable pageable);

    /**
     * Conta total de erros de um job.
     */
    long contarErrosPorJob(UUID jobId, UUID tenantId);

    /**
     * Reprocessa um job que está com status ERRO, resetando para ENFILEIRADO.
     * Valida que o job pertence ao tenant e está em status ERRO.
     * 
     * @param jobId ID do job a ser reprocessado
     * @param tenantId ID do tenant (para validação de segurança)
     * @return Job atualizado com status ENFILEIRADO
     */
    ImportJobResponse reprocessarJob(UUID jobId, UUID tenantId);
}

