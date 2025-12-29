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
public class DadosIdentificacaoUsuario {

    @Column(name = "username", length = 100, unique = true)
    private String username;

    @Column(name = "cpf", length = 11, unique = true)
    private String cpf;
}

