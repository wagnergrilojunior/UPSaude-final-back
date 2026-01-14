package com.upsaude.entity.sistema.multitenancy;

import com.upsaude.entity.embeddable.ConfiguracaoFinanceiraTenant;
import com.upsaude.entity.embeddable.ContatoTenant;
import com.upsaude.entity.embeddable.DadosFiscaisTenant;
import com.upsaude.entity.embeddable.DadosIdentificacaoTenant;
import com.upsaude.entity.embeddable.InformacoesAdicionaisTenant;
import com.upsaude.entity.embeddable.ResponsavelTenant;
import com.upsaude.entity.paciente.Endereco;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners({AuditingEntityListener.class})
public class Tenant {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @EqualsAndHashCode.Include
    private UUID id;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime createdAt;

    @LastModifiedDate
    @Column(name = "atualizado_em")
    private OffsetDateTime updatedAt;

    @Column(name = "ativo", nullable = false)
    private Boolean ativo = true;

    @Column(name = "consorcio", nullable = false, columnDefinition = "boolean default false")
    private Boolean consorcio = false;

    @Embedded
    private DadosIdentificacaoTenant dadosIdentificacao;

    @Embedded
    private ContatoTenant contato;

    @Embedded
    private DadosFiscaisTenant dadosFiscais;

    @Embedded
    private ResponsavelTenant responsavel;

    @Embedded
    private InformacoesAdicionaisTenant informacoesAdicionais;

    @Embedded
    private ConfiguracaoFinanceiraTenant configuracaoFinanceira;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @PrePersist
    @PreUpdate
    public void validateEmbeddables() {
        if (dadosIdentificacao == null) {
            dadosIdentificacao = new DadosIdentificacaoTenant();
        }
        if (contato == null) {
            contato = new ContatoTenant();
        }
        if (dadosFiscais == null) {
            dadosFiscais = new DadosFiscaisTenant();
        }
        if (responsavel == null) {
            responsavel = new ResponsavelTenant();
        }
        if (informacoesAdicionais == null) {
            informacoesAdicionais = new InformacoesAdicionaisTenant();
        }
        if (configuracaoFinanceira == null) {
            configuracaoFinanceira = new ConfiguracaoFinanceiraTenant();
        }
        if (consorcio == null) {
            consorcio = false;
        }
    }
}
