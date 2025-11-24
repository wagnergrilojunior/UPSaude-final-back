package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "estabelecimentos", schema = "public")
@Data
public class Estabelecimentos extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "Tipo é obrigatório")
    @Pattern(regexp = "^(HOSPITAL|CLINICA|POSTO_SAUDE|UBS|UPA|LABORATORIO|FARMACIA|OUTRO)$", 
             message = "Tipo deve ser HOSPITAL, CLINICA, POSTO_SAUDE, UBS, UPA, LABORATORIO, FARMACIA ou OUTRO")
    @Column(name = "tipo", nullable = false, length = 100)
    private String tipo;

    @Column(name = "endereco_json", columnDefinition = "jsonb")
    private String enderecoJson;

    @Column(name = "contato_json", columnDefinition = "jsonb")
    private String contatoJson;

    @Column(name = "metadados", columnDefinition = "jsonb")
    private String metadados;

    

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "estabelecimentos_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "estabelecimento_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();
}
