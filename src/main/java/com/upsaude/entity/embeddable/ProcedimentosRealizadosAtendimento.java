package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de procedimentos realizados no atendimento.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@AllArgsConstructor
public class ProcedimentosRealizadosAtendimento {

    public ProcedimentosRealizadosAtendimento() {
        this.procedimentosRealizados = "";
        this.examesSolicitados = "";
        this.medicamentosPrescritos = "";
        this.orientacoes = "";
        this.encaminhamentos = "";
    }

    @Column(name = "procedimentos_realizados", columnDefinition = "TEXT")
    private String procedimentosRealizados; // Procedimentos realizados durante o atendimento

    @Column(name = "exames_solicitados", columnDefinition = "TEXT")
    private String examesSolicitados; // Exames solicitados

    @Column(name = "medicamentos_prescritos", columnDefinition = "TEXT")
    private String medicamentosPrescritos; // Medicamentos prescritos

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes; // Orientações dadas ao paciente

    @Column(name = "encaminhamentos", columnDefinition = "TEXT")
    private String encaminhamentos; // Encaminhamentos realizados
}

