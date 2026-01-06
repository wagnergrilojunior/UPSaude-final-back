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
public class ResponsavelTenant {

    @Column(name = "responsavel_nome", length = 255)
    private String responsavelNome;

    @Column(name = "responsavel_cpf", length = 11)
    private String responsavelCpf;

    @Column(name = "responsavel_telefone", length = 20)
    private String responsavelTelefone;
}
