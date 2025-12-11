package com.upsaude.api.request.embeddable;

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
public class ComposicaoVacinaRequest {
    private String composicao;

    @Size(max = 100, message = "Tecnologia deve ter no máximo 100 caracteres")
    private String tecnologia;

    @Size(max = 100, message = "Adjuvante deve ter no máximo 100 caracteres")
    private String adjuvante;

    @Size(max = 100, message = "Conservante deve ter no máximo 100 caracteres")
    private String conservante;
}
