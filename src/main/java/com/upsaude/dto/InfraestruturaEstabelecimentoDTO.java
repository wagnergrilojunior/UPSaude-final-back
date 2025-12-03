package com.upsaude.dto;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaEstabelecimentoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private String tipo;
    private String codigo;
    private String codigoCnes;
    private Integer quantidade;
    private Integer capacidade;
    private String descricao;
}
