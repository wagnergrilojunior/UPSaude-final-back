package com.upsaude.dto;

import com.upsaude.entity.embeddable.ClassificacaoAlergia;
import com.upsaude.entity.embeddable.PrevencaoTratamentoAlergia;
import com.upsaude.entity.embeddable.ReacoesAlergia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasDTO {
    private UUID id;
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoAlergia classificacao;
    private ReacoesAlergia reacoes;
    private PrevencaoTratamentoAlergia prevencaoTratamento;
    private String descricao;
    private String substanciasRelacionadas;
    private String observacoes;
    private Boolean active;
}
