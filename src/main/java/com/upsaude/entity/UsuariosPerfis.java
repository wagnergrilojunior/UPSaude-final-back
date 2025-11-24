package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;

@Entity
@Table(name = "usuarios_perfis", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_usuario_perfil", columnNames = {"usuario_id", "papel_id", "tenant_id"})
       })
@Data
public class UsuariosPerfis extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuariosSistema usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "papel_id", nullable = false)
    private Papeis papel;

    @Column(name = "escopo", length = 50)
    private String escopo;
}
