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
public class HistoricoReacoesAlergiaPacienteDTO {
    private LocalDate dataUltimaReacao;
    private Integer numeroReacoes;
    private TipoReacaoAlergicaEnum tipoUltimaReacao;
    private SeveridadeAlergiaEnum severidadeUltimaReacao;
    private String reacaoMaisGrave;
    private String tratamentoUtilizado;
    private Boolean necessitouHospitalizacao;
}
