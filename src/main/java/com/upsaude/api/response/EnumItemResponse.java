package com.upsaude.api.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Response que representa um item de enum.
 *
 * @author UPSaúde
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Representa um valor individual de um enum")
public class EnumItemResponse {
    
    @Schema(description = "Nome do valor do enum (ex: MASCULINO, ATIVO)", example = "MASCULINO")
    private String nome;
    
    @Schema(description = "Código numérico do enum (quando disponível)", example = "1")
    private Integer codigo;
    
    @Schema(description = "Descrição legível do valor do enum", example = "Masculino")
    private String descricao;
}

