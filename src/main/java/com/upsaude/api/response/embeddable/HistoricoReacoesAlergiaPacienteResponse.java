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
public class HistoricoReacoesAlergiaPacienteResponse {
    private LocalDate dataUltimaReacao;
    private Integer numeroReacoes;
    private TipoReacaoAlergicaEnum tipoUltimaReacao;
    private SeveridadeAlergiaEnum severidadeUltimaReacao;
    private String reacaoMaisGrave;
    private String tratamentoUtilizado;
    private Boolean necessitouHospitalizacao;
}
