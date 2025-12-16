package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de conselhos profissionais")
public class ConselhosProfissionaisRequest {
    private String sigla;
    private String nome;
    private String descricao;
}
