package com.upsaude.api.request.clinica.cirurgia;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
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
    @Schema(description = "ID da cirurgia (opcional durante criação, será definido automaticamente)")
    private UUID cirurgia;

    @Builder.Default
    @Schema(description = "Lista de profissionais da equipe")
    private List<EquipeCirurgicaProfissionalRequest> profissionais = new ArrayList<>();

    @Builder.Default
    @Schema(description = "Lista de médicos da equipe")
    private List<EquipeCirurgicaMedicoRequest> medicos = new ArrayList<>();

    private Boolean ehPrincipal;
    private BigDecimal valorParticipacao;
    private BigDecimal percentualParticipacao;
    private OffsetDateTime dataHoraEntrada;
    private OffsetDateTime dataHoraSaida;

    @Size(max = 5000, message = "Observações deve ter no máximo 5000 caracteres")
    private String observacoes;
}
