package com.upsaude.api.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class EquipeCirurgicaRequest {
    @NotNull(message = "Cirurgia é obrigatória")
    private UUID cirurgia;

    @NotNull(message = "Profissional é obrigatório")
    private UUID profissional;

    private UUID medico;

    @NotBlank(message = "Função na cirurgia é obrigatória")
    @Size(max = 100, message = "Função deve ter no máximo 100 caracteres")
    private String funcao;
    private Boolean ehPrincipal;
    private BigDecimal valorParticipacao;
    private BigDecimal percentualParticipacao;
    private OffsetDateTime dataHoraEntrada;
    private OffsetDateTime dataHoraSaida;
    private String observacoes;
}
