package com.upsaude.api.response;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipeEnum;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
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

    @Builder.Default
    private Set<VinculoProfissionalEquipeResponse> vinculosProfissionais = new HashSet<>();

    private String observacoes;
}
