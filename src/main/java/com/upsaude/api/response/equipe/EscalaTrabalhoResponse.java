package com.upsaude.api.response.equipe;
import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscalaTrabalhoResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeResponse profissional;
    private MedicosResponse medico;
    private LocalDate dataInicio;
    private LocalDate dataFim;
    private DayOfWeek diaSemana;
    private LocalTime horaEntrada;
    private LocalTime horaSaida;
    private LocalTime intervaloInicio;
    private LocalTime intervaloFim;
    private Integer cargaHorariaDiaria;
    private String observacoes;
}
