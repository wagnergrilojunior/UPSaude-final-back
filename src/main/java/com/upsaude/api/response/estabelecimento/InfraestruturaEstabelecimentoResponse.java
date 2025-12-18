package com.upsaude.api.response.estabelecimento;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InfraestruturaEstabelecimentoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private String tipo;
    private String codigo;
    private String codigoCnes;
    private Integer quantidade;
    private Integer capacidade;
    private String descricao;
}
