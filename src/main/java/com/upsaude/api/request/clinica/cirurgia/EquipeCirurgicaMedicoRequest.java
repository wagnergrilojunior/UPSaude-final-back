package com.upsaude.api.request.clinica.cirurgia;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de médico na equipe cirúrgica")
public class EquipeCirurgicaMedicoRequest {
    @NotNull(message = "Médico é obrigatório")
    @Schema(description = "ID do médico")
    private UUID medico;

    @Size(max = 100, message = "Função deve ter no máximo 100 caracteres")
    @Schema(description = "Função do médico na equipe")
    private String funcao;

    @Schema(description = "Observações sobre o médico na equipe")
    private String observacoes;
}

