package com.upsaude.dto.embeddable;

import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DiagnosticoAlergiaPacienteDTO {
    private LocalDate dataDiagnostico;
    private LocalDate dataPrimeiraReacao;
    private SeveridadeAlergiaEnum severidade;
    private TipoReacaoAlergicaEnum tipoReacaoObservada;
    private String metodoDiagnostico;
    private String localDiagnostico;
    private String profissionalDiagnostico;
}
