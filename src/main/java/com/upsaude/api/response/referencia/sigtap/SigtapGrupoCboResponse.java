package com.upsaude.api.response.referencia.sigtap;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Resposta com informações de um grupo CBO.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com informações de um grupo CBO")
public class SigtapGrupoCboResponse {
    
    @Schema(description = "Código do grupo CBO", example = "MEDICOS")
    private String codigo;
    
    @Schema(description = "Nome descritivo do grupo", example = "Especialidades Médicas")
    private String nome;
    
    @Schema(description = "Descrição detalhada do grupo", example = "Ocupações relacionadas a médicos e especialidades médicas")
    private String descricao;
    
    @Schema(description = "Total de CBOs neste grupo", example = "150")
    private Long totalCbo;
    
    @Schema(description = "Indica se é um grupo da área de saúde", example = "true")
    private Boolean isSaude;
}

