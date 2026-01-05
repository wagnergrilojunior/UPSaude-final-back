package com.upsaude.entity.farmacia;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "dispensacoes", schema = "public", indexes = {
        @Index(name = "idx_dispensacao_paciente", columnList = "paciente_id"),
        @Index(name = "idx_dispensacao_farmacia", columnList = "farmacia_id"),
        @Index(name = "idx_dispensacao_receita", columnList = "receita_id"),
        @Index(name = "idx_dispensacao_data", columnList = "data_dispensacao"),
        @Index(name = "idx_dispensacao_numero", columnList = "numero_dispensacao"),
        @Index(name = "idx_dispensacao_estabelecimento", columnList = "estabelecimento_id")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Dispensacao extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "farmacia_id", nullable = false)
    private Farmacia farmacia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id")
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_saude_id")
    private ProfissionaisSaude profissionalSaude;

    @OneToMany(mappedBy = "dispensacao", fetch = FetchType.LAZY)
    private List<DispensacaoItem> itens = new ArrayList<>();

    @Column(name = "numero_dispensacao", length = 50)
    private String numeroDispensacao;

    @Column(name = "data_dispensacao", nullable = false)
    private LocalDateTime dataDispensacao;

    @Column(name = "tipo_dispensacao", length = 30)
    private String tipoDispensacao;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

