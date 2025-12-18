package com.upsaude.dto.profissional;

import com.upsaude.enums.StatusAtivoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoHabilitacaoProfissionalDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissional;
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
