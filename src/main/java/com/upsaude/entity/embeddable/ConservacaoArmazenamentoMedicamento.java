package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;

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
    private BigDecimal temperaturaConservacaoMin;

    @Column(name = "temperatura_conservacao_max", precision = 5, scale = 2)
    private BigDecimal temperaturaConservacaoMax;

    @Column(name = "proteger_luz", nullable = false)
    @Builder.Default
    private Boolean protegerLuz = false;

    @Column(name = "proteger_umidade", nullable = false)
    @Builder.Default
    private Boolean protegerUmidade = false;

    @Column(name = "condicoes_armazenamento", length = 255)
    private String condicoesArmazenamento;

    @Column(name = "validade_apos_abertura_dias")
    private Integer validadeAposAberturaDias;

    @Column(name = "instrucoes_conservacao", columnDefinition = "TEXT")
    private String instrucoesConservacao;
}
