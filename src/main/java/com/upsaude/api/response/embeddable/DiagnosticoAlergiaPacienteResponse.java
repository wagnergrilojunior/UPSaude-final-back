package com.upsaude.api.response.embeddable;

import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoAlergiaPacienteResponse {
    private LocalDate dataDiagnostico;
    private LocalDate dataPrimeiraReacao;
    private SeveridadeAlergiaEnum severidade;
    private TipoReacaoAlergicaEnum tipoReacaoObservada;
    private String metodoDiagnostico;
    private String localDiagnostico;
    private String profissionalDiagnostico;
}
