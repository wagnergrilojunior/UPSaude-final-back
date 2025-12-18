package com.upsaude.api.request.sistema;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de permissoes")
public class PermissoesRequest {
    private String nome;
    private String descricao;
    private String modulo;
}
