package com.upsaude.dto.odontologia;

import com.upsaude.entity.odontologia.TratamentosOdontologicos.StatusTratamento;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.estabelecimento.EstabelecimentosDTO;
import com.upsaude.dto.paciente.PacienteDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamentosOdontologicosDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosDTO estabelecimento;
    private PacienteDTO paciente;
    private ProfissionaisSaudeDTO profissional;
    private String titulo;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private StatusTratamento status;
    private String observacoes;
}
