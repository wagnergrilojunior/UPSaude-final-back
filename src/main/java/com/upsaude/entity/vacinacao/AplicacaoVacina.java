package com.upsaude.entity.vacinacao;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.fhir.FhirSyncLog.SyncStatus;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aplicacoes_vacina")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AplicacaoVacina {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // === Identificadores FHIR ===
    @Column(name = "fhir_identifier", length = 255)
    private String fhirIdentifier;

    @Enumerated(EnumType.STRING)
    @Column(name = "fhir_status", nullable = false, length = 50)
    @Builder.Default
    private StatusAplicacao fhirStatus = StatusAplicacao.COMPLETED;

    @Column(name = "fhir_status_reason_codigo", length = 50)
    private String fhirStatusReasonCodigo;

    @Column(name = "fhir_status_reason_descricao", length = 255)
    private String fhirStatusReasonDescricao;

    // === Paciente ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    // === Vacina ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "imunobiologico_id", nullable = false)
    private Imunobiologico imunobiologico;

    // === Lote e Fabricante ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lote_id")
    private LoteVacina lote;

    @Column(name = "numero_lote", length = 100)
    private String numeroLote;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricanteImunobiologico fabricante;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    // === Dose ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_dose_id")
    private TipoDose tipoDose;

    @Column(name = "numero_dose")
    private Integer numeroDose;

    @Column(name = "dose_quantidade", precision = 10, scale = 2)
    private BigDecimal doseQuantidade;

    @Column(name = "dose_unidade", length = 50)
    private String doseUnidade;

    // === Local e Via ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "local_aplicacao_id")
    private LocalAplicacao localAplicacao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "via_administracao_id")
    private ViaAdministracao viaAdministracao;

    // === Estratégia ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estrategia_id")
    private EstrategiaVacinacao estrategia;

    // === Data e Hora ===
    @Column(name = "data_aplicacao", nullable = false)
    private OffsetDateTime dataAplicacao;

    @Column(name = "data_registro", nullable = false)
    @Builder.Default
    private OffsetDateTime dataRegistro = OffsetDateTime.now();

    // === Profissional ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional;

    @Column(name = "profissional_funcao", length = 100)
    private String profissionalFuncao;

    // === Estabelecimento ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id")
    private Estabelecimentos estabelecimento;

    // === Fonte e Origem ===
    @Column(name = "fonte_primaria")
    @Builder.Default
    private Boolean fontePrimaria = true;

    @Column(name = "origem_registro", length = 100)
    private String origemRegistro;

    // === Subpotência ===
    @Column(name = "dose_subpotente")
    @Builder.Default
    private Boolean doseSubpotente = false;

    @Column(name = "motivo_subpotencia", columnDefinition = "TEXT")
    private String motivoSubpotencia;

    // === Programa ===
    @Column(name = "elegibilidade_programa", length = 50)
    private String elegibilidadePrograma;

    @Column(name = "fonte_financiamento", length = 100)
    private String fonteFinanciamento;

    // === Observações ===
    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    // === Sincronização FHIR ===
    @Column(name = "fhir_resource_id", length = 255)
    private String fhirResourceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "fhir_sync_status", length = 50)
    private SyncStatus fhirSyncStatus;

    @Column(name = "fhir_last_sync")
    private OffsetDateTime fhirLastSync;

    // === Tenant ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    // === Auditoria ===
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "criado_por")
    private UsuariosSistema criadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atualizado_por")
    private UsuariosSistema atualizadoPor;

    // === Relacionamentos ===
    @OneToMany(mappedBy = "aplicacao", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<AplicacaoVacinaReacao> reacoes = new ArrayList<>();

    @PrePersist
    protected void onCreate() {
        if (criadoEm == null) {
            criadoEm = OffsetDateTime.now();
        }
        if (dataRegistro == null) {
            dataRegistro = OffsetDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = OffsetDateTime.now();
    }

    public void addReacao(AplicacaoVacinaReacao reacao) {
        reacoes.add(reacao);
        reacao.setAplicacao(this);
    }

    public void removeReacao(AplicacaoVacinaReacao reacao) {
        reacoes.remove(reacao);
        reacao.setAplicacao(null);
    }
}
