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
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para classificação da especialidade médica.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClassificacaoEspecialidadeMedica {

    @Convert(converter = TipoEspecialidadeMedicaEnumConverter.class)
    @Column(name = "tipo_especialidade")
    private TipoEspecialidadeMedicaEnum tipoEspecialidade;

    @Size(max = 50, message = "Código CFM deve ter no máximo 50 caracteres")
    @Column(name = "codigo_cfm", length = 50)
    private String codigoCfm; // Código da especialidade no CFM

    @Size(max = 50, message = "Código CNES deve ter no máximo 50 caracteres")
    @Column(name = "codigo_cnes", length = 50)
    private String codigoCnes; // Código da especialidade no CNES

    @Size(max = 100, message = "Área de atuação deve ter no máximo 100 caracteres")
    @Column(name = "area_atuacao", length = 100)
    private String areaAtuacao; // Ex: Clínica, Cirúrgica, Diagnóstica

    @Size(max = 100, message = "Subárea deve ter no máximo 100 caracteres")
    @Column(name = "subarea", length = 100)
    private String subarea; // Subárea da especialidade

    @Column(name = "requer_residencia", nullable = false)
    @Builder.Default
    private Boolean requerResidencia = false; // Se requer residência médica

    @Column(name = "requer_titulo_especialista", nullable = false)
    @Builder.Default
    private Boolean requerTituloEspecialista = false; // Se requer título de especialista
}

