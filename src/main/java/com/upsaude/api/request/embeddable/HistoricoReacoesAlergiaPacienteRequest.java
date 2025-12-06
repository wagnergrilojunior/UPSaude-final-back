package com.upsaude.api.request.embeddable;

import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import jakarta.validation.constraints.NotNull;
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
public class HistoricoReacoesAlergiaPacienteRequest {
    private LocalDate dataUltimaReacao;
    private Integer numeroReacoes;
    private TipoReacaoAlergicaEnum tipoUltimaReacao;
    private SeveridadeAlergiaEnum severidadeUltimaReacao;
    private String reacaoMaisGrave;
    private String tratamentoUtilizado;
    
    @NotNull(message = "Necessitou hospitalização é obrigatório")
    @Builder.Default
    private Boolean necessitouHospitalizacao = false;
}
