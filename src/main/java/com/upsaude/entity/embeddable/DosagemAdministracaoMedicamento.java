package com.upsaude.entity.embeddable;

import com.upsaude.enums.UnidadeMedidaEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import com.upsaude.util.converter.UnidadeMedidaEnumConverter;
import com.upsaude.util.converter.ViaAdministracaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class DosagemAdministracaoMedicamento {

    public DosagemAdministracaoMedicamento() {
        this.dosagem = "";
        this.unidadeConcentracao = "";
        this.posologiaPadrao = "";
        this.instrucoesUso = "";
    }

    @Column(name = "dosagem", nullable = false, length = 50)
    private String dosagem;

    @Convert(converter = UnidadeMedidaEnumConverter.class)
    @Column(name = "unidade_medida")
    private UnidadeMedidaEnum unidadeMedida;

    @Convert(converter = ViaAdministracaoEnumConverter.class)
    @Column(name = "via_administracao")
    private ViaAdministracaoEnum viaAdministracao;

    @Column(name = "concentracao", precision = 10, scale = 2)
    private BigDecimal concentracao;

    @Column(name = "unidade_concentracao", length = 50)
    private String unidadeConcentracao;

    @Column(name = "posologia_padrao", length = 100)
    private String posologiaPadrao;

    @Column(name = "instrucoes_uso", length = 255)
    private String instrucoesUso;
}
