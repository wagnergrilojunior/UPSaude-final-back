package com.upsaude.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa um Papel (Role) do sistema.
 * Não possui relação com Tenant por ser uma entidade de referência global.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "papeis", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_papeis_nome", columnNames = {"nome"}),
           @UniqueConstraint(name = "uk_papeis_slug", columnNames = {"slug"})
       },
       indexes = {
           @Index(name = "idx_papeis_nome", columnList = "nome")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Papeis extends BaseEntityWithoutTenant {

    @Column(name = "nome", nullable = false, length = 100)
    private String nome;

    @Column(name = "slug", nullable = false, length = 100)
    private String slug;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "permissoes", columnDefinition = "jsonb")
    private String permissoesJson;

    @Column(name = "papel_do_sistema")
    private Boolean papelDoSistema;

    /**
     * Atribuições deste papel a usuários.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(mappedBy = "papel", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuariosPerfis> atribuicoes = new ArrayList<>();
}
