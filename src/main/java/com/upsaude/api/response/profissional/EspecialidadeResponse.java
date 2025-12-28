package com.upsaude.api.response.profissional;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com informações de uma especialidade (CBO) do médico")
public class EspecialidadeResponse {
    
    @Schema(description = "Identificador único da ocupação CBO", example = "ee0e8400-e29b-41d4-a716-446655440009")
    private UUID id;
    
    @Schema(description = "Código CBO (Classificação Brasileira de Ocupações)", example = "225110")
    private String codigoOficial;
    
    @Schema(description = "Nome da especialidade/profissão", example = "MÉDICO CLINICO GERAL")
    private String nome;
}

