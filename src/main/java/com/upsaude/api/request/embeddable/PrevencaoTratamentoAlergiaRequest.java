package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
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
public class PrevencaoTratamentoAlergiaRequest {
    private String medidasPrevencao;

    private String tratamentoImediato;

    private String medicamentosEvitar;

    private String alimentosEvitar;

    private String substanciasEvitar;

    @NotNull(message = "Epinefrina necessária é obrigatória")
    @Builder.Default
    private Boolean epinefrinaNecessaria = false;

    @Size(max = 255, message = "Antihistamínico recomendado deve ter no máximo 255 caracteres")
    private String antihistaminicoRecomendado;
}
