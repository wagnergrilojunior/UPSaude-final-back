package com.upsaude.api.response;

import com.upsaude.api.response.embeddable.ClassificacaoDoencaResponse;
import com.upsaude.api.response.embeddable.EpidemiologiaDoencaResponse;
import com.upsaude.api.response.embeddable.SintomasDoencaResponse;
import com.upsaude.api.response.embeddable.TratamentoPadraoDoencaResponse;
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
public class DoencasResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoDoencaResponse classificacao;
    private Boolean cronica;
    private CidDoencasResponse cidPrincipal;
    private SintomasDoencaResponse sintomas;
    private TratamentoPadraoDoencaResponse tratamentoPadrao;
    private EpidemiologiaDoencaResponse epidemiologia;
    private String descricao;
    private String causas;
    private String fisiopatologia;
    private String prognostico;
    private String observacoes;
}
