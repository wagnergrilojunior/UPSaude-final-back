package com.upsaude.api.request;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
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
    private TipoVinculoProfissionalEnum tipoVinculo;
    private String funcaoEquipe;
    private Integer cargaHorariaSemanal;
    private StatusAtivoEnum status;
    private String observacoes;
}
