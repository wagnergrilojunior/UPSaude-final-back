package com.upsaude.api.request;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import com.upsaude.util.converter.StatusAtivoEnumDeserializer;
import com.upsaude.util.converter.TipoVinculoProfissionalEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinculoProfissionalEquipeRequest {
    private UUID profissional;
    private UUID equipe;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    @JsonDeserialize(using = TipoVinculoProfissionalEnumDeserializer.class)
    private TipoVinculoProfissionalEnum tipoVinculo;
    private String funcaoEquipe;
    private Integer cargaHorariaSemanal;

    @JsonDeserialize(using = StatusAtivoEnumDeserializer.class)
    private StatusAtivoEnum status;
    private String observacoes;
}
