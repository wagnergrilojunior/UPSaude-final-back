package com.upsaude.entity.estabelecimento.equipamento;
import com.upsaude.entity.BaseEntityWithoutTenant;
import com.upsaude.entity.paciente.Endereco;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "fabricantes_equipamento", schema = "public",
       indexes = {
           @Index(name = "idx_fabricante_equipamento_nome", columnList = "nome"),
           @Index(name = "idx_fabricante_equipamento_cnpj", columnList = "cnpj"),
           @Index(name = "idx_fabricante_equipamento_endereco", columnList = "endereco_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class FabricantesEquipamento extends BaseEntityWithoutTenant {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cnpj", length = 18)
    private String cnpj;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "site", length = 255)
    private String site;

}
