package com.upsaude.entity.cnes;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.referencia.sigtap.SigtapTipoLeito;
import com.upsaude.enums.StatusLeitoEnum;
import com.upsaude.util.converter.StatusLeitoEnumConverter;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.OffsetDateTime;

@Entity
@Table(name = "leitos", schema = "public", indexes = {
        @Index(name = "idx_leitos_codigo_cnes", columnList = "codigo_cnes_leito"),
        @Index(name = "idx_leitos_estabelecimento", columnList = "estabelecimento_id"),
        @Index(name = "idx_leitos_status", columnList = "status")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Leitos extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @Column(name = "codigo_cnes_leito", length = 50)
    private String codigoCnesLeito;

    @Column(name = "numero_leito", length = 50)
    private String numeroLeito;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_leito_id")
    private SigtapTipoLeito tipoLeito;

    @Column(name = "setor_unidade", length = 255)
    private String setorUnidade;

    @Column(name = "andar", length = 50)
    private String andar;

    @Column(name = "sala", length = 50)
    private String sala;

    @Convert(converter = StatusLeitoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusLeitoEnum status;

    @Column(name = "data_ativacao", nullable = false)
    private OffsetDateTime dataAtivacao;

    @Column(name = "data_inativacao")
    private OffsetDateTime dataInativacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

}
