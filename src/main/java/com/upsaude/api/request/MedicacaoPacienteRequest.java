package com.upsaude.api.request;

import com.upsaude.enums.FrequenciaMedicacaoEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacaoPacienteRequest {
    private UUID paciente;
    private UUID medicacao;
    private String dose;
    private FrequenciaMedicacaoEnum frequencia;
    private ViaAdministracaoEnum via;
    private UUID cidRelacionado;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String observacoes;
}
