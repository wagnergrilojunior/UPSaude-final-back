package com.upsaude.api.response.embeddable;

import com.upsaude.enums.StatusFuncionamentoEnum;
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
public class LicenciamentoEstabelecimentoResponse {

    private StatusFuncionamentoEnum statusFuncionamento;

    private OffsetDateTime dataValidadeLicenca;

    private String numeroAlvara;

    private String numeroLicencaSanitaria;
}
