package com.upsaude.api.request.cirurgia;

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
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de equipe cirúrgica")
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
