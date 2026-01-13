package com.upsaude.entity.referencia.exame;

import com.upsaude.entity.BaseEntityWithoutTenant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;

/**
 * Catálogo unificado de exames/procedimentos laboratoriais.
 * Suporta múltiplas origens: SIGTAP, LOINC, GAL, TUSS.
 * 
 * Estratégia: Modelo canônico único com campos de controle de origem.
 */
@Entity
@Table(name = "catalogo_exames", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "uk_catalogo_exames_source_code", columnNames = { "source_system", "external_code" })
}, indexes = {
        @Index(name = "idx_catalogo_exames_code", columnList = "external_code"),
        @Index(name = "idx_catalogo_exames_source", columnList = "source_system"),
        @Index(name = "idx_catalogo_exames_nome", columnList = "nome"),
        @Index(name = "idx_catalogo_exames_loinc", columnList = "codigo_loinc"),
        @Index(name = "idx_catalogo_exames_gal", columnList = "codigo_gal"),
        @Index(name = "idx_catalogo_exames_sigtap", columnList = "codigo_sigtap")
})
@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CatalogoExame extends BaseEntityWithoutTenant {

    // ============== CAMPOS DE CONTROLE DE ORIGEM ==============

    @Column(name = "source_system", nullable = false, length = 20)
    private String sourceSystem; // SIGTAP, LOINC, GAL, TUSS, FHIR

    @Column(name = "external_code", nullable = false, length = 50)
    private String externalCode; // Código na origem

    @Column(name = "external_version", length = 50)
    private String externalVersion; // Versão do CodeSystem/tabela origem

    @Column(name = "last_sync_at")
    private OffsetDateTime lastSyncAt;

    // ============== CÓDIGOS POR SISTEMA (Multi-fonte) ==============

    @Column(name = "codigo_loinc", length = 20)
    private String codigoLoinc; // Código LOINC se disponível

    @Column(name = "codigo_gal", length = 20)
    private String codigoGal; // Código GAL se disponível

    @Column(name = "codigo_sigtap", length = 20)
    private String codigoSigtap; // Código SIGTAP se disponível

    @Column(name = "codigo_tuss", length = 20)
    private String codigoTuss; // Código TUSS se disponível

    // ============== DADOS DO EXAME ==============

    @Column(name = "nome", nullable = false, length = 500)
    private String nome;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "tipo_amostra", length = 200)
    private String tipoAmostra;

    @Column(name = "material", length = 200)
    private String material;

    @Column(name = "metodo", length = 200)
    private String metodo;

    // ============== DADOS SIGTAP (quando aplicável) ==============

    @Column(name = "sigtap_competencia_inicial", length = 6)
    private String sigtapCompetenciaInicial;

    @Column(name = "sigtap_competencia_final", length = 6)
    private String sigtapCompetenciaFinal;

    // ============== DADOS FHIR (quando aplicável) ==============

    @Column(name = "fhir_code_system", length = 200)
    private String fhirCodeSystem;

    @Column(name = "fhir_value_set", length = 200)
    private String fhirValueSet;
}
