package com.upsaude.entity.cnes;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.UUID;

@Entity
@Table(name = "cnes_historico_estabelecimento", schema = "public", uniqueConstraints = {
        @UniqueConstraint(name = "uk_cnes_historico_estabelecimento_competencia", columnNames = { "estabelecimento_id",
                "competencia" })
}, indexes = {
        @Index(name = "idx_cnes_historico_estabelecimento_id", columnList = "estabelecimento_id"),
        @Index(name = "idx_cnes_historico_competencia", columnList = "competencia"),
        @Index(name = "idx_cnes_historico_data_sincronizacao", columnList = "data_sincronizacao")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class CnesHistoricoEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @Column(name = "competencia", nullable = false, length = 6)
    private String competencia;

    @Column(name = "dados_jsonb", columnDefinition = "jsonb", nullable = false)
    @JdbcTypeCode(SqlTypes.JSON)
    private String dadosJsonb;

    @Column(name = "data_sincronizacao", nullable = false)
    private OffsetDateTime dataSincronizacao;
}
