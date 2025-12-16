package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de contraindicacoes vacina")
public class ContraindicacoesVacinaRequest {
    private String contraindicacoes;
    private String precaucoes;

    @NotNull(message = "Gestante pode é obrigatório")
    @Builder.Default
    private Boolean gestantePode = false;

    @NotNull(message = "Lactante pode é obrigatório")
    @Builder.Default
    private Boolean lactantePode = false;

    @NotNull(message = "Imunocomprometido pode é obrigatório")
    @Builder.Default
    private Boolean imunocomprometidoPode = false;
}
