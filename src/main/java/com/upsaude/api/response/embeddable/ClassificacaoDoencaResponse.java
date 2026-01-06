package com.upsaude.api.response.embeddable;

import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.TipoDoencaEnum;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados complementares de classificação da doença")
public class ClassificacaoDoencaResponse {

    @Schema(description = "Tipo de doença (classificação interna)")
    private TipoDoencaEnum tipoDoenca;

    @Schema(description = "Gravidade da doença (classificação interna)")
    private GravidadeDoencaEnum gravidade;

    @Schema(description = "Indica se a doença é de notificação compulsória")
    private Boolean doencaNotificavel;

    @Schema(description = "Indica se a doença é transmissível")
    private Boolean doencaTransmissivel;
}
