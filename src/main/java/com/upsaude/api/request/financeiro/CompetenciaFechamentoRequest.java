package com.upsaude.api.request.financeiro;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Request para fechamento de competência financeira.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompetenciaFechamentoRequest {
    
    /**
     * Motivo do fechamento da competência.
     */
    private String motivo;
}
