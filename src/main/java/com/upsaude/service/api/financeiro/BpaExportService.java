package com.upsaude.service.api.financeiro;

import org.springframework.core.io.Resource;

import java.util.UUID;

/**
 * Serviço para exportação/download de arquivo BPA gerado.
 */
public interface BpaExportService {
    
    /**
     * Exporta o arquivo BPA gerado para uma competência.
     * 
     * @param competenciaId ID da competência financeira
     * @param tenantId ID do tenant (município)
     * @return Resource com o conteúdo do arquivo BPA para download
     * @throws com.upsaude.exception.NotFoundException se o BPA não foi gerado ainda
     */
    Resource exportarBpa(UUID competenciaId, UUID tenantId);
    
    /**
     * Verifica se o BPA foi gerado para uma competência.
     * 
     * @param competenciaId ID da competência financeira
     * @param tenantId ID do tenant (município)
     * @return true se o BPA foi gerado, false caso contrário
     */
    boolean bpaFoiGerado(UUID competenciaId, UUID tenantId);
}
