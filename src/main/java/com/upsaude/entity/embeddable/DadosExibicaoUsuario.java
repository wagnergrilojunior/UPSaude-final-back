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
public class DadosExibicaoUsuario {

    @Column(name = "nome_exibicao", length = 255)
    private String nomeExibicao;

    @Column(name = "foto_url", columnDefinition = "TEXT")
    private String fotoUrl;
}
