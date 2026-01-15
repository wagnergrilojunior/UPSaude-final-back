package com.upsaude.entity.financeiro;

import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.paciente.Paciente;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;
import com.upsaude.entity.sistema.usuario.UsuariosSistema;

@Entity
@Table(
    name = "guia_atendimento_ambulatorial",
    schema = "public",
    uniqueConstraints = {
        @UniqueConstraint(name = "uk_guia_ambulatorial_tenant_comp_num", columnNames = {"tenant_id", "competencia_id", "numero"})
    },
    indexes = {
        @Index(name = "idx_guia_ambulatorial_status", columnList = "tenant_id, competencia_id, status")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class GuiaAtendimentoAmbulatorial extends BaseEntityFinanceiro {

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
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "documento_faturamento_id")
    private DocumentoFaturamento documentoFaturamento;

    @Column(name = "numero", nullable = false, length = 100)
    private String numero;

    @Column(name = "status", nullable = false, length = 30)
private String status;

    @Column(name = "emitida_em")
    private OffsetDateTime emitidaEm;

    @Column(name = "cancelada_em")
    private OffsetDateTime canceladaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cancelada_por")
    private UsuariosSistema canceladaPor;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
