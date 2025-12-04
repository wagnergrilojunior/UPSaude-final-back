package com.upsaude.api.response;

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
public class MedicacaoPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private MedicacaoResponse medicacao;
    private String dose;
    private FrequenciaMedicacaoEnum frequencia;
    private ViaAdministracaoEnum via;
    private CidDoencasResponse cidRelacionado;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private Boolean medicacaoAtiva;
    private String observacoes;
}
