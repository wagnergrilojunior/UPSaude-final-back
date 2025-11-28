package com.upsaude.dto;

import com.upsaude.entity.embeddable.ClassificacaoDoenca;
import com.upsaude.entity.embeddable.EpidemiologiaDoenca;
import com.upsaude.entity.embeddable.SintomasDoenca;
import com.upsaude.entity.embeddable.TratamentoPadraoDoenca;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasDTO {
    private UUID id;
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoDoenca classificacao;
    private UUID cidPrincipalId;
    private SintomasDoenca sintomas;
    private TratamentoPadraoDoenca tratamentoPadrao;
    private EpidemiologiaDoenca epidemiologia;
    private String descricao;
    private String causas;
    private String fisiopatologia;
    private String prognostico;
    private String observacoes;
    private Boolean active;
}

