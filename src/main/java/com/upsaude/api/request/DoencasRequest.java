package com.upsaude.api.request;

import com.upsaude.entity.embeddable.ClassificacaoDoenca;
import com.upsaude.entity.embeddable.EpidemiologiaDoenca;
import com.upsaude.entity.embeddable.SintomasDoenca;
import com.upsaude.entity.embeddable.TratamentoPadraoDoenca;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasRequest {
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoDoenca classificacao;
    private UUID cidPrincipal;
    private SintomasDoenca sintomas;
    private TratamentoPadraoDoenca tratamentoPadrao;
    private EpidemiologiaDoenca epidemiologia;
    private String descricao;
    private String causas;
    private String fisiopatologia;
    private String prognostico;
    private String observacoes;
}
