package com.upsaude.api.response;

import com.upsaude.enums.StatusReceitaEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReceitasMedicasResponse {
    private UUID id;
    private UUID estabelecimentoId;
    private String estabelecimentoNome;
    private UUID medicoId;
    private String medicoNome;
    private UUID pacienteId;
    private String pacienteNome;
    private String numeroReceita;
    private OffsetDateTime dataPrescricao;
    private OffsetDateTime dataValidade;
    private Boolean usoContinuo;
    private String observacoes;
    private StatusReceitaEnum status;
    private String origemReceita;
    private UUID cidPrincipalId;
    private String cidPrincipalCodigo;
    private String cidPrincipalDescricao;
    private List<UUID> medicacoesIds;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
}

