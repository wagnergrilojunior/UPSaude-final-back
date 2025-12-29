package com.upsaude.api.request.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Configuração do usuário")
public class ConfiguracaoUsuarioRequest {

    private Boolean adminTenant;

    @Size(max = 50, message = "Tipo vínculo deve ter no máximo 50 caracteres")
    private String tipoVinculo;
}

