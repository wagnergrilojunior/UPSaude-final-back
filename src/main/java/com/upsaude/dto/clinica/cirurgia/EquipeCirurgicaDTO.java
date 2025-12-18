package com.upsaude.dto.clinica.cirurgia;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeCirurgicaDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private CirurgiaDTO cirurgia;
    private ProfissionaisSaudeDTO profissional;
    private MedicosDTO medico;
    private String funcao;
    private Boolean ehPrincipal;
    private BigDecimal valorParticipacao;
    private BigDecimal percentualParticipacao;
    private OffsetDateTime dataHoraEntrada;
    private OffsetDateTime dataHoraSaida;
    private String observacoes;
}
