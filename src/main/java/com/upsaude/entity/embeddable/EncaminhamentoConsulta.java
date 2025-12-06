package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para informações de encaminhamentos da consulta.
 *
 * @author UPSaúde
 */
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
    private String encaminhamentos; // Encaminhamentos realizados

    @Column(name = "especialista_encaminhado", length = 255)
    private String especialistaEncaminhado; // Especialista para o qual foi encaminhado

    @Column(name = "motivo_encaminhamento", columnDefinition = "TEXT")
    private String motivoEncaminhamento; // Motivo do encaminhamento

    @Column(name = "urgencia_encaminhamento", nullable = false)
    @Builder.Default
    private Boolean urgenciaEncaminhamento = false; // Se o encaminhamento é urgente

    @Column(name = "prazo_encaminhamento", length = 50)
    private String prazoEncaminhamento; // Prazo para o encaminhamento (ex: 30 dias)
}

