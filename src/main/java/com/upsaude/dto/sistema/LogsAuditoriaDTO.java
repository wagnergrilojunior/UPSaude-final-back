package com.upsaude.dto.sistema;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsAuditoriaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String acao;
    private String entidade;
    private UUID entidadeId;
    private String detalhes;
    private UUID realizadoPor;
}
