package com.upsaude.entity.paciente;
import com.upsaude.entity.BaseEntity;

import com.upsaude.enums.TipoResponsavelEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "paciente_responsavel_legal", schema = "public",
       indexes = {
           @Index(name = "idx_paciente_responsavel_legal_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_responsavel_legal_cpf", columnList = "cpf")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class ResponsavelLegal extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "cpf", length = 14)
    private String cpf;

    @Column(name = "telefone", length = 20)
    private String telefone;

    @Column(name = "rg", length = 20)
    private String rg;

    @Column(name = "cns", length = 15)
    private String cns;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "tipo_responsavel")
    private TipoResponsavelEnum tipoResponsavel;

    @Column(name = "autorizacao_responsavel", nullable = false)
    private Boolean autorizacaoResponsavel = false;
}
