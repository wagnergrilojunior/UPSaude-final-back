package com.upsaude.api.response.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.TipoDoencaEnum;
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
public class ClassificacaoDoencaResponse {
    private TipoDoencaEnum tipoDoenca;
    private GravidadeDoencaEnum gravidade;
    private String categoria;
    private String subcategoria;
    private String codigoCidPrincipal;
    private Boolean doencaNotificavel;
    private Boolean doencaTransmissivel;
}
