package com.upsaude.entity.estabelecimento.equipamento;
import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fabricantes_equipamento", schema = "public",
       indexes = {
           @Index(name = "idx_fabricante_equipamento_nome", columnList = "nome"),
           @Index(name = "idx_fabricante_equipamento_cnpj", columnList = "cnpj")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class FabricantesEquipamento extends BaseEntityWithoutTenant {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cnpj", length = 18)
    private String cnpj;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "estado", length = 100)
    private String estado;

    @Column(name = "cidade", length = 100)
    private String cidade;

    @Column(name = "endereco", length = 255)
    private String endereco;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "site", length = 255)
    private String site;

    @Column(name = "registro_anvisa", length = 50)
    private String registroAnvisa;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
