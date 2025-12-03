package com.upsaude.api.request;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LogsAuditoriaRequest {
    private String acao;
    private String entidade;
    private UUID entidadeId;
    private String detalhes;
    private UUID realizadoPor;
}
