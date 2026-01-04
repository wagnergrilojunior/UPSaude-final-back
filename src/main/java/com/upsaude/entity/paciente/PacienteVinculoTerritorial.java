package com.upsaude.entity.paciente;

import com.upsaude.entity.BaseEntity;
import com.upsaude.enums.OrigemVinculoTerritorialEnum;
import com.upsaude.util.converter.OrigemVinculoTerritorialEnumConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "paciente_vinculo_territorial", schema = "public",
       indexes = {
           @Index(name = "idx_paciente_vinculo_territorial_paciente", columnList = "paciente_id"),
           @Index(name = "idx_paciente_vinculo_territorial_ativo", columnList = "paciente_id, ativo"),
           @Index(name = "idx_paciente_vinculo_territorial_cnes", columnList = "cnes_estabelecimento"),
           @Index(name = "idx_paciente_vinculo_territorial_ine", columnList = "ine_equipe"),
           @Index(name = "idx_paciente_vinculo_territorial_microarea", columnList = "microarea"),
           @Index(name = "idx_paciente_vinculo_territorial_origem", columnList = "origem"),
           @Index(name = "idx_paciente_vinculo_territorial_data_inicio", columnList = "data_inicio"),
           @Index(name = "idx_paciente_vinculo_territorial_tenant", columnList = "tenant_id")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class PacienteVinculoTerritorial extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "municipio_ibge", length = 7)
    private String municipioIbge;

    @Column(name = "cnes_estabelecimento", length = 7)
    private String cnesEstabelecimento;

    @Column(name = "ine_equipe", length = 10)
    private String ineEquipe;

    @Column(name = "microarea", length = 10)
    private String microarea;

    @Column(name = "data_inicio", nullable = false)
    private LocalDate dataInicio;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Convert(converter = OrigemVinculoTerritorialEnumConverter.class)
    @Column(name = "origem")
    private OrigemVinculoTerritorialEnum origem;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

