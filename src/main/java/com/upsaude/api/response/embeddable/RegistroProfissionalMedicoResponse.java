package com.upsaude.api.response.embeddable;

import com.upsaude.enums.StatusRegistroMedicoEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistroProfissionalMedicoResponse {
    private String crm;
    private String crmUf;
    private StatusRegistroMedicoEnum statusCrm;
    private LocalDate dataEmissaoCrm;
    private LocalDate dataValidadeCrm;
    private String crmComplementar;
    private String observacoesCrm;
}
