package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.OrigemEnderecoEnum;
import com.upsaude.enums.TipoEnderecoEnum;
import com.upsaude.util.converter.OrigemEnderecoEnumConverter;
import com.upsaude.util.converter.TipoEnderecoEnumConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "paciente_endereco", schema = "public",
       indexes = {
           @Index(name = "idx_paciente_endereco_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_endereco_endereco", columnList = "endereco_id"),
           @Index(name = "idx_paciente_endereco_principal", columnList = "paciente_id, principal"),
           @Index(name = "idx_paciente_endereco_ativo", columnList = "paciente_id, ativo"),
           @Index(name = "idx_paciente_endereco_tipo", columnList = "tipo_endereco"),
           @Index(name = "idx_paciente_endereco_origem", columnList = "origem"),
           @Index(name = "idx_paciente_endereco_tenant", columnList = "tenant_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PacienteEndereco extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id", nullable = false)
    @NotNull(message = "Endereço é obrigatório")
    private Endereco endereco;

    @Convert(converter = TipoEnderecoEnumConverter.class)
    @Column(name = "tipo_endereco")
    private TipoEnderecoEnum tipoEndereco;

    @Column(name = "principal", nullable = false)
    private Boolean principal = false;

    @Convert(converter = OrigemEnderecoEnumConverter.class)
    @Column(name = "origem")
    private OrigemEnderecoEnum origem;

    @Column(name = "data_inicio", nullable = false)
    @NotNull(message = "Data de início é obrigatória")
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    // Campo 'ativo' herdado de BaseEntity (active -> ativo)
    // Usar getActive()/setActive() em vez de getAtivo()/setAtivo()

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @PrePersist
    public void prePersist() {
        if (dataInicio == null) {
            dataInicio = LocalDate.now();
        }
        if (getActive() == null) {
            setActive(true);
        }
        if (principal == null) {
            principal = false;
        }
    }
}

