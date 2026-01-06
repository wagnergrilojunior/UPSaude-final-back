package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusFuncionamentoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LicenciamentoEstabelecimento {

    @Enumerated(EnumType.STRING)
    @Column(name = "status_funcionamento", length = 50)
    private StatusFuncionamentoEnum statusFuncionamento;

    @Column(name = "data_validade_licenca")
    private OffsetDateTime dataValidadeLicenca;

    @Column(name = "numero_alvara", length = 100)
    private String numeroAlvara;

    @Column(name = "numero_licenca_sanitaria", length = 100)
    private String numeroLicencaSanitaria;
}
