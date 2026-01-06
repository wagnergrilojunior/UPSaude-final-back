package com.upsaude.api.response.embeddable;

import com.upsaude.enums.TipoCnsEnum;
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
public class IntegracaoGovPacienteResponse {
    private Boolean cartaoSusAtivo;
    private LocalDate dataAtualizacaoCns;
    private String origemCadastro;
    private Boolean cnsValidado;
    private TipoCnsEnum tipoCns;
}

