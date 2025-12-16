package com.upsaude.api.request.embeddable;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de conservacao vacina")
public class ConservacaoVacinaRequest {
    private BigDecimal temperaturaConservacaoMin;
    private BigDecimal temperaturaConservacaoMax;

    @Size(max = 50, message = "Tipo conservação deve ter no máximo 50 caracteres")
    private String tipoConservacao;

    @NotNull(message = "Proteger luz é obrigatório")
    @Builder.Default
    private Boolean protegerLuz = false;

    @NotNull(message = "Agitar antes uso é obrigatório")
    @Builder.Default
    private Boolean agitarAntesUso = false;

    private Integer validadeAposAberturaHoras;
    private Integer validadeAposReconstituicaoHoras;
}
