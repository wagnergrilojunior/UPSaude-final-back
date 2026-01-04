package com.upsaude.entity.embeddable;

import com.upsaude.enums.TipoEspecialidadeMedicaEnum;
import com.upsaude.util.converter.TipoEspecialidadeMedicaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
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

    @Column(name = "codigo_cfm", length = 50)
    private String codigoCfm;

    @Column(name = "codigo_cnes", length = 50)
    private String codigoCnes;

    @Column(name = "area_atuacao", length = 100)
    private String areaAtuacao;

    @Column(name = "subarea", length = 100)
    private String subarea;

    @Column(name = "requer_residencia", nullable = false)
    @Builder.Default
    private Boolean requerResidencia = false;

    @Column(name = "requer_titulo_especialista", nullable = false)
    @Builder.Default
    private Boolean requerTituloEspecialista = false;
}
