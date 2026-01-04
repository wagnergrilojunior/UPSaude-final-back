package com.upsaude.entity.embeddable;

import com.upsaude.enums.StatusRegistroMedicoEnum;
import com.upsaude.util.converter.StatusRegistroMedicoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;
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

    @Column(name = "crm", nullable = false, length = 20)
    private String crm;

    @Column(name = "crm_uf", length = 2)
    private String crmUf;

    @Convert(converter = StatusRegistroMedicoEnumConverter.class)
    @Column(name = "status_crm")
    private StatusRegistroMedicoEnum statusCrm;

    @Column(name = "data_emissao_crm")
    private LocalDate dataEmissaoCrm;

    @Column(name = "data_validade_crm")
    private LocalDate dataValidadeCrm;

    @Column(name = "crm_complementar", length = 50)
    private String crmComplementar;

    @Column(name = "observacoes_crm", length = 255)
    private String observacoesCrm;
}
