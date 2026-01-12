package com.upsaude.api.response.profissional.equipe;

import com.upsaude.enums.TipoEquipeEnum;
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
public class EquipeSaudeSimplificadoResponse {
    private UUID id;
    private String nomeReferencia;
    private TipoEquipeEnum tipoEquipe;
    private UUID estabelecimentoId;
}
