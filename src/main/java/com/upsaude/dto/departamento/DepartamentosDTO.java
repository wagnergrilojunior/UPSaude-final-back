package com.upsaude.dto.departamento;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

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
