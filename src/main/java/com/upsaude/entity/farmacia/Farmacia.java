package com.upsaude.entity.farmacia;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.sistema.integracao.IntegracaoGov;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "farmacias", schema = "public", indexes = {
        @Index(name = "idx_farmacia_nome", columnList = "nome"),
        @Index(name = "idx_farmacia_cnes", columnList = "codigo_cnes"),
        @Index(name = "idx_farmacia_ativo", columnList = "ativo"),
        @Index(name = "idx_farmacia_tenant", columnList = "tenant_id"),
        @Index(name = "idx_farmacia_estabelecimento", columnList = "estabelecimento_id")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Farmacia extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "codigo_cnes", length = 20)
    private String codigoCnes;

    @Column(name = "codigo_farmacia", length = 50)
    private String codigoFarmaciaInterno;

    @Column(name = "responsavel_tecnico", length = 255)
    private String responsavelTecnico;

    @Column(name = "crf_responsavel", length = 30)
    private String crfResponsavel;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 150)
    private String email;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @OneToMany(mappedBy = "farmacia",
               cascade = { CascadeType.PERSIST, CascadeType.MERGE },
               fetch = FetchType.LAZY)
    private List<IntegracaoGov> integracoesGov = new ArrayList<>();
}
