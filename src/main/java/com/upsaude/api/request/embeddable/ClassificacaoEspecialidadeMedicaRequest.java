package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoEspecialidadeMedicaEnum;
import com.upsaude.util.converter.TipoEspecialidadeMedicaEnumDeserializer;
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
public class ClassificacaoEspecialidadeMedicaRequest {
    @JsonDeserialize(using = TipoEspecialidadeMedicaEnumDeserializer.class)
    private TipoEspecialidadeMedicaEnum tipoEspecialidade;

    @Size(max = 50, message = "Código CFM deve ter no máximo 50 caracteres")
    private String codigoCfm;

    @Size(max = 50, message = "Código CNES deve ter no máximo 50 caracteres")
    private String codigoCnes;

    @Size(max = 100, message = "Área de atuação deve ter no máximo 100 caracteres")
    private String areaAtuacao;

    @Size(max = 100, message = "Subárea deve ter no máximo 100 caracteres")
    private String subarea;

    @NotNull(message = "Requer residência é obrigatório")
    @Builder.Default
    private Boolean requerResidencia = false;

    @NotNull(message = "Requer título especialista é obrigatório")
    @Builder.Default
    private Boolean requerTituloEspecialista = false;
}
