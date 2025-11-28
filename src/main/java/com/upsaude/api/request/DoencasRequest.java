package com.upsaude.api.request;

import com.upsaude.entity.embeddable.ClassificacaoDoenca;
import com.upsaude.entity.embeddable.EpidemiologiaDoenca;
import com.upsaude.entity.embeddable.SintomasDoenca;
import com.upsaude.entity.embeddable.TratamentoPadraoDoenca;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DoencasRequest {
    @NotBlank(message = "Nome da doença é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    private String nome;

    @Size(max = 255, message = "Nome científico deve ter no máximo 255 caracteres")
    private String nomeCientifico;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
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
}

