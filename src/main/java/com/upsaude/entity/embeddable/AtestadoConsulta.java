package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe embeddable para informações de atestados emitidos na consulta.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtestadoConsulta {

    @Column(name = "atestado_emitido", nullable = false)
    @Builder.Default
    private Boolean atestadoEmitido = false; // Se foi emitido atestado

    @Column(name = "tipo_atestado", length = 100)
    private String tipoAtestado; // Ex: Atestado Médico, Atestado de Comparecimento, Atestado de Saúde

    @Column(name = "dias_afastamento")
    private Integer diasAfastamento; // Dias de afastamento (se aplicável)

    @Column(name = "data_inicio_afastamento")
    private LocalDate dataInicioAfastamento; // Data de início do afastamento

    @Column(name = "data_fim_afastamento")
    private LocalDate dataFimAfastamento; // Data de fim do afastamento

    @Column(name = "motivo_atestado", columnDefinition = "TEXT")
    private String motivoAtestado; // Motivo do atestado

    @Column(name = "cid_atestado", length = 10)
    private String cidAtestado; // CID relacionado ao atestado
}

