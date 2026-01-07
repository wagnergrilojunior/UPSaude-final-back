package com.upsaude.api.response.clinica.cirurgia;

import com.upsaude.api.response.clinica.atendimento.ProfissionalAtendimentoResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EquipeCirurgicaProfissionalResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private UUID tenantId;
    private UUID estabelecimentoId;
    private ProfissionalAtendimentoResponse profissional;
    private String funcao;
    private String observacoes;
}

