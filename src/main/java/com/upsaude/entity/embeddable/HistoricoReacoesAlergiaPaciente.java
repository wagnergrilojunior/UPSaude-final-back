package com.upsaude.entity.embeddable;

import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import com.upsaude.util.converter.SeveridadeAlergiaEnumConverter;
import com.upsaude.util.converter.TipoReacaoAlergicaEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Classe embeddable para histórico de reações alérgicas do paciente.
 *
 * @author UPSaúde
 */
@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class HistoricoReacoesAlergiaPaciente {

    @Column(name = "data_ultima_reacao")
    private LocalDate dataUltimaReacao; // Data da última reação alérgica

    @Column(name = "numero_reacoes")
    private Integer numeroReacoes; // Número de reações já ocorridas

    @Convert(converter = TipoReacaoAlergicaEnumConverter.class)
    @Column(name = "tipo_ultima_reacao")
    private TipoReacaoAlergicaEnum tipoUltimaReacao; // Tipo da última reação

    @Convert(converter = SeveridadeAlergiaEnumConverter.class)
    @Column(name = "severidade_ultima_reacao")
    private SeveridadeAlergiaEnum severidadeUltimaReacao; // Severidade da última reação

    @Column(name = "reacao_mais_grave", columnDefinition = "TEXT")
    private String reacaoMaisGrave; // Descrição da reação mais grave já ocorrida

    @Column(name = "tratamento_utilizado", columnDefinition = "TEXT")
    private String tratamentoUtilizado; // Tratamento utilizado nas reações anteriores

    @Column(name = "necessitou_hospitalizacao", nullable = false)
    @Builder.Default
    private Boolean necessitouHospitalizacao = false; // Se já necessitou hospitalização por reação alérgica
}

