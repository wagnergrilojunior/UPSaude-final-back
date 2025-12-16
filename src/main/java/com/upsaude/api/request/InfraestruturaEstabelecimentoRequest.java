package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de infraestrutura estabelecimento")
public class InfraestruturaEstabelecimentoRequest {
    private UUID estabelecimento;
    private String tipo;
    private String codigo;
    private String codigoCnes;
    private Integer quantidade;
    private Integer capacidade;
    private String descricao;
}
