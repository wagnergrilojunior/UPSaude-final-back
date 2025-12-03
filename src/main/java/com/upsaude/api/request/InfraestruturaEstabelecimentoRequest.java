package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaEstabelecimentoRequest {
    private UUID estabelecimento;
    private String tipo;
    private String codigo;
    private String codigoCnes;
    private Integer quantidade;
    private Integer capacidade;
    private String descricao;
}
