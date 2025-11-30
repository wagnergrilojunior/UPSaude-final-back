package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "cid_doencas", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class CidDoencas extends BaseEntityWithoutTenant {

    @Column(name = "codigo", nullable = false, length = 10, unique = true)
    @NotBlank(message = "Código CID é obrigatório")
    @Pattern(regexp = "^[A-Z]\\d{2}(\\.\\d{1,2})?$", message = "Código CID deve seguir o formato A99 ou A99.99")
    @Size(max = 10, message = "Código CID deve ter no máximo 10 caracteres")
    private String codigo;

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotBlank(message = "Descrição é obrigatória")
    @Size(max = 1000, message = "Descrição deve ter no máximo 1000 caracteres")
    private String descricao;

    @Column(name = "descricao_abreviada", length = 255)
    @Size(max = 255, message = "Descrição abreviada deve ter no máximo 255 caracteres")
    private String descricaoAbreviada;

    @Column(name = "categoria", length = 100)
    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    private String categoria;

    @Column(name = "subcategoria", length = 100)
    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    private String subcategoria;

    @Column(name = "sexo_restricao", length = 1)
    @Pattern(regexp = "^[MF]?$", message = "Restrição de sexo deve ser M, F ou vazio")
    private String sexoRestricao;

    @Column(name = "idade_minima")
    private Integer idadeMinima;

    @Column(name = "idade_maxima")
    private Integer idadeMaxima;

    
}
