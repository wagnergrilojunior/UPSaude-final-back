package com.upsaude.dto.estabelecimento.departamento;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentosDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private String nome;
    private String descricao;
}
