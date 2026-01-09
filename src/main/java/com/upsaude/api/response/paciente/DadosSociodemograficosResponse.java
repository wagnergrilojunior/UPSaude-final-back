package com.upsaude.api.response.paciente;

import com.upsaude.api.response.paciente.PacienteResponse;

import com.upsaude.enums.CondicaoMoradiaEnum;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.enums.SituacaoFamiliarEnum;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.upsaude.util.converter.RacaCorEnumSerializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import com.upsaude.util.converter.NacionalidadeEnumSerializer;
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.EscolaridadeEnumSerializer;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.CondicaoMoradiaEnumSerializer;
import com.upsaude.util.converter.CondicaoMoradiaEnumDeserializer;
import com.upsaude.util.converter.SituacaoFamiliarEnumSerializer;
import com.upsaude.util.converter.SituacaoFamiliarEnumDeserializer;
import java.time.OffsetDateTime;
import java.util.UUID;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosSociodemograficosResponse {
    private UUID id;
    private OffsetDateTime createdAt;
    private OffsetDateTime updatedAt;
    private Boolean active;
    private PacienteResponse paciente;
    @JsonSerialize(using = RacaCorEnumSerializer.class)
    @JsonDeserialize(using = RacaCorEnumDeserializer.class)
    private RacaCorEnum racaCor;

    @JsonSerialize(using = NacionalidadeEnumSerializer.class)
    @JsonDeserialize(using = NacionalidadeEnumDeserializer.class)
    private NacionalidadeEnum nacionalidade;

    private String paisNascimento;
    private String naturalidade;
    private String municipioNascimentoIbge;

    @JsonSerialize(using = EscolaridadeEnumSerializer.class)
    @JsonDeserialize(using = EscolaridadeEnumDeserializer.class)
    private EscolaridadeEnum escolaridade;

    private String ocupacaoProfissao;
    private Boolean situacaoRua;
    private Integer tempoSituacaoRua;
    @JsonSerialize(using = CondicaoMoradiaEnumSerializer.class)
    @JsonDeserialize(using = CondicaoMoradiaEnumDeserializer.class)
    private CondicaoMoradiaEnum condicaoMoradia;

    @JsonSerialize(using = SituacaoFamiliarEnumSerializer.class)
    @JsonDeserialize(using = SituacaoFamiliarEnumDeserializer.class)
    private SituacaoFamiliarEnum situacaoFamiliar;
}
