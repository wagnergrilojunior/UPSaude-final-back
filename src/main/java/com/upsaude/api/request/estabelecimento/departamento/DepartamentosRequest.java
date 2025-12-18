package com.upsaude.api.request.estabelecimento.departamento;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de departamentos")
public class DepartamentosRequest {
    private UUID estabelecimento;
    private String nome;
    private String descricao;
}
