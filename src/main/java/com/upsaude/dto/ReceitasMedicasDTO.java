package com.upsaude.dto;

import com.upsaude.enums.StatusReceitaEnum;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitasMedicasDTO {
    private UUID id;
    private UUID estabelecimentoId;
    private UUID medicoId;
    private UUID pacienteId;
    private String numeroReceita;
    private OffsetDateTime dataPrescricao;
    private OffsetDateTime dataValidade;
    private Boolean usoContinuo;
    private String observacoes;
    private StatusReceitaEnum status;
    private String origemReceita;
    private UUID cidPrincipalId;
    private List<UUID> medicacoesIds;
    private Boolean active;
}

