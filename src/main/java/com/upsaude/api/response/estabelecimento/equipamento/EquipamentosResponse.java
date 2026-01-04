package com.upsaude.api.response.estabelecimento.equipamento;

import com.upsaude.api.response.embeddable.DadosIdentificacaoEquipamentoResponse;
import com.upsaude.api.response.embeddable.DescricoesEquipamentoResponse;
import com.upsaude.api.response.embeddable.EspecificacoesTecnicasEquipamentoResponse;
import com.upsaude.api.response.embeddable.ManutencaoCalibracaoEquipamentoResponse;
import com.upsaude.enums.StatusAtivoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipamentosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    
    private DadosIdentificacaoEquipamentoResponse dadosIdentificacao;
    
    private FabricantesEquipamentoResponse fabricante;
    
    private EspecificacoesTecnicasEquipamentoResponse especificacoesTecnicas;
    
    private ManutencaoCalibracaoEquipamentoResponse manutencaoCalibracao;
    
    private StatusAtivoEnum status;
    
    private Boolean disponivelUso;
    
    private DescricoesEquipamentoResponse descricoes;
}
