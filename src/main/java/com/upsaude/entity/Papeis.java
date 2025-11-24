package com.upsaude.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

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
public class Papeis  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

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

    @OneToMany(mappedBy = "papel", fetch = FetchType.LAZY)
    private List<UsuariosPerfis> atribuicoes = new ArrayList<>();
}
