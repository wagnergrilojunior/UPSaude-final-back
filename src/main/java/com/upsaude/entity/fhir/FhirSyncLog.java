package com.upsaude.entity.fhir;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "fhir_sync_log")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FhirSyncLog {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "recurso", nullable = false, length = 100)
    private String recurso;

    @Column(name = "data_inicio", nullable = false)
    private OffsetDateTime dataInicio;

    @Column(name = "data_fim")
    private OffsetDateTime dataFim;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 50)
    private SyncStatus status;

    @Column(name = "total_encontrados")
    @Builder.Default
    private Integer totalEncontrados = 0;

    @Column(name = "novos_inseridos")
    @Builder.Default
    private Integer novosInseridos = 0;

    @Column(name = "atualizados")
    @Builder.Default
    private Integer atualizados = 0;

    @Column(name = "erros")
    @Builder.Default
    private Integer erros = 0;

    @Column(name = "mensagem_erro", columnDefinition = "TEXT")
    private String mensagemErro;

    @Column(name = "usuario_id")
    private UUID usuarioId;

    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @PrePersist
    protected void onCreate() {
        if (criadoEm == null) {
            criadoEm = OffsetDateTime.now();
        }
        if (dataInicio == null) {
            dataInicio = OffsetDateTime.now();
        }
    }

    public void concluir() {
        this.dataFim = OffsetDateTime.now();
        this.status = SyncStatus.CONCLUIDO;
    }

    public void falhar(String mensagem) {
        this.dataFim = OffsetDateTime.now();
        this.status = SyncStatus.ERRO;
        this.mensagemErro = mensagem;
    }

    public enum SyncStatus {
        EM_ANDAMENTO,
        CONCLUIDO,
        ERRO
    }
}
