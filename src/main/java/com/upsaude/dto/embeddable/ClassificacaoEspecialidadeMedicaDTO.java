package com.upsaude.dto.embeddable;

import com.upsaude.enums.TipoEspecialidadeMedicaEnum;
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
public class ClassificacaoEspecialidadeMedicaDTO {
    private TipoEspecialidadeMedicaEnum tipoEspecialidade;
    private String codigoCfm;
    private String codigoCnes;
    private String areaAtuacao;
    private String subarea;
    private Boolean requerResidencia;
    private Boolean requerTituloEspecialista;
}
