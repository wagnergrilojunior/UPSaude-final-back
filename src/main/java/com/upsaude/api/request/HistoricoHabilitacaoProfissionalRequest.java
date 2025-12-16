package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de historico habilitação profissional")
public class HistoricoHabilitacaoProfissionalRequest {
    private UUID profissional;
    private String tipoEvento;
    private OffsetDateTime dataEvento;
    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum statusAnterior;

    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum statusNovo;
    private OffsetDateTime dataValidadeAnterior;
    private OffsetDateTime dataValidadeNova;
    private String numeroProcesso;
    private String observacoes;
    private String documentoReferencia;
    private UUID usuarioResponsavel;
}
