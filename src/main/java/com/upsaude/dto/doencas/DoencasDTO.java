package com.upsaude.dto.doencas;

import com.upsaude.dto.embeddable.ClassificacaoDoencaDTO;
import com.upsaude.dto.embeddable.EpidemiologiaDoencaDTO;
import com.upsaude.dto.embeddable.SintomasDoencaDTO;
import com.upsaude.dto.embeddable.TratamentoPadraoDoencaDTO;
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
    private ClassificacaoDoencaDTO classificacao;
    private SintomasDoencaDTO sintomas;
    private TratamentoPadraoDoencaDTO tratamentoPadrao;
    private EpidemiologiaDoencaDTO epidemiologia;
    private String descricao;
    private String causas;
    private String fisiopatologia;
    private String prognostico;
    private String observacoes;
}
