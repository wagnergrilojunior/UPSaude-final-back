package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class AtestadoConsulta {

    public AtestadoConsulta() {
        this.atestadoEmitido = false;
        this.tipoAtestado = "";
        this.motivoAtestado = "";
        this.cidAtestado = "";
    }

    @Column(name = "atestado_emitido", nullable = false)
    @Builder.Default
    private Boolean atestadoEmitido = false;

    @Column(name = "tipo_atestado", length = 100)
    private String tipoAtestado;

    @Column(name = "dias_afastamento")
    private Integer diasAfastamento;

    @Column(name = "data_inicio_afastamento")
    private LocalDate dataInicioAfastamento;

    @Column(name = "data_fim_afastamento")
    private LocalDate dataFimAfastamento;

    @Column(name = "motivo_atestado", columnDefinition = "TEXT")
    private String motivoAtestado;

    @Column(name = "cid_atestado", length = 10)
    private String cidAtestado;
}
