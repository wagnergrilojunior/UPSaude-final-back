package com.upsaude.dto;

import lombok.*;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinculosPapeisDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID departamentoId;
    private Long papelId;
    private Boolean active;
}

