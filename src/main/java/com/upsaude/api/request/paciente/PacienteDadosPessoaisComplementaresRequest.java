package com.upsaude.api.request.paciente;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.util.converter.IdentidadeGeneroEnumDeserializer;
import com.upsaude.util.converter.OrientacaoSexualEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Dados pessoais complementares do paciente")
public class PacienteDadosPessoaisComplementaresRequest {

    private UUID paciente;

    @Size(max = 100, message = "Nome da mãe deve ter no máximo 100 caracteres")
    private String nomeMae;

    @Size(max = 100, message = "Nome do pai deve ter no máximo 100 caracteres")
    private String nomePai;

    @JsonDeserialize(using = IdentidadeGeneroEnumDeserializer.class)
    private IdentidadeGeneroEnum identidadeGenero;

    @JsonDeserialize(using = OrientacaoSexualEnumDeserializer.class)
    private OrientacaoSexualEnum orientacaoSexual;
}

