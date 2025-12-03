package com.upsaude.dto;

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
public class DoencasDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private String nome;
    private String nomeCientifico;
    private String codigoInterno;
    private ClassificacaoDoenca classificacao;
    private CidDoencasDTO cidPrincipal;
    private SintomasDoenca sintomas;
    private TratamentoPadraoDoenca tratamentoPadrao;
    private EpidemiologiaDoenca epidemiologia;
    private String descricao;
    private String causas;
    private String fisiopatologia;
    private String prognostico;
    private String observacoes;
}
