package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.TipoContatoEnum;
import com.upsaude.util.converter.TipoContatoEnumConverter;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paciente_contato", schema = "public",
       indexes = {
           @Index(name = "idx_paciente_contato_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_contato_tipo", columnList = "tipo"),
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

    @Size(max = 255, message = "Nome deve ter no máximo 255 caracteres")
    @Column(name = "nome", length = 255)
    private String nome;

    @Size(max = 255, message = "Email deve ter no máximo 255 caracteres")
    @Column(name = "email", length = 255)
    private String email;

    @Size(max = 20, message = "Celular deve ter no máximo 20 caracteres")
    @Column(name = "celular", length = 20)
    private String celular;

    @Size(max = 20, message = "Telefone deve ter no máximo 20 caracteres")
    @Column(name = "telefone", length = 20)
    private String telefone;
}

