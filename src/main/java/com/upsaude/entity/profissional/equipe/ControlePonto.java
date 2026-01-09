package com.upsaude.entity.profissional.equipe;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.enums.TipoPontoEnum;
import com.upsaude.util.converter.TipoPontoEnumConverter;
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
@Table(name = "controle_ponto", schema = "public",
       indexes = {
           @Index(name = "idx_controle_ponto_profissional", columnList = "profissional_id"),
           @Index(name = "idx_controle_ponto_data_hora", columnList = "data_hora"),
           @Index(name = "idx_controle_ponto_tipo", columnList = "tipo_ponto"),
           @Index(name = "idx_controle_ponto_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_controle_ponto_data", columnList = "data_ponto")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class ControlePonto extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @Column(name = "data_hora", nullable = false)
    private OffsetDateTime dataHora;

    @Column(name = "data_ponto", nullable = false)
    private java.time.LocalDate dataPonto;

    @Convert(converter = TipoPontoEnumConverter.class)
    @Column(name = "tipo_ponto", nullable = false)
    private TipoPontoEnum tipoPonto;

    @Column(name = "latitude")
    private Double latitude;

    @Column(name = "longitude")
    private Double longitude;

    @Column(name = "endereco_ip", length = 45)
    private String enderecoIp;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "justificativa", columnDefinition = "TEXT")
    private String justificativa;

    @Column(name = "aprovado")
    private Boolean aprovado;

    @Column(name = "aprovado_por")
    private java.util.UUID aprovadoPor;

    @Column(name = "data_aprovacao")
    private OffsetDateTime dataAprovacao;
}
