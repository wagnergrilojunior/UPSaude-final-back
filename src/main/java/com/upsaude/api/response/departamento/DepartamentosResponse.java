package com.upsaude.api.response.departamento;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private String nome;
    private String descricao;
}
