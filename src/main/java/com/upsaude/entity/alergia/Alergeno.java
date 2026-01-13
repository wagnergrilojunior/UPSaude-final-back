package com.upsaude.entity.alergia;

import java.time.OffsetDateTime;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "alergenos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Alergeno {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "codigo_fhir", nullable = false, unique = true, length = 50)
    private String codigoFhir;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "categoria", length = 50)
    private String categoria;

    @Column(name = "fonte", length = 50)
    private String fonte;

    @Column(name = "codigo_sistema", length = 500)
    private String codigoSistema;

    @Column(name = "ativo", nullable = false)
    @Builder.Default
    private Boolean ativo = true;

    @Column(name = "data_sincronizacao")
    private OffsetDateTime dataSincronizacao;

    @CreatedDate
    @Column(name = "criado_em", nullable = false, updatable = false)
    private OffsetDateTime criadoEm;

    @LastModifiedDate
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
}
