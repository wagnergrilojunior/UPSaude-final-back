package com.upsaude.entity.embeddable;

import com.upsaude.validation.annotation.CPFValido;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentosBasicosProfissional {

    @NotNull(message = "{validation.cpf.obrigatorio}")
    @CPFValido
    @Column(name = "cpf", length = 11, unique = true, nullable = false)
    private String cpf;

    @Column(name = "rg", length = 20)
    private String rg;

    @Column(name = "orgao_emissor_rg", length = 50)
    private String orgaoEmissorRg;

    @Column(name = "uf_emissao_rg", length = 2)
    private String ufEmissaoRg;

    @Column(name = "data_emissao_rg")
    private LocalDate dataEmissaoRg;

    @Column(name = "cns", length = 15)
    private String cns;
}
