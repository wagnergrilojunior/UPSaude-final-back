package com.upsaude.dto.clinica.medicacao;

import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.paciente.PacienteDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DispensacoesMedicamentosDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private MedicacaoDTO medicacao;
    private Integer quantidade;
    private OffsetDateTime dataDispensacao;
    private String observacoes;
}
