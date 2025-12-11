package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.SeveridadeAlergiaEnum;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import com.upsaude.util.converter.SeveridadeAlergiaEnumDeserializer;
import com.upsaude.util.converter.TipoReacaoAlergicaEnumDeserializer;
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
    @JsonDeserialize(using = TipoReacaoAlergicaEnumDeserializer.class)
    private TipoReacaoAlergicaEnum tipoUltimaReacao;

    @JsonDeserialize(using = SeveridadeAlergiaEnumDeserializer.class)
    private SeveridadeAlergiaEnum severidadeUltimaReacao;
    private String reacaoMaisGrave;
    private String tratamentoUtilizado;

    @NotNull(message = "Necessitou hospitalização é obrigatório")
    @Builder.Default
    private Boolean necessitouHospitalizacao = false;
}
