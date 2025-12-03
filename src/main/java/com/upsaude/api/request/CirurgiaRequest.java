package com.upsaude.api.request;

import com.upsaude.enums.StatusCirurgiaEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CirurgiaRequest {
    private UUID paciente;
    private UUID cirurgiaoPrincipal;
    private UUID medicoCirurgiao;
    private UUID especialidade;
    private UUID convenio;
    private String descricao;
    private String codigoProcedimento;
    private OffsetDateTime dataHoraPrevista;
    private OffsetDateTime dataHoraInicio;
    private OffsetDateTime dataHoraFim;
    private Integer duracaoPrevistaMinutos;
    private Integer duracaoRealMinutos;
    private String salaCirurgica;
    private String leitoCentroCirurgico;
    private StatusCirurgiaEnum status;
    private BigDecimal valorCirurgia;
    private BigDecimal valorMaterial;
    private BigDecimal valorTotal;
    private String observacoesPreOperatorio;
    private String observacoesPosOperatorio;
    private String observacoes;
    private String observacoesInternas;
}
