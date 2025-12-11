package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoAlergiaEnum;
import com.upsaude.util.converter.TipoAlergiaEnumDeserializer;
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
public class ClassificacaoAlergiaRequest {
    @JsonDeserialize(using = TipoAlergiaEnumDeserializer.class)
    private TipoAlergiaEnum tipoAlergia;
    
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;
    
    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    private String subcategoria;
    
    @Size(max = 50, message = "Código CID deve ter no máximo 50 caracteres")
    private String codigoCid;
    
    @NotNull(message = "Alergia comum é obrigatória")
    @Builder.Default
    private Boolean alergiaComum = false;
    
    @NotNull(message = "Alergia grave é obrigatória")
    @Builder.Default
    private Boolean alergiaGrave = false;
}
