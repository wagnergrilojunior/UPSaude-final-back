package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class TratamentoPadraoDoenca {

    public TratamentoPadraoDoenca() {
        this.tratamentoPadrao = "";
        this.medicamentosComuns = "";
        this.procedimentosComuns = "";
        this.cuidadosEspeciais = "";
        this.prevencao = "";
    }

    @Column(name = "tratamento_padrao", columnDefinition = "TEXT")
    private String tratamentoPadrao;

    @Column(name = "medicamentos_comuns", columnDefinition = "TEXT")
    private String medicamentosComuns;

    @Column(name = "procedimentos_comuns", columnDefinition = "TEXT")
    private String procedimentosComuns;

    @Column(name = "cuidados_especiais", columnDefinition = "TEXT")
    private String cuidadosEspeciais;

    @Column(name = "prevencao", columnDefinition = "TEXT")
    private String prevencao;
}
