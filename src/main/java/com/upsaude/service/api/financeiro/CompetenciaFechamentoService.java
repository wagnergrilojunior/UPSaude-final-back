package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.CompetenciaFechamentoRequest;
import com.upsaude.api.response.financeiro.CompetenciaFechamentoResponse;

import java.util.UUID;

/**
 * Serviço para fechamento completo de competência financeira.
 */
public interface CompetenciaFechamentoService {
    
    /**
     * Fecha uma competência financeira, gerando BPA e calculando hashes de integridade.
     * 
     * @param competenciaId ID da competência financeira
     * @param tenantId ID do tenant (município)
     * @param usuarioId ID do usuário que está fechando a competência
     * @param request Request com motivo do fechamento
     * @return Response com informações do fechamento
     */
    CompetenciaFechamentoResponse fecharCompetencia(
            UUID competenciaId, 
            UUID tenantId, 
            UUID usuarioId, 
            CompetenciaFechamentoRequest request);
}
