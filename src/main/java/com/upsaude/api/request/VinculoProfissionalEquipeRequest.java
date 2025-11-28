package com.upsaude.api.request;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class VinculoProfissionalEquipeRequest {
    @NotNull(message = "Profissional é obrigatório")
    private UUID profissionalId;

    @NotNull(message = "Equipe é obrigatória")
    private UUID equipeId;

    @NotNull(message = "Data de início do vínculo é obrigatória")
    private OffsetDateTime dataInicio;

    private OffsetDateTime dataFim;

    @NotNull(message = "Tipo de vínculo é obrigatório")
    private TipoVinculoProfissionalEnum tipoVinculo;

    @Size(max = 255, message = "Função na equipe deve ter no máximo 255 caracteres")
    private String funcaoEquipe;

    private Integer cargaHorariaSemanal;

    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    private String observacoes;
}

