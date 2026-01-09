package com.upsaude.api.request.embeddable;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.upsaude.enums.StatusFuncionamentoEnum;
import com.upsaude.util.converter.StatusFuncionamentoEnumDeserializer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.OffsetDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados de licenciamento do estabelecimento")
public class LicenciamentoEstabelecimentoRequest {

    @JsonDeserialize(using = StatusFuncionamentoEnumDeserializer.class)
    private StatusFuncionamentoEnum statusFuncionamento;

    private OffsetDateTime dataValidadeLicenca;

    @Size(max = 100, message = "Número do alvará deve ter no máximo 100 caracteres")
    private String numeroAlvara;

    @Size(max = 100, message = "Número da licença sanitária deve ter no máximo 100 caracteres")
    private String numeroLicencaSanitaria;
}
