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
public class ProcedimentosRealizadosAtendimento {

    public ProcedimentosRealizadosAtendimento() {
        this.procedimentosRealizados = "";
        this.examesSolicitados = "";
        this.medicamentosPrescritos = "";
        this.orientacoes = "";
        this.encaminhamentos = "";
    }

    @Column(name = "procedimentos_realizados", columnDefinition = "TEXT")
    private String procedimentosRealizados;

    @Column(name = "exames_solicitados", columnDefinition = "TEXT")
    private String examesSolicitados;

    @Column(name = "medicamentos_prescritos", columnDefinition = "TEXT")
    private String medicamentosPrescritos;

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes;

    @Column(name = "encaminhamentos", columnDefinition = "TEXT")
    private String encaminhamentos;
}
