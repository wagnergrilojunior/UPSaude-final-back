package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConselhosProfissionaisRequest {
    private String sigla;
    private String nome;
    private String descricao;
}
