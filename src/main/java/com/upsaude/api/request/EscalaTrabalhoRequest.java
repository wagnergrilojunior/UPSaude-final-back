package com.upsaude.api.request;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de escala trabalho")
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
