package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartamentosRequest {
    private UUID estabelecimento;
    private String nome;
    private String descricao;
}
