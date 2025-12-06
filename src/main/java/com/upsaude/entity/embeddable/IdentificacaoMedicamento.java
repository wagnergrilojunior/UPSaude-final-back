package com.upsaude.entity.embeddable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * Classe embeddable para identificação do medicamento.
 *
 * @author UPSaúde
 */
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
        this.codigoSigtap = "";
    }

    @NotBlank(message = "Princípio ativo é obrigatório")
    @Size(max = 255, message = "Princípio ativo deve ter no máximo 255 caracteres")
    @Column(name = "principio_ativo", nullable = false, length = 255)
    private String principioAtivo;

    @NotBlank(message = "Nome comercial é obrigatório")
    @Size(max = 255, message = "Nome comercial deve ter no máximo 255 caracteres")
    @Column(name = "nome_comercial", nullable = false, length = 255)
    private String nomeComercial;

    @Size(max = 255, message = "Nome genérico deve ter no máximo 255 caracteres")
    @Column(name = "nome_generico", length = 255)
    private String nomeGenerico; // Nome genérico do medicamento

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno; // Código interno do sistema

    @Size(max = 20, message = "Código CATMAT deve ter no máximo 20 caracteres")
    @Column(name = "catmat_codigo", length = 20)
    private String catmatCodigo; // Código CATMAT do Ministério da Saúde

    @Size(max = 50, message = "Código ANVISA deve ter no máximo 50 caracteres")
    @Column(name = "codigo_anvisa", length = 50)
    private String codigoAnvisa; // Código de registro na ANVISA

    @Size(max = 50, message = "Código TUSS deve ter no máximo 50 caracteres")
    @Column(name = "codigo_tuss", length = 50)
    private String codigoTuss; // Código TUSS (Terminologia Unificada da Saúde Suplementar)

    @Size(max = 50, message = "Código SIGTAP deve ter no máximo 50 caracteres")
    @Column(name = "codigo_sigtap", length = 50)
    private String codigoSigtap; // Código SIGTAP (Sistema de Gerenciamento da Tabela de Procedimentos)
}

