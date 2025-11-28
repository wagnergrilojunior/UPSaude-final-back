package com.upsaude.entity.embeddable;

import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoMedicamentoEnum;
import com.upsaude.util.converter.UnidadeMedidaEnumConverter;
import com.upsaude.util.converter.ViaAdministracaoMedicamentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Classe embeddable para dosagem e administração do medicamento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DosagemAdministracaoMedicamento {

    @NotBlank(message = "Dosagem é obrigatória")
    @Size(max = 50, message = "Dosagem deve ter no máximo 50 caracteres")
    @Column(name = "dosagem", nullable = false, length = 50)
    private String dosagem; // Ex: 500mg, 10ml

    @Convert(converter = UnidadeMedidaEnumConverter.class)
    @Column(name = "unidade_medida")
    private UnidadeMedidaEnum unidadeMedida;

    @Convert(converter = ViaAdministracaoMedicamentoEnumConverter.class)
    @Column(name = "via_administracao")
    private ViaAdministracaoMedicamentoEnum viaAdministracao;

    @Column(name = "concentracao", precision = 10, scale = 2)
    private BigDecimal concentracao; // Concentração do princípio ativo

    @Size(max = 50, message = "Unidade concentração deve ter no máximo 50 caracteres")
    @Column(name = "unidade_concentracao", length = 50)
    private String unidadeConcentracao; // Ex: mg/ml, mg/g

    @Size(max = 100, message = "Posologia padrão deve ter no máximo 100 caracteres")
    @Column(name = "posologia_padrao", length = 100)
    private String posologiaPadrao; // Ex: 1 comprimido 3x ao dia

    @Size(max = 255, message = "Instruções de uso deve ter no máximo 255 caracteres")
    @Column(name = "instrucoes_uso", length = 255)
    private String instrucoesUso; // Instruções gerais de uso
}

