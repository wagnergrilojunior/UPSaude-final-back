package com.upsaude.api.response;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
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
public class EquipeCirurgicaResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private CirurgiaResponse cirurgia;
    private ProfissionaisSaudeResponse profissional;
    private MedicosResponse medico;
    private String funcao;
    private Boolean ehPrincipal;
    private BigDecimal valorParticipacao;
    private BigDecimal percentualParticipacao;
    private OffsetDateTime dataHoraEntrada;
    private OffsetDateTime dataHoraSaida;
    private String observacoes;
}
