package com.upsaude.dto.clinica.cirurgia;

import com.upsaude.enums.StatusCirurgiaEnum;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.convenio.ConvenioDTO;
import com.upsaude.dto.profissional.EspecialidadesMedicasDTO;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CirurgiaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO cirurgiaoPrincipal;
    private MedicosDTO medicoCirurgiao;
    private EspecialidadesMedicasDTO especialidade;
    private ConvenioDTO convenio;
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
