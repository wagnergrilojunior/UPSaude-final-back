package com.upsaude.entity;

import com.upsaude.enums.TipoResponsavelEnum;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Entidade para armazenar informações do responsável legal do paciente.
 * Relacionamento 1:1 com Paciente.
 * Utilizada principalmente para pacientes menores de idade ou pessoas com capacidade civil reduzida.
 */
@Entity
@Table(name = "responsaveis_legais", schema = "public",
       indexes = {
           @Index(name = "idx_responsaveis_legais_paciente", columnList = "paciente_id"),
           @Index(name = "idx_responsaveis_legais_cpf", columnList = "cpf")
       })
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelLegal extends BaseEntity {

    /**
     * Relacionamento 1:1 com Paciente.
     * O paciente possui um único responsável legal (opcional).
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    /**
     * Nome completo do responsável legal.
     * Campo obrigatório para identificação do responsável.
     */
    @Size(max = 255, message = "Nome completo deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    /**
     * CPF do responsável legal.
     * Utilizado para identificação única e validação de dados.
     */
    @Pattern(regexp = "^\\d{11}$", message = "CPF deve ter 11 dígitos")
    @Column(name = "cpf", length = 14)
    private String cpf;

    /**
     * Telefone de contato do responsável legal.
     * Importante para comunicação em caso de emergências ou necessidade de autorização.
     */
    @Pattern(regexp = "^\\d{10,11}$", message = "Telefone deve ter 10 ou 11 dígitos")
    @Column(name = "telefone", length = 20)
    private String telefone;

    /**
     * Tipo de responsável legal.
     * Define a natureza da responsabilidade (pai, mãe, tutor, curador, etc.).
     */
    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_responsavel")
    private TipoResponsavelEnum tipoResponsavel;

    /**
     * Indica se o responsável legal autorizou o uso dos dados do paciente conforme LGPD.
     * Importante para conformidade com a Lei Geral de Proteção de Dados.
     */
    @Column(name = "autorizacao_uso_dados_lgpd", nullable = false)
    private Boolean autorizacaoUsoDadosLGPD = false;

    /**
     * Indica se o responsável legal autorizou o tratamento do paciente.
     * Necessário para procedimentos em menores de idade ou pessoas com capacidade civil reduzida.
     */
    @Column(name = "autorizacao_responsavel", nullable = false)
    private Boolean autorizacaoResponsavel = false;
}

