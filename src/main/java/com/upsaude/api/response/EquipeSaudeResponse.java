package com.upsaude.api.response;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeSaudeResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String ine;
    private String nomeReferencia;
    private TipoEquipeEnum tipoEquipe;
    private EstabelecimentosResponse estabelecimento;
    private OffsetDateTime dataAtivacao;
    private OffsetDateTime dataInativacao;
    private StatusAtivoEnum status;
    private String observacoes;
}
