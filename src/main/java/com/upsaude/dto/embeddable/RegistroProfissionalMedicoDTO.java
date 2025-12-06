package com.upsaude.dto.embeddable;

import com.upsaude.enums.StatusRegistroMedicoEnum;
import java.time.LocalDate;
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
public class RegistroProfissionalMedicoDTO {
    private String crm;
    private String crmUf;
    private StatusRegistroMedicoEnum statusCrm;
    private LocalDate dataEmissaoCrm;
    private LocalDate dataValidadeCrm;
    private String crmComplementar;
    private String observacoesCrm;
}
