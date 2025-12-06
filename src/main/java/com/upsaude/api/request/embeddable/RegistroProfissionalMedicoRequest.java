package com.upsaude.api.request.embeddable;

import com.upsaude.enums.StatusRegistroMedicoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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
public class RegistroProfissionalMedicoRequest {
    @NotBlank(message = "CRM é obrigatório")
    @Pattern(regexp = "^\\d{4,10}$", message = "CRM deve ter entre 4 e 10 dígitos")
    private String crm;
    
    @Pattern(regexp = "^[A-Z]{2}$", message = "UF do CRM deve ter 2 letras maiúsculas")
    private String crmUf;
    
    private StatusRegistroMedicoEnum statusCrm;
    
    private LocalDate dataEmissaoCrm;
    
    private LocalDate dataValidadeCrm;
    
    @Size(max = 50, message = "CRM complementar deve ter no máximo 50 caracteres")
    private String crmComplementar;
    
    @Size(max = 255, message = "Observações CRM deve ter no máximo 255 caracteres")
    private String observacoesCrm;
}
