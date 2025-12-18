package com.upsaude.dto.medicacao;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacoesContinuasDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String dosagem;
    private String fabricante;
}
