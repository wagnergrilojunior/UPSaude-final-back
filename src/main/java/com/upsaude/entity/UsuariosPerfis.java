package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "usuarios_perfis", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class UsuariosPerfis extends BaseEntity {
    // NOTA: As constraints únicas são gerenciadas via índices parciais no banco de dados:
    // - uk_usuario_perfil_estabelecimento: garante unicidade de (usuario_id, papel_id, tenant_id, estabelecimento_id) 
    //   quando estabelecimento_id IS NOT NULL (permite perfis diferentes por estabelecimento)
    // - uk_usuario_perfil_global: garante unicidade de (usuario_id, papel_id, tenant_id) 
    //   quando estabelecimento_id IS NULL (perfis globais)

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuariosSistema usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "papel_id", nullable = false)
    private Papeis papel;

    @Column(name = "escopo", length = 50)
    private String escopo;
}
