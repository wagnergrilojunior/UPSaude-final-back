package com.upsaude.api.request;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeSaudeRequest {
    private String ine;
    private String nomeReferencia;
    private TipoEquipeEnum tipoEquipe;
    private UUID estabelecimento;
    private OffsetDateTime dataAtivacao;
    private OffsetDateTime dataInativacao;
    private StatusAtivoEnum status;
    private String observacoes;
}
