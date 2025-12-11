package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InformacoesFinanceirasConvenioRequest {
    private LocalDate dataInicioContrato;
    private LocalDate dataFimContrato;
    private LocalDate dataRenovacaoContrato;
    private Integer prazoContratoMeses;
    private BigDecimal valorContratoMensal;
    private BigDecimal valorContratoAnual;
    private BigDecimal percentualDesconto;
    private BigDecimal percentualCoparticipacao;
    private BigDecimal franquiaAnual;
    private Integer carenciaGeralDias;
    private Integer carenciaPartosDias;
    private Integer carenciaCirurgiasDias;
    private Integer carenciaExamesDias;
    private Integer limiteAnualConsultas;
    private Integer limiteAnualExames;
    private Integer limiteAnualInternacoes;

    @Size(max = 50, message = "Forma de pagamento deve ter no máximo 50 caracteres")
    private String formaPagamento;

    @Size(max = 50, message = "Dia de vencimento deve ter no máximo 50 caracteres")
    private String diaVencimento;

    @Size(max = 255, message = "Observações financeiras deve ter no máximo 255 caracteres")
    private String observacoesFinanceiras;
}
