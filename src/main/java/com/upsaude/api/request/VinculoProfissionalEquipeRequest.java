package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoVinculoProfissionalEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de vínculo profissional equipe")
public class VinculoProfissionalEquipeRequest {
    @NotNull(message = "Profissional é obrigatório")
    private UUID profissional;

    @NotNull(message = "Equipe é obrigatória")
    private UUID equipe;

    @NotNull(message = "Data de início do vínculo é obrigatória")
    private OffsetDateTime dataInicio;

    private OffsetDateTime dataFim;

    @JsonDeserialize(using = TipoVinculoProfissionalEnumDeserializer.class)
    @NotNull(message = "Tipo de vínculo é obrigatório")
    private TipoVinculoProfissionalEnum tipoVinculo;

    private String funcaoEquipe;
    private Integer cargaHorariaSemanal;

    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    private String observacoes;
}
