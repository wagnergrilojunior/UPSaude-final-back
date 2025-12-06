package com.upsaude.api.response.embeddable;

import com.upsaude.enums.TipoAlergiaEnum;
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
public class ClassificacaoAlergiaResponse {
    private TipoAlergiaEnum tipoAlergia;
    private String categoria;
    private String subcategoria;
    private String codigoCid;
    private Boolean alergiaComum;
    private Boolean alergiaGrave;
}
