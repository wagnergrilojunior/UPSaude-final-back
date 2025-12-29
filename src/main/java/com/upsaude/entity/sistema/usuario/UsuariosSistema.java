package com.upsaude.entity.sistema.usuario;

import com.upsaude.entity.embeddable.ConfiguracaoUsuario;
import com.upsaude.entity.embeddable.DadosExibicaoUsuario;
import com.upsaude.entity.embeddable.DadosIdentificacaoUsuario;
import com.upsaude.entity.estabelecimento.UsuarioEstabelecimento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.auth.User;
import com.upsaude.entity.sistema.multitenancy.Tenant;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
@Table(name = "usuarios_sistema", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_usuarios_sistema_user_tenant", columnNames = {"user_id", "tenant_id"}),
           @UniqueConstraint(name = "uk_usuarios_sistema_username", columnNames = {"username"})
       },
       indexes = {
           @Index(name = "idx_usuarios_sistema_user", columnList = "user_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners({AuditingEntityListener.class})
public class UsuariosSistema {

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Embedded
    private DadosIdentificacaoUsuario dadosIdentificacao;

    @Embedded
    private DadosExibicaoUsuario dadosExibicao;

    @Embedded
    private ConfiguracaoUsuario configuracao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_saude_id")
    private ProfissionaisSaude profissionalSaude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioEstabelecimento> estabelecimentosVinculados = new ArrayList<>();

    //===============================================================================================================

    @PrePersist
    @PreUpdate
    public void validateEmbeddablesAndCollections() {
        if (dadosIdentificacao == null) {
            dadosIdentificacao = new DadosIdentificacaoUsuario();
        }
        if (dadosExibicao == null) {
            dadosExibicao = new DadosExibicaoUsuario();
        }
        if (configuracao == null) {
            configuracao = new ConfiguracaoUsuario();
        }

        if (estabelecimentosVinculados == null) {
            estabelecimentosVinculados = new ArrayList<>();
        }
    }

    public void addEstabelecimentoVinculado(UsuarioEstabelecimento estabelecimentoVinculado) {
        if (estabelecimentoVinculado == null) {
            return;
        }
        if (estabelecimentosVinculados == null) {
            estabelecimentosVinculados = new ArrayList<>();
        }
        if (!estabelecimentosVinculados.contains(estabelecimentoVinculado)) {
            estabelecimentosVinculados.add(estabelecimentoVinculado);
            estabelecimentoVinculado.setUsuario(this);
        }
    }

    public void removeEstabelecimentoVinculado(UsuarioEstabelecimento estabelecimentoVinculado) {
        if (estabelecimentoVinculado == null || estabelecimentosVinculados == null) {
            return;
        }
        if (estabelecimentosVinculados.remove(estabelecimentoVinculado)) {
            estabelecimentoVinculado.setUsuario(null);
        }
    }
}
