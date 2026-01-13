package com.upsaude.entity.financeiro;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.AtendimentoProcedimento;
import com.upsaude.entity.paciente.Paciente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(
    name = "estorno_financeiro",
    schema = "public",
    indexes = {
        @Index(name = "idx_estorno_financeiro_competencia_motivo", columnList = "tenant_id, competencia_id, motivo"),
        @Index(name = "idx_estorno_financeiro_data", columnList = "tenant_id, data_estorno")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class EstornoFinanceiro extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "competencia_id", nullable = false)
    private CompetenciaFinanceira competencia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "guia_ambulatorial_id")
    private GuiaAtendimentoAmbulatorial guiaAmbulatorial;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_procedimento_id")
    private AtendimentoProcedimento atendimentoProcedimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_financeiro_origem_id")
    private LancamentoFinanceiro lancamentoFinanceiroOrigem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lancamento_financeiro_estorno_id")
    private LancamentoFinanceiro lancamentoFinanceiroEstorno;

    @Column(name = "motivo", nullable = false, length = 30)
    private String motivo; // CANCELAMENTO | FALTA_PACIENTE | NAO_EXECUTADO | AJUSTE | OUTRO

    @Column(name = "valor_estornado", nullable = false, precision = 14, scale = 2)
    private BigDecimal valorEstornado;

    @Column(name = "procedimento_codigo", length = 20)
    private String procedimentoCodigo; // Snapshot do código SIGTAP

    @Column(name = "procedimento_nome", length = 255)
    private String procedimentoNome; // Snapshot do nome

    @Column(name = "data_estorno", nullable = false)
    private OffsetDateTime dataEstorno;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
