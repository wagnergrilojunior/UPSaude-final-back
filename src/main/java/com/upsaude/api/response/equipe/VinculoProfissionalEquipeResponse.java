package com.upsaude.api.response.equipe;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinculoProfissionalEquipeResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeResponse profissional;
    private EquipeSaudeResponse equipe;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private TipoVinculoProfissionalEnum tipoVinculo;
    private String funcaoEquipe;
    private Integer cargaHorariaSemanal;
    private StatusAtivoEnum status;
    private String observacoes;
}
