package com.upsaude.entity.financeiro;

import com.upsaude.entity.sistema.usuario.UsuariosSistema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(
    name = "conciliacao_bancaria",
    schema = "public",
    indexes = {
        @Index(name = "idx_conciliacao_bancaria_conta", columnList = "conta_financeira_id")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class ConciliacaoBancaria extends BaseEntityFinanceiro {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conta_financeira_id", nullable = false)
    private ContaFinanceira contaFinanceira;

    @Column(name = "periodo_inicio", nullable = false)
    private LocalDate periodoInicio;

    @Column(name = "periodo_fim", nullable = false)
    private LocalDate periodoFim;

    @Column(name = "status", nullable = false, length = 20)
private String status;

    @Column(name = "fechada_em")
    private OffsetDateTime fechadaEm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fechada_por")
    private UsuariosSistema fechadaPor;

    @OneToMany(mappedBy = "conciliacao", fetch = FetchType.LAZY)
    private List<ConciliacaoItem> itens = new ArrayList<>();
}
