package com.upsaude.api.request.sistema.auditoria;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de logs auditoria")
public class LogsAuditoriaRequest {
    private String acao;
    private String entidade;
    private UUID entidadeId;
    private String detalhes;
    private UUID realizadoPor;
}
