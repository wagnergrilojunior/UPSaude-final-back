package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusRegistroMedicoEnum;
import com.upsaude.util.converter.StatusRegistroMedicoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class RegistroProfissionalMedico {

    public RegistroProfissionalMedico() {
    }

    @Pattern(regexp = "^\\d{4,10}$", message = "CRM deve ter entre 4 e 10 dígitos")
    @Column(name = "crm", nullable = false, length = 20)
    private String crm;

    @Pattern(regexp = "^[A-Z]{2}$", message = "UF do CRM deve ter 2 letras maiúsculas")
    @Column(name = "crm_uf", length = 2)
    private String crmUf;

    @Convert(converter = StatusRegistroMedicoEnumConverter.class)
    @Column(name = "status_crm")
    private StatusRegistroMedicoEnum statusCrm;

    @Column(name = "data_emissao_crm")
    private LocalDate dataEmissaoCrm;

    @Column(name = "data_validade_crm")
    private LocalDate dataValidadeCrm;

    @Size(max = 50, message = "CRM complementar deve ter no máximo 50 caracteres")
    @Column(name = "crm_complementar", length = 50)
    private String crmComplementar;

    @Size(max = 255, message = "Observações CRM deve ter no máximo 255 caracteres")
    @Column(name = "observacoes_crm", length = 255)
    private String observacoesCrm;
}
