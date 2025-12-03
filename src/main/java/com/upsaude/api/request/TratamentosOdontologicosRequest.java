package com.upsaude.api.request;

import com.upsaude.entity.TratamentosOdontologicos.StatusTratamento;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TratamentosOdontologicosRequest {
    private UUID paciente;
    private UUID profissional;
    private String titulo;
    private String descricao;
    private OffsetDateTime dataInicio;
    private OffsetDateTime dataFim;
    private StatusTratamento status;
    private String observacoes;
}
