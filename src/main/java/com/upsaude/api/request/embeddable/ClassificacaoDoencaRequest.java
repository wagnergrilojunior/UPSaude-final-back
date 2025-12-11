package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.GravidadeDoencaEnum;
import com.upsaude.enums.TipoDoencaEnum;
import com.upsaude.util.converter.GravidadeDoencaEnumDeserializer;
import com.upsaude.util.converter.TipoDoencaEnumDeserializer;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ClassificacaoDoencaRequest {
    @JsonDeserialize(using = TipoDoencaEnumDeserializer.class)
    private TipoDoencaEnum tipoDoenca;
    
    @JsonDeserialize(using = GravidadeDoencaEnumDeserializer.class)
    private GravidadeDoencaEnum gravidade;
    
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;
    
    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    private String subcategoria;
    
    @Size(max = 50, message = "Código CID principal deve ter no máximo 50 caracteres")
    private String codigoCidPrincipal;
    
    @NotNull(message = "Doença notificável é obrigatória")
    @Builder.Default
    private Boolean doencaNotificavel = false;
    
    @NotNull(message = "Doença transmissível é obrigatória")
    @Builder.Default
    private Boolean doencaTransmissivel = false;
}
