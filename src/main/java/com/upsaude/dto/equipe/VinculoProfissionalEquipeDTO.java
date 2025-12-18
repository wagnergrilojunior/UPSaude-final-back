package com.upsaude.dto.equipe;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoVinculoProfissionalEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VinculoProfissionalEquipeDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissional;
    private EquipeSaudeDTO equipe;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private TipoVinculoProfissionalEnum tipoVinculo;
    private String funcaoEquipe;
    private Integer cargaHorariaSemanal;
    private StatusAtivoEnum status;
    private String observacoes;
}
