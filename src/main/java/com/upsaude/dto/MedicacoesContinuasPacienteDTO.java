package com.upsaude.dto;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MedicacoesContinuasPacienteDTO {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteDTO paciente;
    private MedicacoesContinuasDTO medicacaoContinua;
    private String dosagemAtual;
    private String frequenciaAdministracao;
    private LocalDate dataInicio;
    private String observacoes;
}
