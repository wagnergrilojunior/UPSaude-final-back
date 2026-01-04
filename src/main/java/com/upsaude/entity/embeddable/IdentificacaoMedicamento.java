package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Embeddable
@Data
@Builder
@AllArgsConstructor
public class IdentificacaoMedicamento {

    public IdentificacaoMedicamento() {
        this.principioAtivo = "";
        this.nomeComercial = "";
        this.nomeGenerico = "";
        this.codigoInterno = "";
        this.catmatCodigo = "";
        this.codigoAnvisa = "";
        this.codigoTuss = "";
    }

    @Column(name = "principio_ativo", nullable = false, length = 255)
    private String principioAtivo;

    @Column(name = "nome_comercial", nullable = false, length = 255)
    private String nomeComercial;

    @Column(name = "nome_generico", length = 255)
    private String nomeGenerico;

    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Column(name = "catmat_codigo", length = 20)
    private String catmatCodigo;

    @Column(name = "codigo_anvisa", length = 50)
    private String codigoAnvisa;

    @Column(name = "codigo_tuss", length = 50)
    private String codigoTuss;
}
