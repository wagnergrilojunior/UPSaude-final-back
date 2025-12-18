package com.upsaude.api.response.alergia;

import com.upsaude.api.response.embeddable.ClassificacaoAlergiaResponse;
import com.upsaude.api.response.embeddable.PrevencaoTratamentoAlergiaResponse;
import com.upsaude.api.response.embeddable.ReacoesAlergiaResponse;
import java.time.OffsetDateTime;
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
public class AlergiasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoAlergiaResponse classificacao;
    private ReacoesAlergiaResponse reacoes;
    private PrevencaoTratamentoAlergiaResponse prevencaoTratamento;
    private String descricao;
    private String substanciasRelacionadas;
    private String observacoes;
}
