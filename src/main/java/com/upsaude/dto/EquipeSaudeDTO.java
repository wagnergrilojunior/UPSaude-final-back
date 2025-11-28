package com.upsaude.dto;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
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
public class EquipeSaudeDTO {
    private UUID id;
    private String ine;
    private String nomeReferencia;
    private TipoEquipeEnum tipoEquipe;
    private UUID estabelecimentoId;
    private OffsetDateTime dataAtivacao;
    private OffsetDateTime dataInativacao;
    private StatusAtivoEnum status;
    private String observacoes;
    private Boolean active;
}

