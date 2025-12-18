package com.upsaude.api.response.medicacao;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacoesContinuasPacienteResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    private MedicacoesContinuasResponse medicacaoContinua;
    private String dosagemAtual;
    private String frequenciaAdministracao;
    private LocalDate dataInicio;
    private String observacoes;
}
