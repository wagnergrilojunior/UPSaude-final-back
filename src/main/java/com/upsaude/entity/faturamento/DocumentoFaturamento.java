package com.upsaude.entity.faturamento;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(
    name = "documento_faturamento",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_documento_faturamento_tenant_tipo_comp_num", columnNames = {"tenant_id", "tipo", "competencia_id", "numero", "serie"})
    },
    indexes = {
        @Index(name = "idx_documento_faturamento_status", columnList = "tenant_id, competencia_id, status")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class DocumentoFaturamento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private CompetenciaFinanceira competencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "convenio_id")
    private Convenio convenio;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_ambulatorial_id")
    private com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial guiaAmbulatorial;

    @Column(name = "tipo", nullable = false, length = 50)
    private String tipo; 

    @Column(name = "numero", nullable = false, length = 100)
    private String numero;

    @Column(name = "serie", length = 20)
    private String serie;

    @Column(name = "status", nullable = false, length = 30)
    private String status; 

    @Column(name = "pagador_tipo", length = 30)
    private String pagadorTipo; 

    @Column(name = "emitido_em")
    private OffsetDateTime emitidoEm;

    @Column(name = "cancelado_em")
    private OffsetDateTime canceladoEm;

    @Column(name = "cancelado_por")
    private UUID canceladoPor;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "payload_layout", columnDefinition = "jsonb")
    private String payloadLayout; 

    @OneToMany(mappedBy = "documento", fetch = FetchType.LAZY)
    private List<DocumentoFaturamentoItem> itens = new ArrayList<>();
}
