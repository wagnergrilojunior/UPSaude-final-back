package com.upsaude.entity.estabelecimento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;

import com.upsaude.enums.TipoUsuarioSistemaEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "usuarios_estabelecimentos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_usuario_estabelecimento", columnNames = {"usuario_id", "estabelecimento_id", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_usuario_estabelecimento_usuario", columnList = "usuario_id"),
           @Index(name = "idx_usuario_estabelecimento_estabelecimento", columnList = "estabelecimento_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class UsuarioEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    private UsuariosSistema usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_usuario", nullable = false, length = 50)
    private TipoUsuarioSistemaEnum tipoUsuario;
}
