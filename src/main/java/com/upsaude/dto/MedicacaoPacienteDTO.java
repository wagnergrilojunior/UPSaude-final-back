package com.upsaude.dto;

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
public class MedicacaoPacienteDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private MedicacaoDTO medicacao;
    private String dose;
    private FrequenciaMedicacaoEnum frequencia;
    private ViaAdministracaoEnum via;
    private CidDoencasDTO cidRelacionado;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private String observacoes;
}
