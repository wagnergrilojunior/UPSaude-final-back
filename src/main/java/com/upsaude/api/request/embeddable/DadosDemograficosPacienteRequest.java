package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.EscolaridadeEnum;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.NacionalidadeEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.enums.RacaCorEnum;
import com.upsaude.util.converter.EscolaridadeEnumDeserializer;
import com.upsaude.util.converter.IdentidadeGeneroEnumDeserializer;
import com.upsaude.util.converter.NacionalidadeEnumDeserializer;
import com.upsaude.util.converter.OrientacaoSexualEnumDeserializer;
import com.upsaude.util.converter.RacaCorEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
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
@Schema(description = "Dados demográficos do paciente")
public class DadosDemograficosPacienteRequest {

    @JsonDeserialize(using = EscolaridadeEnumDeserializer.class)
    private EscolaridadeEnum escolaridade;

    @JsonDeserialize(using = NacionalidadeEnumDeserializer.class)
    private NacionalidadeEnum nacionalidade;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos na naturalidade")
    @Size(max = 100, message = "Naturalidade deve ter no máximo 100 caracteres")
    private String naturalidade;

    @Pattern(regexp = "^$|^[\\p{L}0-9 .'-]+$", message = "Caracteres inválidos no país de nascimento")
    @Size(max = 100, message = "País de nascimento deve ter no máximo 100 caracteres")
    private String paisNascimento;

    @Size(max = 7, message = "Município de nascimento IBGE deve ter no máximo 7 caracteres")
    private String municipioNascimentoIbge;

    @JsonDeserialize(using = RacaCorEnumDeserializer.class)
    private RacaCorEnum racaCor;

    @Size(max = 150, message = "Ocupação/Profissão deve ter no máximo 150 caracteres")
    private String ocupacaoProfissao;

    private Boolean situacaoRua;

    @JsonDeserialize(using = IdentidadeGeneroEnumDeserializer.class)
    private IdentidadeGeneroEnum identidadeGenero;

    @JsonDeserialize(using = OrientacaoSexualEnumDeserializer.class)
    private OrientacaoSexualEnum orientacaoSexual;
}

