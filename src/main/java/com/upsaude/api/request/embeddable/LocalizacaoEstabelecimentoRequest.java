package com.upsaude.api.request.embeddable;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Dados de localização do estabelecimento")
public class LocalizacaoEstabelecimentoRequest {

    private Double latitude;

    private Double longitude;
}
