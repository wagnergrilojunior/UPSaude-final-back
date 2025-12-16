package com.upsaude.entity.embeddable;

import com.upsaude.enums.TipoEspecialidadeMedicaEnum;
import com.upsaude.util.converter.TipoEspecialidadeMedicaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ClassificacaoEspecialidadeMedica {

    public ClassificacaoEspecialidadeMedica() {
        this.codigoCfm = "";
        this.codigoCnes = "";
        this.areaAtuacao = "";
        this.subarea = "";
        this.requerResidencia = false;
        this.requerTituloEspecialista = false;
    }

    @Convert(converter = TipoEspecialidadeMedicaEnumConverter.class)
    @Column(name = "tipo_especialidade")
    private TipoEspecialidadeMedicaEnum tipoEspecialidade;

    @Size(max = 50, message = "Código CFM deve ter no máximo 50 caracteres")
    @Column(name = "codigo_cfm", length = 50)
    private String codigoCfm;

    @Size(max = 50, message = "Código CNES deve ter no máximo 50 caracteres")
    @Column(name = "codigo_cnes", length = 50)
    private String codigoCnes;

    @Size(max = 100, message = "Área de atuação deve ter no máximo 100 caracteres")
    @Column(name = "area_atuacao", length = 100)
    private String areaAtuacao;

    @Size(max = 100, message = "Subárea deve ter no máximo 100 caracteres")
    @Column(name = "subarea", length = 100)
    private String subarea;

    @Column(name = "requer_residencia", nullable = false)
    @Builder.Default
    private Boolean requerResidencia = false;

    @Column(name = "requer_titulo_especialista", nullable = false)
    @Builder.Default
    private Boolean requerTituloEspecialista = false;
}
