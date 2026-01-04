package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paciente_endereco", schema = "public",
       indexes = {
           @Index(name = "idx_paciente_endereco_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_endereco_endereco", columnList = "endereco_id"),
           @Index(name = "idx_paciente_endereco_principal", columnList = "paciente_id, principal"),
           @Index(name = "idx_paciente_endereco_ativo", columnList = "paciente_id, ativo"),
           @Index(name = "idx_paciente_endereco_tenant", columnList = "tenant_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PacienteEndereco extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    private Endereco endereco;

    @Column(name = "principal", nullable = false)
    private Boolean principal = false;

    @PrePersist
    public void prePersist() {
        if (getActive() == null) {
            setActive(true);
        }
        if (principal == null) {
            principal = false;
        }
    }
}

