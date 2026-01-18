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

    

    @Column(name = "source_system", nullable = false, length = 20)
    private String sourceSystem; 

    @Column(name = "external_code", nullable = false, length = 50)
    private String externalCode; 

    @Column(name = "external_version", length = 50)
    private String externalVersion; 

    @Column(name = "last_sync_at")
    private OffsetDateTime lastSyncAt;

    

    @Column(name = "codigo_loinc", length = 20)
    private String codigoLoinc; 

    @Column(name = "codigo_gal", length = 20)
    private String codigoGal; 

    @Column(name = "codigo_sigtap", length = 20)
    private String codigoSigtap; 

    @Column(name = "codigo_tuss", length = 20)
    private String codigoTuss; 

    

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

    

    @Column(name = "sigtap_competencia_inicial", length = 6)
    private String sigtapCompetenciaInicial;

    @Column(name = "sigtap_competencia_final", length = 6)
    private String sigtapCompetenciaFinal;

    

    @Column(name = "fhir_code_system", length = 200)
    private String fhirCodeSystem;

    @Column(name = "fhir_value_set", length = 200)
    private String fhirValueSet;
}
