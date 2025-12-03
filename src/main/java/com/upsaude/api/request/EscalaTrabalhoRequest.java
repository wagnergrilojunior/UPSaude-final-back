package com.upsaude.api.request;

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
public class EscalaTrabalhoRequest {
    private UUID profissional;
    private UUID medico;
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
