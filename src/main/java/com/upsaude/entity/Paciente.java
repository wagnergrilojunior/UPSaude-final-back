package com.upsaude.entity;

import com.upsaude.enums.EstadoCivilEnum;
import com.upsaude.enums.SexoEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "pacientes", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_pacientes_cpf", columnNames = {"cpf", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_pacientes_cpf", columnList = "cpf")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Paciente extends BaseEntity {

    @NotBlank(message = "Nome completo é obrigatório")
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Column(name = "nome_completo", nullable = false, length = 255)
    private String nomeCompleto;

    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "rg", length = 20)
    private String rg;

    @Pattern(regexp = "^\\d{15}$", message = "CNS deve ter 15 dígitos")
    @Column(name = "cns", length = 20)
    private String cns;

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "sexo")
    @Enumerated(EnumType.STRING)
    private SexoEnum sexo;

    @Column(name = "estado_civil", length = 50)
    @Enumerated(EnumType.STRING)
    private EstadoCivilEnum estadoCivil;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "email", length = 100)
    private String email;

    @Column(name = "nome_mae", length = 100)
    private String nomeMae;

    @Column(name = "nome_pai", length = 100)
    private String nomePai;

    @Column(name = "responsavel_nome", length = 255)
    private String responsavelNome;

    @Pattern(regexp = "^\\d{11}$", message = "CPF do responsável deve ter 11 dígitos")
    @Column(name = "responsavel_cpf", length = 14)
    private String responsavelCpf;

    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone do responsável deve ter 10 ou 11 dígitos")
    @Column(name = "responsavel_telefone", length = 20)
    private String responsavelTelefone;

    @Column(name = "endereco_json", columnDefinition = "jsonb")
    private String enderecoJson;

    @Column(name = "contato_json", columnDefinition = "jsonb")
    private String contatoJson;

    @Column(name = "informacoes_adicionais_json", columnDefinition = "jsonb")
    private String informacoesAdicionaisJson;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @Column(name = "numero_carteirinha", length = 50)
    private String numeroCarteirinha;

    @Column(name = "data_validade_carteirinha")
    private LocalDate dataValidadeCarteirinha;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

    @OneToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "pacientes_enderecos",
        schema = "public",
        joinColumns = @JoinColumn(name = "paciente_id"),
        inverseJoinColumns = @JoinColumn(name = "endereco_id")
    )
    private List<Endereco> enderecos = new ArrayList<>();

}
