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
@Schema(description = "Dados fiscais do tenant")
public class DadosFiscaisTenantRequest {

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    private String inscricaoMunicipal;
}
