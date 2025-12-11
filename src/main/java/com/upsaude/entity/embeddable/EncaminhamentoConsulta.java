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
public class EncaminhamentoConsulta {

    public EncaminhamentoConsulta() {
        this.encaminhamentos = "";
        this.especialistaEncaminhado = "";
        this.motivoEncaminhamento = "";
        this.urgenciaEncaminhamento = false;
        this.prazoEncaminhamento = "";
    }

    @Column(name = "encaminhamentos", columnDefinition = "TEXT")
    private String encaminhamentos;

    @Column(name = "especialista_encaminhado", length = 255)
    private String especialistaEncaminhado;

    @Column(name = "motivo_encaminhamento", columnDefinition = "TEXT")
    private String motivoEncaminhamento;

    @Column(name = "urgencia_encaminhamento", nullable = false)
    @Builder.Default
    private Boolean urgenciaEncaminhamento = false;

    @Column(name = "prazo_encaminhamento", length = 50)
    private String prazoEncaminhamento;
}
