package com.upsaude.entity;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "usuarios_sistema", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_usuarios_sistema_user_tenant", columnNames = {"user_id", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_usuarios_sistema_user", columnList = "user_id")
       })
@Data
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
    private Boolean active = true;

    // ========== TENANT ==========
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    @NotNull(message = "Tenant é obrigatório")
    private Tenant tenant;

    // ========== DADOS DO USUÁRIO ==========
    @Column(name = "user_id", nullable = false)
    @NotNull(message = "Usuário (auth) é obrigatório")
    private UUID userId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_saude_id")
    private ProfissionaisSaude profissionalSaude;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id")
    private Paciente paciente;

    /**
     * Indica se o usuário é administrador do tenant.
     * Se true: tem acesso total ao tenant e todos os estabelecimentos, não precisa de vínculos.
     * Se false: precisa ter vínculos com estabelecimentos específicos.
     */
    @Column(name = "admin_tenant", nullable = false)
    @NotNull(message = "Admin tenant é obrigatório")
    private Boolean adminTenant = false;

    @Column(name = "nome_exibicao", length = 255)
    private String nomeExibicao;

    @Column(name = "user", length = 100, unique = true)
    private String user; // Campo para login alternativo (username)

    @Column(name = "foto_url", columnDefinition = "TEXT")
    private String fotoUrl; // URL da foto no Supabase Storage

    // ========== RELACIONAMENTOS ==========
    
    /**
     * Estabelecimentos vinculados ao usuário.
     * OneToMany bidirecional com cascade completo - JPA gerencia automaticamente.
     */
    @OneToMany(mappedBy = "usuario", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioEstabelecimento> estabelecimentosVinculados = new ArrayList<>();
}
