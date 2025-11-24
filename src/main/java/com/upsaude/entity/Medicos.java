package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToOne;
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
@Table(name = "medicos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_medicos_crm_uf_tenant", columnNames = {"crm", "crm_uf", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_medicos_crm", columnList = "crm")
       })
@Data
public class Medicos extends BaseEntity {

    

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @NotBlank(message = "CRM é obrigatório")
    @Pattern(regexp = "^\\d{4,10}$", message = "CRM deve ter entre 4 e 10 dígitos")
    @Column(name = "crm", nullable = false, length = 20)
    private String crm;

    @Pattern(regexp = "^[A-Z]{2}$", message = "UF do CRM deve ter 2 letras maiúsculas")
    @Column(name = "crm_uf", length = 2)
    private String crmUf;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "especialidade_id")
    private EspecialidadesMedicas especialidade;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(name = "telefone", length = 20)
    private String telefone;

    @Email(message = "Email inválido")
    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "medicos_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "medico_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();
}
