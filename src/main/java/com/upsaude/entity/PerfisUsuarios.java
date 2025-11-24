package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "perfis_usuarios", schema = "public")
@Data
public class PerfisUsuarios {

    @Id
    @Column(name = "usuario_id", nullable = false)
    private java.util.UUID usuarioId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tenant_id")
    private Tenant tenant;

    @Column(name = "criado_em", nullable = false)
    private java.time.OffsetDateTime criadoEm;

    @Column(name = "atualizado_em", nullable = false)
    private java.time.OffsetDateTime atualizadoEm;
}
