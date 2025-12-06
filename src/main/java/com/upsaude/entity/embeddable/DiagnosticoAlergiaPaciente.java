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

/**
 * Classe embeddable para informações de diagnóstico da alergia no paciente.
 *
 * @author UPSaúde
 */
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
    private LocalDate dataDiagnostico; // Data do diagnóstico da alergia

    @Column(name = "data_primeira_reacao")
    private LocalDate dataPrimeiraReacao; // Data da primeira reação alérgica

    @Convert(converter = SeveridadeAlergiaEnumConverter.class)
    @Column(name = "severidade")
    private SeveridadeAlergiaEnum severidade; // Severidade da alergia

    @Convert(converter = TipoReacaoAlergicaEnumConverter.class)
    @Column(name = "tipo_reacao_observada")
    private TipoReacaoAlergicaEnum tipoReacaoObservada; // Tipo de reação observada no paciente

    @Column(name = "metodo_diagnostico", length = 255)
    private String metodoDiagnostico; // Método de diagnóstico (ex: Teste cutâneo, RAST, Teste de provocação)

    @Column(name = "local_diagnostico", length = 255)
    private String localDiagnostico; // Local onde foi feito o diagnóstico

    @Column(name = "profissional_diagnostico", length = 255)
    private String profissionalDiagnostico; // Profissional que fez o diagnóstico
}

