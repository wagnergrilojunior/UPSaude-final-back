package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.TipoReacaoAlergicaEnum;
import com.upsaude.util.converter.TipoReacaoAlergicaEnumDeserializer;
import jakarta.validation.constraints.Size;
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
@Schema(description = "Dados de reacoes alergia")
public class ReacoesAlergiaRequest {
    @JsonDeserialize(using = TipoReacaoAlergicaEnumDeserializer.class)
    private TipoReacaoAlergicaEnum tipoReacaoPrincipal;

    private String reacoesComuns;

    private String reacoesGraves;

    private String sintomas;

    @Size(max = 100, message = "Tempo após exposição deve ter no máximo 100 caracteres")
    private String tempoAposExposicao;
}
