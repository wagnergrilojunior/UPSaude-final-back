package com.upsaude.api.request;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacoesContinuasPacienteRequest {
    private UUID paciente;
    private UUID medicacaoContinua;
    private String dosagemAtual;
    private String frequenciaAdministracao;
    private LocalDate dataInicio;
    private String observacoes;
}
