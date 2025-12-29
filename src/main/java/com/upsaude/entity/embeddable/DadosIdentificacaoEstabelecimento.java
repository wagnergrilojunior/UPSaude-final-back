package com.upsaude.entity.embeddable;

import com.upsaude.enums.NaturezaJuridicaEnum;
import com.upsaude.enums.TipoEstabelecimentoEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosIdentificacaoEstabelecimento {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 100)
    private TipoEstabelecimentoEnum tipo;

    @Column(name = "cnes", length = 7)
    private String cnes;

    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Enumerated(EnumType.STRING)
    @Column(name = "natureza_juridica", length = 50)
    private NaturezaJuridicaEnum naturezaJuridica;

}

