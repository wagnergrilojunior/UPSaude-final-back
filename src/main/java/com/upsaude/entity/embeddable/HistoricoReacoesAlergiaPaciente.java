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

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class HistoricoReacoesAlergiaPaciente {

    public HistoricoReacoesAlergiaPaciente() {
        this.reacaoMaisGrave = "";
        this.tratamentoUtilizado = "";
        this.necessitouHospitalizacao = false;
    }

    @Column(name = "data_ultima_reacao")
    private LocalDate dataUltimaReacao;

    @Column(name = "numero_reacoes")
    private Integer numeroReacoes;

    @Convert(converter = TipoReacaoAlergicaEnumConverter.class)
    @Column(name = "tipo_ultima_reacao")
    private TipoReacaoAlergicaEnum tipoUltimaReacao;

    @Convert(converter = SeveridadeAlergiaEnumConverter.class)
    @Column(name = "severidade_ultima_reacao")
    private SeveridadeAlergiaEnum severidadeUltimaReacao;

    @Column(name = "reacao_mais_grave", columnDefinition = "TEXT")
    private String reacaoMaisGrave;

    @Column(name = "tratamento_utilizado", columnDefinition = "TEXT")
    private String tratamentoUtilizado;

    @Column(name = "necessitou_hospitalizacao", nullable = false)
    @Builder.Default
    private Boolean necessitouHospitalizacao = false;
}
