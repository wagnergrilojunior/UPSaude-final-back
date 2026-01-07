package com.upsaude.api.response.clinica.atendimento;

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
public class EquipeSaudeCheckInResponse {
    private UUID id;
    private String ine;
    private String nomeReferencia;
    private TipoEquipeEnum tipoEquipe;
    private UUID estabelecimentoId;
}
