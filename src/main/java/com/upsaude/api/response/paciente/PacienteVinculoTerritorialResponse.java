package com.upsaude.api.response.paciente;

import com.upsaude.enums.OrigemVinculoTerritorialEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PacienteVinculoTerritorialResponse {

    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;

    private String municipioIbge;
    private String cnesEstabelecimento;
    private String ineEquipe;
    private String microarea;
    private LocalDate dataInicio;
    private LocalDate dataFim;

    private OrigemVinculoTerritorialEnum origem;

    private String observacoes;
}
