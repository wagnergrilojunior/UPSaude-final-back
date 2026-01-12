package com.upsaude.entity.clinica.prontuario;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.referencia.Ciap2;
import com.upsaude.entity.referencia.cid.Cid10Subcategorias;
import java.time.OffsetDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;

@Entity
@Table(name = "doencas_paciente", schema = "public", indexes = {
        @Index(name = "idx_doenca_paciente_prontuario", columnList = "prontuario_id"),
        @Index(name = "idx_doenca_paciente_cid10", columnList = "cid10_subcategorias_id"),
        @Index(name = "idx_doenca_paciente_ciap2", columnList = "ciap2_id"),
        @Index(name = "idx_doenca_paciente_data_diagnostico", columnList = "data_diagnostico"),
        @Index(name = "idx_doenca_paciente_ativa", columnList = "ativa"),
        @Index(name = "idx_doenca_paciente_tenant", columnList = "tenant_id")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class DoencaPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "prontuario_id", nullable = false)
    private Prontuario prontuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid10_subcategorias_id")
    private Cid10Subcategorias diagnostico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ciap2_id")
    private Ciap2 ciap2;

    @Column(name = "tipo_catalogo", length = 10)
    private String tipoCatalogo; // CID10, CIAP2, OUTRO

    @Column(name = "codigo", length = 20)
    private String codigo;

    @Column(name = "descricao_personalizada", length = 500)
    private String descricaoPersonalizada;

    @Column(name = "data_diagnostico", nullable = false)
    private LocalDate dataDiagnostico;

    @Column(name = "status", length = 20)
    private String status; // ativo, resolvido, erro

    @Column(name = "cronico")
    private Boolean cronico = false;

    @Column(name = "ativa", nullable = false)
    private Boolean ativa = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
