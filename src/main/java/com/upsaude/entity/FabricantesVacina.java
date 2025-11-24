package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "fabricantes_vacina", schema = "public")
@Data
public class FabricantesVacina extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "pais", length = 100)
    private String pais;

    @Column(name = "contato_json", columnDefinition = "jsonb")
    private String contatoJson;

    

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "fabricantes_vacina_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "fabricante_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();
}
