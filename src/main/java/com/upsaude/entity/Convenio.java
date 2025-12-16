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

    @NotBlank(message = "Nome do convênio é obrigatório")
    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 255, message = "Nome fantasia deve ter no máximo 255 caracteres")
    @Column(name = "nome_fantasia", length = 255)
    private String nomeFantasia;

    @Size(max = 50, message = "Código deve ter no máximo 50 caracteres")
    @Column(name = "codigo", length = 50)
    private String codigo;

    @Pattern(regexp = "^\\d{14}$", message = "CNPJ deve ter 14 dígitos")
    @Column(name = "cnpj", length = 14)
    private String cnpj;

    @Size(max = 20, message = "Inscrição estadual deve ter no máximo 20 caracteres")
    @Column(name = "inscricao_estadual", length = 20)
    private String inscricaoEstadual;

    @Size(max = 20, message = "Inscrição municipal deve ter no máximo 20 caracteres")
    @Column(name = "inscricao_municipal", length = 20)
    private String inscricaoMunicipal;

    @Convert(converter = TipoConvenioEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "Tipo de convênio é obrigatório")
    private TipoConvenioEnum tipo;

    @Convert(converter = ModalidadeConvenioEnumConverter.class)
    @Column(name = "modalidade")
    private ModalidadeConvenioEnum modalidade;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Embedded
    private ContatoConvenio contato;

    @Embedded
    private RegistroANSConvenio registroAns;

    @Embedded
    private CoberturaConvenio cobertura;

    @Embedded
    private InformacoesFinanceirasConvenio informacoesFinanceiras;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    @Column(name = "ativo_comercial", nullable = false)
    private Boolean ativoComercial = true;

    @Column(name = "aceita_novos_clientes", nullable = false)
    private Boolean aceitaNovosClientes = true;

    @Column(name = "data_cadastro")
    private LocalDate dataCadastro;

    @Column(name = "data_ativacao")
    private LocalDate dataAtivacao;

    @Column(name = "data_desativacao")
    private LocalDate dataDesativacao;

    @Column(name = "rede_credenciada_nacional", nullable = false)
    private Boolean redeCredenciadaNacional = false;

    @Column(name = "rede_credenciada_regional", nullable = false)
    private Boolean redeCredenciadaRegional = false;

    @Column(name = "quantidade_estabelecimentos_credenciados")
    private Integer quantidadeEstabelecimentosCredenciados;

    @Column(name = "quantidade_profissionais_credenciados")
    private Integer quantidadeProfissionaisCredenciados;

    @Size(max = 255, message = "Contrato deve ter no máximo 255 caracteres")
    @Column(name = "contrato", length = 255)
    private String contrato;

    @Size(max = 255, message = "Tabela de preços deve ter no máximo 255 caracteres")
    @Column(name = "tabela_precos", length = 255)
    private String tabelaPrecos;

    @Size(max = 255, message = "Manual do convênio deve ter no máximo 255 caracteres")
    @Column(name = "manual_convenio", length = 255)
    private String manualConvenio;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

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
