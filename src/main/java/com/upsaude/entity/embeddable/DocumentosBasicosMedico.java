package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DocumentosBasicosMedico {

    @Column(name = "cpf", length = 11)
    private String cpf;

    @Column(name = "rg", length = 20)
    private String rg;

    @Column(name = "orgao_emissor_rg", length = 10)
    private String orgaoEmissorRg;

    @Column(name = "uf_emissor_rg", length = 2)
    private String ufEmissorRg;
}
