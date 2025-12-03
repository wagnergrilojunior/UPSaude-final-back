package com.upsaude.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.persistence.Index;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "tenants", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_tenants_slug", columnNames = {"slug"}),
           @UniqueConstraint(name = "uk_tenants_cnpj", columnNames = {"cnpj"})
       },
       indexes = {
           @Index(name = "idx_tenants_slug", columnList = "slug"),
           @Index(name = "idx_tenants_cnpj", columnList = "cnpj")
       })
@Data
public class Tenant extends BaseEntity {

    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "name", nullable = false, length = 255)
    private String nome;

    @NotBlank(message = "Slug é obrigatório")
    @Size(max = 255, message = "Slug deve ter no máximo 255 caracteres")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug deve conter apenas letras minúsculas, números e hífens")
    @Column(name = "slug", nullable = false, length = 255, unique = true)
    private String slug;

    @Column(name = "description", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "logo_url", columnDefinition = "TEXT")
    private String urlLogo;

    @Column(name = "metadata", columnDefinition = "jsonb")
    private String metadados;

    @Column(name = "is_active")
    private Boolean ativo;

    /**
     * Endereços do tenant.
     * Cascade PERSIST/MERGE para gerenciar associações.
     */
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
        name = "tenants_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "tenant_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    @Column(name = "cnpj", length = 14, unique = true)
    private String cnpj;

    @Column(name = "cnes", length = 20)
    private String cnes;

    @Column(name = "tipo_instituicao", length = 50)
    private String tipoInstituicao;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Column(name = "site", length = 255)
    private String site;

    @Column(name = "inscricao_estadual", length = 20)
    private String inscricaoEstadual;

    @Column(name = "inscricao_municipal", length = 20)
    private String inscricaoMunicipal;

    @Column(name = "responsavel_nome", length = 255)
    private String responsavelNome;

    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    @Column(name = "responsavel_cpf", length = 11)
    private String responsavelCpf;

    @Column(name = "responsavel_telefone", length = 20)
    private String responsavelTelefone;

    @Column(name = "horario_funcionamento", columnDefinition = "TEXT")
    private String horarioFuncionamento;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
