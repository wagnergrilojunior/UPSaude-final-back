package com.upsaude.api.response.embeddable;

import com.upsaude.enums.TipoEquipamentoEnum;
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
public class DadosIdentificacaoEquipamentoResponse {

    private String nome;

    private String nomeComercial;

    private String codigoInterno;

    private String codigoCnes;

    private String registroAnvisa;

    private TipoEquipamentoEnum tipo;

    private String categoria;

    private String subcategoria;

    private String classeRisco;
}
