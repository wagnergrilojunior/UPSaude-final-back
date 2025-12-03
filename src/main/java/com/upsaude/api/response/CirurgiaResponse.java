package com.upsaude.api.response;

import com.upsaude.enums.StatusCirurgiaEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CirurgiaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse cirurgiaoPrincipal;
    private MedicosResponse medicoCirurgiao;
    private EspecialidadesMedicasResponse especialidade;
    private ConvenioResponse convenio;
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
