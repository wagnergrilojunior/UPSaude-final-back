package com.upsaude.api.request;

import com.upsaude.entity.embeddable.ClassificacaoAlergia;
import com.upsaude.entity.embeddable.PrevencaoTratamentoAlergia;
import com.upsaude.entity.embeddable.ReacoesAlergia;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlergiasRequest {
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoAlergia classificacao;
    private ReacoesAlergia reacoes;
    private PrevencaoTratamentoAlergia prevencaoTratamento;
    private String descricao;
    private String substanciasRelacionadas;
    private String observacoes;
}
