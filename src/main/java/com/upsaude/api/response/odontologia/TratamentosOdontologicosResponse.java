package com.upsaude.api.response.odontologia;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.entity.TratamentosOdontologicos.StatusTratamento;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
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
public class TratamentosOdontologicosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private EstabelecimentosResponse estabelecimento;
    private PacienteResponse paciente;
    private ProfissionaisSaudeResponse profissional;
    private String titulo;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private StatusTratamento status;

    private String observacoes;
}
