package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa o perfil de um usuário.
 * Possui relação com Tenant.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "perfis_usuarios", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class PerfisUsuarios extends BaseEntity {

    @Column(name = "usuario_id", nullable = false, unique = true)
    private java.util.UUID usuarioId;
}
