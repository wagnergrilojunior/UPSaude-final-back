package com.upsaude.entity.referencia.sigtap;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "sigtap_ocupacao", schema = "public", uniqueConstraints = {
                @UniqueConstraint(name = "uk_sigtap_ocupacao_codigo_oficial", columnNames = { "codigo_oficial" })
}, indexes = {
                @Index(name = "idx_sigtap_ocupacao_nome", columnList = "nome")
})
@Data
@EqualsAndHashCode(callSuper = true)
public class SigtapOcupacao extends BaseEntityWithoutTenant {

        @Column(name = "codigo_oficial", nullable = false, length = 6)
        private String codigoOficial;

        @Column(name = "nome", length = 150)
        private String nome;

        // Campos FHIR - Hierarquia CBO
        @Column(name = "grande_grupo", length = 100)
        private String grandeGrupo;

        @Column(name = "subgrupo_principal", length = 100)
        private String subgrupoPrincipal;

        @Column(name = "subgrupo", length = 100)
        private String subgrupo;

        @Column(name = "familia", length = 100)
        private String familia;

        @Column(name = "descricao_fhir", columnDefinition = "TEXT")
        private String descricaoFhir;

        @Column(name = "codigo_cbo_completo", length = 10)
        private String codigoCboCompleto;
}
