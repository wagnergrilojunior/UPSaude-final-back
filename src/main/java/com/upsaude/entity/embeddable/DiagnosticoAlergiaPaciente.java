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
public class DiagnosticoAlergiaPaciente {

    public DiagnosticoAlergiaPaciente() {
        this.metodoDiagnostico = "";
        this.localDiagnostico = "";
        this.profissionalDiagnostico = "";
    }

    @Column(name = "data_diagnostico")
    private LocalDate dataDiagnostico;

    @Column(name = "data_primeira_reacao")
    private LocalDate dataPrimeiraReacao;

    @Convert(converter = SeveridadeAlergiaEnumConverter.class)
    @Column(name = "severidade")
    private SeveridadeAlergiaEnum severidade;

    @Convert(converter = TipoReacaoAlergicaEnumConverter.class)
    @Column(name = "tipo_reacao_observada")
    private TipoReacaoAlergicaEnum tipoReacaoObservada;

    @Column(name = "metodo_diagnostico", length = 255)
    private String metodoDiagnostico;

    @Column(name = "local_diagnostico", length = 255)
    private String localDiagnostico;

    @Column(name = "profissional_diagnostico", length = 255)
    private String profissionalDiagnostico;
}
