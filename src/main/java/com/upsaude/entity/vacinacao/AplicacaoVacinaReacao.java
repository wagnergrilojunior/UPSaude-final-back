package com.upsaude.entity.vacinacao;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.sistema.multitenancy.Tenant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "aplicacoes_vacina_reacoes")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AplicacaoVacinaReacao {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aplicacao_id", nullable = false)
    private AplicacaoVacina aplicacao;

    // === Dados da Reação ===
    @Column(name = "codigo_reacao", length = 50)
    private String codigoReacao;

    @Column(name = "nome_reacao", length = 255)
    private String nomeReacao;

    @Column(name = "data_ocorrencia", nullable = false)
    private OffsetDateTime dataOcorrencia;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    // === Classificação ===
    @Column(name = "criticidade", length = 50)
    private String criticidade;

    @Column(name = "grau_certeza", length = 50)
    private String grauCerteza;

    @Column(name = "categoria_agente", length = 50)
    private String categoriaAgente;

    // === Acompanhamento ===
    @Column(name = "requer_tratamento")
    @Builder.Default
    private Boolean requerTratamento = false;

    @Column(name = "tratamento_realizado", columnDefinition = "TEXT")
    private String tratamentoRealizado;

    @Column(name = "evolucao", columnDefinition = "TEXT")
    private String evolucao;

    @Column(name = "resolvida")
    @Builder.Default
    private Boolean resolvida = false;

    @Column(name = "data_resolucao")
    private OffsetDateTime dataResolucao;

    // === Notificação ===
    @Column(name = "notificada_anvisa")
    @Builder.Default
    private Boolean notificadaAnvisa = false;

    @Column(name = "data_notificacao")
    private OffsetDateTime dataNotificacao;

    @Column(name = "numero_notificacao", length = 100)
    private String numeroNotificacao;

    // === Reportado ===
    @Column(name = "reportado_por", length = 100)
    private String reportadoPor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_registro_id")
    private ProfissionaisSaude profissionalRegistro;

    // === Tenant ===
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id", nullable = false)
    private Tenant tenant;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @Column(name = "atualizado_em")
    private OffsetDateTime atualizadoEm;

    @PrePersist
    protected void onCreate() {
        if (criadoEm == null) {
            criadoEm = OffsetDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        atualizadoEm = OffsetDateTime.now();
    }

    public void marcarResolvida() {
        this.resolvida = true;
        this.dataResolucao = OffsetDateTime.now();
    }
}
