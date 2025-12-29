package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.util.converter.TipoContatoEnumConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "paciente_contato", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_paciente_contato_tipo_valor_tenant", 
               columnNames = {"tipo", "valor", "tenant_id"})
       },
       indexes = {
           @Index(name = "idx_paciente_contato_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_contato_tipo", columnList = "tipo"),
           @Index(name = "idx_paciente_contato_valor", columnList = "valor"),
           @Index(name = "idx_paciente_contato_principal", columnList = "paciente_id, principal"),
           @Index(name = "idx_paciente_contato_verificado", columnList = "verificado"),
           @Index(name = "idx_paciente_contato_tenant", columnList = "tenant_id"),
           @Index(name = "idx_paciente_contato_ativo", columnList = "ativo")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PacienteContato extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @Convert(converter = TipoContatoEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "Tipo de contato é obrigatório")
    private TipoContatoEnum tipo;

    @NotBlank(message = "Valor do contato é obrigatório")
    @Size(max = 255, message = "Valor do contato deve ter no máximo 255 caracteres")
    @Column(name = "valor", nullable = false, length = 255)
    private String valor;

    @Column(name = "principal", nullable = false)
    private Boolean principal = false;

    @Column(name = "verificado", nullable = false)
    private Boolean verificado = false;

    @Column(name = "data_verificacao", columnDefinition = "TIMESTAMP WITH TIME ZONE")
    private OffsetDateTime dataVerificacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

