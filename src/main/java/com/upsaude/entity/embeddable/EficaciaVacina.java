package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Classe embeddable para informações de eficácia e proteção da vacina.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EficaciaVacina {

    @Column(name = "eficacia_percentual", precision = 5, scale = 2)
    private BigDecimal eficaciaPercentual; // Eficácia da vacina em percentual

    @Column(name = "protecao_inicio_dias")
    private Integer protecaoInicioDias; // Dias após aplicação para início da proteção

    @Column(name = "protecao_duracao_anos")
    private Integer protecaoDuracaoAnos; // Duração estimada da proteção em anos

    @Column(name = "doencas_protege", columnDefinition = "TEXT")
    private String doencasProtege; // Doenças que a vacina protege
}

