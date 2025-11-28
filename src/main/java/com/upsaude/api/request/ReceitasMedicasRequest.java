package com.upsaude.api.request;

import com.upsaude.enums.StatusReceitaEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
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
public class ReceitasMedicasRequest {

    @NotNull(message = "ID do estabelecimento é obrigatório")
    private UUID estabelecimentoId;

    @NotNull(message = "ID do médico é obrigatório")
    private UUID medicoId;

    @NotNull(message = "ID do paciente é obrigatório")
    private UUID pacienteId;

    @NotBlank(message = "Número da receita é obrigatório")
    @Size(max = 50, message = "Número da receita deve ter no máximo 50 caracteres")
    private String numeroReceita;

    @NotNull(message = "Data de prescrição é obrigatória")
    private OffsetDateTime dataPrescricao;

    @NotNull(message = "Data de validade é obrigatória")
    private OffsetDateTime dataValidade;

    @NotNull(message = "Indicação de uso contínuo é obrigatória")
    private Boolean usoContinuo;

    @Size(max = 1000, message = "Observações devem ter no máximo 1000 caracteres")
    private String observacoes;

    @NotNull(message = "Status é obrigatório")
    private StatusReceitaEnum status;

    @Size(max = 50, message = "Origem da receita deve ter no máximo 50 caracteres")
    private String origemReceita;

    private UUID cidPrincipalId;

    private List<UUID> medicacoesIds;
}

