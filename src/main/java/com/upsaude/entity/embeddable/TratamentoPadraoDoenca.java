package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Classe embeddable para tratamento padrão da doença.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamentoPadraoDoenca {

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

