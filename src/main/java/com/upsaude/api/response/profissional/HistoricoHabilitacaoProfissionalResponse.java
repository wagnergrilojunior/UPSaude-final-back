package com.upsaude.api.response.profissional;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;

import com.upsaude.enums.StatusAtivoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoHabilitacaoProfissionalResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeResponse profissional;
    private String tipoEvento;
    private OffsetDateTime dataEvento;
    private StatusAtivoEnum statusAnterior;
    private StatusAtivoEnum statusNovo;
    private OffsetDateTime dataValidadeAnterior;
    private OffsetDateTime dataValidadeNova;
    private String numeroProcesso;
    private String observacoes;
    private String documentoReferencia;
    private UUID usuarioResponsavel;
}
