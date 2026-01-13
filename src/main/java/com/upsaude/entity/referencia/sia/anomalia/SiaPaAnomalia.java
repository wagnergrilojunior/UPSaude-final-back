package com.upsaude.entity.referencia.sia.anomalia;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "sia_pa_anomalia", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class SiaPaAnomalia extends BaseEntityWithoutTenant {

    @Column(name = "competencia", length = 6, nullable = false)
    private String competencia;

    @Column(name = "uf", length = 2, nullable = false)
    private String uf;

    @Column(name = "tipo_anomalia", length = 80, nullable = false)
    private String tipoAnomalia;

    @Column(name = "severidade", length = 20, nullable = false)
    private String severidade;

    @Column(name = "chave", length = 120)
    private String chave;

    @Column(name = "registro_id")
    private UUID registroId;

    @Column(name = "descricao", columnDefinition = "TEXT", nullable = false)
    private String descricao;

    @Column(name = "valor_atual", precision = 18, scale = 2)
    private BigDecimal valorAtual;

    @Column(name = "valor_referencia", precision = 18, scale = 2)
    private BigDecimal valorReferencia;

    @Column(name = "delta", precision = 18, scale = 2)
    private BigDecimal delta;

    @Column(name = "delta_pct", precision = 18, scale = 6)
    private BigDecimal deltaPct;

    @Column(name = "dados", columnDefinition = "jsonb")
    @JdbcTypeCode(SqlTypes.JSON)
    private String dados;
}

