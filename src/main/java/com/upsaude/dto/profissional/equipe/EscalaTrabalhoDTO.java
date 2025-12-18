package com.upsaude.dto.profissional.equipe;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import com.upsaude.dto.profissional.MedicosDTO;
import com.upsaude.dto.profissional.ProfissionaisSaudeDTO;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EscalaTrabalhoDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private ProfissionaisSaudeDTO profissional;
    private MedicosDTO medico;
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
