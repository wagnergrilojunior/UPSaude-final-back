package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.OrigemIdentificadorEnum;
import com.upsaude.enums.TipoIdentificadorEnum;
import com.upsaude.util.converter.OrigemIdentificadorEnumConverter;
import com.upsaude.util.converter.TipoIdentificadorEnumConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "paciente_identificador", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_paciente_identificador_tipo_valor_tenant", 
               columnNames = {"tipo", "valor", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_paciente_identificador_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_identificador_tipo", columnList = "tipo"),
           @Index(name = "idx_paciente_identificador_valor", columnList = "valor"),
           @Index(name = "idx_paciente_identificador_origem", columnList = "origem"),
           @Index(name = "idx_paciente_identificador_principal", columnList = "paciente_id, principal"),
           @Index(name = "idx_paciente_identificador_validado", columnList = "validado"),
           @Index(name = "idx_paciente_identificador_tenant", columnList = "tenant_id"),
           @Index(name = "idx_paciente_identificador_ativo", columnList = "ativo")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PacienteIdentificador extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Convert(converter = TipoIdentificadorEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    private TipoIdentificadorEnum tipo;

    @Column(name = "valor", nullable = false, length = 255)
    private String valor;

    @Convert(converter = OrigemIdentificadorEnumConverter.class)
    @Column(name = "origem")
    private OrigemIdentificadorEnum origem;

    @Column(name = "validado", nullable = false)
    private Boolean validado = false;

    @Column(name = "data_validacao")
    private LocalDate dataValidacao;

    @Column(name = "principal", nullable = false)
    private Boolean principal = false;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

