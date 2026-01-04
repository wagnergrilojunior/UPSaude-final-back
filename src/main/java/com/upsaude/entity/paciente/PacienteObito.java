package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.OrigemObitoEnum;
import com.upsaude.util.converter.OrigemObitoEnumConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "paciente_obito", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_paciente_obito_paciente", 
               columnNames = {"paciente_id"})
       },
       indexes = {
           @Index(name = "idx_paciente_obito_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_obito_data_obito", columnList = "data_obito"),
           @Index(name = "idx_paciente_obito_origem", columnList = "origem"),
           @Index(name = "idx_paciente_obito_tenant", columnList = "tenant_id"),
           @Index(name = "idx_paciente_obito_ativo", columnList = "ativo")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PacienteObito extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(name = "data_obito", nullable = false)
    private LocalDate dataObito;

    @Column(name = "causa_obito_cid10", length = 10)
    private String causaObitoCid10;

    @Column(name = "data_registro", nullable = false, columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataRegistro;

    @Convert(converter = OrigemObitoEnumConverter.class)
    @Column(name = "origem")
    private OrigemObitoEnum origem;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    public void prePersist() {
        if (dataRegistro == null) {
            dataRegistro = OffsetDateTime.now();
        }
    }
}

