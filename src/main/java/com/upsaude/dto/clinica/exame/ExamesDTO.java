package com.upsaude.dto.clinica.exame;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.clinica.atendimento.AtendimentoDTO;
import com.upsaude.dto.clinica.atendimento.ConsultasDTO;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamesDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private CatalogoExamesDTO catalogoExame;
    private AtendimentoDTO atendimento;
    private ConsultasDTO consulta;
    private ProfissionaisSaudeDTO profissionalSolicitante;
    private MedicosDTO medicoSolicitante;
    private String tipoExame;
    private String nomeExame;
    private OffsetDateTime dataSolicitacao;
    private OffsetDateTime dataExame;
    private OffsetDateTime dataResultado;
    private String unidadeLaboratorio;
    private EstabelecimentosDTO estabelecimentoRealizador;
    private ProfissionaisSaudeDTO profissionalResponsavel;
    private MedicosDTO medicoResponsavel;
    private String resultados;
    private String laudo;
    private String observacoes;
}
