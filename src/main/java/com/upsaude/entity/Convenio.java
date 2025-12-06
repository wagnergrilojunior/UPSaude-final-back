package com.upsaude.entity;

import com.upsaude.entity.embeddable.CoberturaConvenio;
import com.upsaude.entity.embeddable.ContatoConvenio;
import com.upsaude.entity.embeddable.InformacoesFinanceirasConvenio;
import com.upsaude.entity.embeddable.IntegracaoGovernamentalConvenio;
import com.upsaude.entity.embeddable.RegistroANSConvenio;
import com.upsaude.enums.ModalidadeConvenioEnum;
import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoConvenioEnum;
import com.upsaude.util.converter.ModalidadeConvenioEnumConverter;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import com.upsaude.util.converter.TipoConvenioEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

/**
 * Entidade que representa um convênio/plano de saúde.
 * Armazena informações completas sobre convênios para sistemas de gestão de saúde.
 * Baseado em padrões da ANS (Agência Nacional de Saúde Suplementar) e TISS.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "convenios", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_convenios_codigo_tenant", columnNames = {"codigo", "tenant_id"}),
           @UniqueConstraint(name = "uk_convenios_cnpj_tenant", columnNames = {"cnpj", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_convenio_nome", columnList = "nome"),
           @Index(name = "idx_convenio_codigo", columnList = "codigo"),
           @Index(name = "idx_convenio_cnpj", columnList = "cnpj"),
           @Index(name = "idx_convenio_tipo", columnList = "tipo"),
           @Index(name = "idx_convenio_modalidade", columnList = "modalidade"),
           @Index(name = "idx_convenio_status", columnList = "status"),
           @Index(name = "idx_convenio_registro_ans", columnList = "registro_ans")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Convenio extends BaseEntity {

    public Convenio() {
        this.contato = new ContatoConvenio();
        this.registroAns = new RegistroANSConvenio();
        this.cobertura = new CoberturaConvenio();
        this.informacoesFinanceiras = new InformacoesFinanceirasConvenio();
        this.integracaoGovernamental = new IntegracaoGovernamentalConvenio();
    }

    // ========== IDENTIFICAÇÃO BÁSICA ==========

    @NotBlank(message = "Nome do convênio é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo; // Código interno do convênio

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    @Column(name = "inscricao_estadual", length = 20)
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    @Column(name = "inscricao_municipal", length = 20)
    private String inscricaoMunicipal;

    // ========== CLASSIFICAÇÃO ==========

    @Convert(converter = TipoConvenioEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "Tipo de convênio é obrigatório")
    private TipoConvenioEnum tipo;

    @Convert(converter = ModalidadeConvenioEnumConverter.class)
    @Column(name = "modalidade")
    private ModalidadeConvenioEnum modalidade;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria; // Ex: Básico, Intermediário, Premium

    // ========== ENDEREÇO ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    // ========== CONTATO ==========

    @Embedded
    private ContatoConvenio contato;

    // ========== REGISTRO ANS ==========

    @Embedded
    private RegistroANSConvenio registroAns;

    // ========== COBERTURA ==========

    @Embedded
    private CoberturaConvenio cobertura;

    // ========== INFORMAÇÕES FINANCEIRAS ==========

    @Embedded
    private InformacoesFinanceirasConvenio informacoesFinanceiras;

    // ========== STATUS ==========

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    @Column(name = "ativo_comercial", nullable = false)
    private Boolean ativoComercial = true; // Se está ativo comercialmente

    @Column(name = "aceita_novos_clientes", nullable = false)
    private Boolean aceitaNovosClientes = true; // Se aceita novos clientes

    // ========== DATAS IMPORTANTES ==========

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro; // Data de cadastro do convênio

    @Column(name = "data_ativacao")
    private LocalDate dataAtivacao; // Data de ativação

    @Column(name = "data_desativacao")
    private LocalDate dataDesativacao; // Data de desativação

    // ========== REDE CREDENCIADA ==========

    @Column(name = "rede_credenciada_nacional", nullable = false)
    private Boolean redeCredenciadaNacional = false; // Se tem rede credenciada nacional

    @Column(name = "rede_credenciada_regional", nullable = false)
    private Boolean redeCredenciadaRegional = false; // Se tem rede credenciada regional

    @Column(name = "quantidade_estabelecimentos_credenciados")
    private Integer quantidadeEstabelecimentosCredenciados; // Quantidade de estabelecimentos credenciados

    @Column(name = "quantidade_profissionais_credenciados")
    private Integer quantidadeProfissionaisCredenciados; // Quantidade de profissionais credenciados

    // ========== DOCUMENTAÇÃO ==========

    @Size(max = 255, message = "Contrato deve ter no máximo 255 caracteres")
    @Column(name = "contrato", length = 255)
    private String contrato; // URL ou caminho do contrato

    @Size(max = 255, message = "Tabela de preços deve ter no máximo 255 caracteres")
    @Column(name = "tabela_precos", length = 255)
    private String tabelaPrecos; // URL ou caminho da tabela de preços

    @Size(max = 255, message = "Manual do convênio deve ter no máximo 255 caracteres")
    @Column(name = "manual_convenio", length = 255)
    private String manualConvenio; // URL ou caminho do manual

    // ========== DESCRIÇÃO E OBSERVAÇÕES ==========

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // ========== INTEGRAÇÃO COM SISTEMAS GOVERNAMENTAIS ==========

    @Embedded
    private IntegracaoGovernamentalConvenio integracaoGovernamental;

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (contato == null) {
            contato = new ContatoConvenio();
        }
        if (registroAns == null) {
            registroAns = new RegistroANSConvenio();
        }
        if (cobertura == null) {
            cobertura = new CoberturaConvenio();
        }
        if (informacoesFinanceiras == null) {
            informacoesFinanceiras = new InformacoesFinanceirasConvenio();
        }
        if (integracaoGovernamental == null) {
            integracaoGovernamental = new IntegracaoGovernamentalConvenio();
        }
    }
}
