package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.IdentidadeGeneroEnum;
import com.upsaude.enums.OrientacaoSexualEnum;
import com.upsaude.util.converter.IdentidadeGeneroEnumConverter;
import com.upsaude.util.converter.OrientacaoSexualEnumConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "paciente_dados_pessoais_complementares", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_paciente_dados_pessoais_complementares_paciente", 
               columnNames = {"paciente_id"})
       },
       indexes = {
           @Index(name = "idx_paciente_dados_pessoais_complementares_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_dados_pessoais_complementares_tenant", columnList = "tenant_id"),
           @Index(name = "idx_paciente_dados_pessoais_complementares_ativo", columnList = "ativo")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PacienteDadosPessoaisComplementares extends BaseEntity {

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false, unique = true)
    private Paciente paciente;

    @Column(name = "nome_mae", length = 100)
    private String nomeMae;

    @Column(name = "nome_pai", length = 100)
    private String nomePai;

    @Convert(converter = IdentidadeGeneroEnumConverter.class)
    @Column(name = "identidade_genero")
    private IdentidadeGeneroEnum identidadeGenero;

    @Convert(converter = OrientacaoSexualEnumConverter.class)
    @Column(name = "orientacao_sexual")
    private OrientacaoSexualEnum orientacaoSexual;
}

