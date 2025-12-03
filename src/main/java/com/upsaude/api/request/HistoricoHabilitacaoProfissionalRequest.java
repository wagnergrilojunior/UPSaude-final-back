package com.upsaude.api.request;

import com.upsaude.enums.StatusAtivoEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoHabilitacaoProfissionalRequest {
    private UUID profissional;
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
