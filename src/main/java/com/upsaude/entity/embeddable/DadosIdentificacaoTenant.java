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
public class DadosIdentificacaoTenant {

    @Column(name = "name", nullable = false, length = 255)
    private String nome;

    @Column(name = "description", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "logo_url", columnDefinition = "TEXT")
    private String urlLogo;

    @Column(name = "cnpj", length = 14, unique = true)
    private String cnpj;
}

