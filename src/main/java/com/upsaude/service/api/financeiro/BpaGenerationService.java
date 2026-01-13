package com.upsaude.service.api.financeiro;

import java.util.List;
import java.util.UUID;

/**
 * Serviço para geração de arquivo BPA em formato TXT de largura fixa.
 */
public interface BpaGenerationService {
    
    /**
     * Gera o conteúdo do arquivo BPA para uma competência e tenant específicos.
     * 
     * @param competenciaId ID da competência financeira
     * @param tenantId ID do tenant (município)
     * @return Conteúdo do arquivo BPA como String (TXT de largura fixa)
     */
    String gerarBpa(UUID competenciaId, UUID tenantId);
    
    /**
     * Consolida dados de todas as fontes para a competência/tenant.
     * 
     * @param competenciaId ID da competência financeira
     * @param tenantId ID do tenant (município)
     * @return Lista de dados consolidados para geração do BPA
     */
    List<com.upsaude.api.response.financeiro.BpaConsolidadoDto> consolidarDadosCompetencia(UUID competenciaId, UUID tenantId);
}
