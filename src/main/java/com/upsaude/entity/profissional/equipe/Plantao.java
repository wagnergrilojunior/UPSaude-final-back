package com.upsaude.entity.profissional.equipe;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.enums.TipoPlantaoEnum;
import com.upsaude.util.converter.TipoPlantaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "plantoes", schema = "public",
       indexes = {
           @Index(name = "idx_plantao_profissional", columnList = "profissional_id"),
           @Index(name = "idx_plantao_data_hora_inicio", columnList = "data_hora_inicio"),
           @Index(name = "idx_plantao_tipo", columnList = "tipo_plantao"),
           @Index(name = "idx_plantao_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_plantao_periodo", columnList = "data_hora_inicio,data_hora_fim")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Plantao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @Column(name = "data_hora_inicio", nullable = false)
    private OffsetDateTime dataHoraInicio;

    @Column(name = "data_hora_fim")
    private OffsetDateTime dataHoraFim;

    @Column(name = "data_hora_fim_previsto", nullable = false)
    private OffsetDateTime dataHoraFimPrevisto;

    @Convert(converter = TipoPlantaoEnumConverter.class)
    @Column(name = "tipo_plantao", nullable = false)
    private TipoPlantaoEnum tipoPlantao;

    @Column(name = "setor", length = 255)
    private String setor;

    @Column(name = "leito_inicio")
    private Integer leitoInicio;

    @Column(name = "leito_fim")
    private Integer leitoFim;

    @Column(name = "valor_plantao", precision = 10, scale = 2)
    private java.math.BigDecimal valorPlantao;

    @Column(name = "tem_hora_extra")
    private Boolean temHoraExtra;

    @Column(name = "valor_hora_extra", precision = 10, scale = 2)
    private java.math.BigDecimal valorHoraExtra;

    @Column(name = "confirmado")
    private Boolean confirmado;

    @Column(name = "data_confirmacao")
    private OffsetDateTime dataConfirmacao;

    @Column(name = "cancelado")
    private Boolean cancelado;

    @Column(name = "data_cancelamento")
    private OffsetDateTime dataCancelamento;

    @Column(name = "motivo_cancelamento", columnDefinition = "TEXT")
    private String motivoCancelamento;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
