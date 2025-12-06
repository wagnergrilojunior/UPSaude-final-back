package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para tratamento padrão da doença.
 *
 * @author UPSaúde
 */
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
    private String tratamentoPadrao; // Tratamento padrão recomendado

    @Column(name = "medicamentos_comuns", columnDefinition = "TEXT")
    private String medicamentosComuns; // Medicamentos comumente utilizados

    @Column(name = "procedimentos_comuns", columnDefinition = "TEXT")
    private String procedimentosComuns; // Procedimentos comumente realizados

    @Column(name = "cuidados_especiais", columnDefinition = "TEXT")
    private String cuidadosEspeciais; // Cuidados especiais necessários

    @Column(name = "prevencao", columnDefinition = "TEXT")
    private String prevencao; // Medidas de prevenção
}

