package com.upsaude.api.request.paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.CondicaoMoradiaEnum;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SituacaoFamiliarEnum;
import com.upsaude.util.converter.CondicaoMoradiaEnumDeserializer;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.SituacaoFamiliarEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@Schema(description = "Dados de dados sociodemográficos")
public class DadosSociodemograficosRequest {
    private UUID paciente;

    @JsonDeserialize(using = RacaCorEnumDeserializer.class)
    private RacaCorEnum racaCor;

    @JsonDeserialize(using = NacionalidadeEnumDeserializer.class)
    private NacionalidadeEnum nacionalidade;

    private String paisNascimento;
    private String naturalidade;
    private String municipioNascimentoIbge;

    @JsonDeserialize(using = EscolaridadeEnumDeserializer.class)
    private EscolaridadeEnum escolaridade;

    private String ocupacaoProfissao;
    private Boolean situacaoRua;
    private Integer tempoSituacaoRua;

    @JsonDeserialize(using = CondicaoMoradiaEnumDeserializer.class)
    private CondicaoMoradiaEnum condicaoMoradia;

    @JsonDeserialize(using = SituacaoFamiliarEnumDeserializer.class)
    private SituacaoFamiliarEnum situacaoFamiliar;
}
