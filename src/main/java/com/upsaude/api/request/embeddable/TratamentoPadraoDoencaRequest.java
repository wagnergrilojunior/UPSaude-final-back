package com.upsaude.api.request.embeddable;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de tratamento padrao doenca")
public class TratamentoPadraoDoencaRequest {
    private String tratamentoPadrao;
    private String medicamentosComuns;
    private String procedimentosComuns;
    private String cuidadosEspeciais;
    private String prevencao;
}
