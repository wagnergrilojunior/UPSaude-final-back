package com.upsaude.api.response.embeddable;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
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
public class DadosIdentificacaoEstabelecimentoResponse {

    private String nome;

    private String nomeFantasia;

    private TipoEstabelecimentoEnum tipo;

    private String cnes;

    private String cnpj;

    private NaturezaJuridicaEnum naturezaJuridica;
}

