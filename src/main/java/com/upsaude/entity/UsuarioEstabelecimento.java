package com.upsaude.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa o vínculo entre um usuário do sistema e um estabelecimento.
 * Permite que um usuário tenha acesso a múltiplos estabelecimentos dentro do mesmo tenant.
 * 
 * @author UPSaúde
 */
@Entity
@Table(name = "usuarios_estabelecimentos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_usuario_estabelecimento", columnNames = {"usuario_id", "estabelecimento_id", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_usuario_estabelecimento_usuario", columnList = "usuario_id"),
           @Index(name = "idx_usuario_estabelecimento_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class UsuarioEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_id", nullable = false)
    @NotNull(message = "Usuário é obrigatório")
    private UsuariosSistema usuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    @NotNull(message = "Estabelecimento é obrigatório")
    private Estabelecimentos estabelecimento;
}

