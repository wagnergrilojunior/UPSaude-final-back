package com.upsaude.api.request.profissional;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Request para adicionar especialidade (CBO) a um médico")
public class AdicionarEspecialidadeRequest {
    
    @NotBlank(message = "Código CBO é obrigatório")
    @Pattern(regexp = "^\\d{6}$", message = "Código CBO deve ter 6 dígitos")
    @Size(min = 6, max = 6, message = "Código CBO deve ter exatamente 6 dígitos")
    @Schema(description = "Código CBO da especialidade", example = "225110", required = true)
    private String codigoCbo;
}

