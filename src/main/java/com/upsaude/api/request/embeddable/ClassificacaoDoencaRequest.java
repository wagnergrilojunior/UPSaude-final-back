package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.TipoDoencaEnum;
import com.upsaude.util.converter.GravidadeDoencaEnumDeserializer;
import com.upsaude.util.converter.TipoDoencaEnumDeserializer;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Request para campos complementares de classificação da doença.
 * 
 * NOTA: Os campos categoria, subcategoria e codigoCidPrincipal foram removidos
 * pois agora vêm diretamente da tabela oficial CID-10 através do campo
 * cid10SubcategoriaId no DoencasRequest.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados complementares de classificação da doença (campos oficiais vêm do CID-10)")
public class ClassificacaoDoencaRequest {
    
    @Schema(description = "Tipo de doença (classificação interna)")
    @JsonDeserialize(using = TipoDoencaEnumDeserializer.class)
    private TipoDoencaEnum tipoDoenca;

    @Schema(description = "Gravidade da doença (classificação interna)")
    @JsonDeserialize(using = GravidadeDoencaEnumDeserializer.class)
    private GravidadeDoencaEnum gravidade;

    @Schema(description = "Indica se a doença é de notificação compulsória")
    @NotNull(message = "Doença notificável é obrigatória")
    @Builder.Default
    private Boolean doencaNotificavel = false;

    @Schema(description = "Indica se a doença é transmissível")
    @NotNull(message = "Doença transmissível é obrigatória")
    @Builder.Default
    private Boolean doencaTransmissivel = false;
}
