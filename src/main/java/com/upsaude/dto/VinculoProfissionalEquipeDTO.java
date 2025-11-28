package com.upsaude.dto;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinculoProfissionalEquipeDTO {
    private UUID id;
    private UUID profissionalId;
    private UUID equipeId;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private TipoVinculoProfissionalEnum tipoVinculo;
    private String funcaoEquipe;
    private Integer cargaHorariaSemanal;
    private StatusAtivoEnum status;
    private String observacoes;
    private Boolean active;
}

