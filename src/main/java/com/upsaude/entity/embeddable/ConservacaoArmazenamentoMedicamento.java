package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

/**
 * Classe embeddable para conservação e armazenamento do medicamento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ConservacaoArmazenamentoMedicamento {

    public ConservacaoArmazenamentoMedicamento() {
        this.condicoesArmazenamento = "";
        this.protegerLuz = false;
        this.protegerUmidade = false;
        this.instrucoesConservacao = "";
    }

    @Column(name = "temperatura_conservacao_min", precision = 5, scale = 2)
    private BigDecimal temperaturaConservacaoMin; // Temperatura mínima de conservação

    @Column(name = "temperatura_conservacao_max", precision = 5, scale = 2)
    private BigDecimal temperaturaConservacaoMax; // Temperatura máxima de conservação

    @Column(name = "proteger_luz", nullable = false)
    @Builder.Default
    private Boolean protegerLuz = false; // Se deve proteger da luz

    @Column(name = "proteger_umidade", nullable = false)
    @Builder.Default
    private Boolean protegerUmidade = false; // Se deve proteger da umidade

    @Column(name = "condicoes_armazenamento", length = 255)
    private String condicoesArmazenamento; // Condições específicas de armazenamento

    @Column(name = "validade_apos_abertura_dias")
    private Integer validadeAposAberturaDias; // Validade em dias após abertura

    @Column(name = "instrucoes_conservacao", columnDefinition = "TEXT")
    private String instrucoesConservacao; // Instruções de conservação
}

