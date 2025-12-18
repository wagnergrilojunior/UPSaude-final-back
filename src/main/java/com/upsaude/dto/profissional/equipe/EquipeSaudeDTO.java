package com.upsaude.dto.profissional.equipe;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeSaudeDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String ine;
    private String nomeReferencia;
    private TipoEquipeEnum tipoEquipe;
    private EstabelecimentosDTO estabelecimento;
    private OffsetDateTime dataAtivacao;
    private OffsetDateTime dataInativacao;
    private StatusAtivoEnum status;
    private String observacoes;
}
