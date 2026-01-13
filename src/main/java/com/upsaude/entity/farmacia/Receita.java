package com.upsaude.entity.farmacia;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.enums.TipoControleMedicamentoEnum;
import com.upsaude.util.converter.TipoControleMedicamentoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "receitas", schema = "public", indexes = {
        @Index(name = "idx_receita_paciente", columnList = "paciente_id"),
        @Index(name = "idx_receita_medico", columnList = "medico_id"),
        @Index(name = "idx_receita_data_prescricao", columnList = "data_prescricao"),
        @Index(name = "idx_receita_numero", columnList = "numero_receita"),
        @Index(name = "idx_receita_estabelecimento", columnList = "estabelecimento_id")
})
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Receita extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private Consulta consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private com.upsaude.entity.clinica.atendimento.Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medico_id")
    private Medicos medico;

    @OneToMany(mappedBy = "receita", fetch = FetchType.LAZY)
    private List<ReceitaItem> itens = new ArrayList<>();

    @Column(name = "numero_receita", length = 50)
    private String numeroReceita;

    @Column(name = "data_prescricao", nullable = false)
    private LocalDate dataPrescricao;

    @Column(name = "data_validade")
    private LocalDate dataValidade;

    @Convert(converter = TipoControleMedicamentoEnumConverter.class)
    @Column(name = "tipo_receita")
    private TipoControleMedicamentoEnum tipoReceita;

    @Column(name = "fhir_status", length = 20)
    private String fhirStatus;

    @Column(name = "intent", length = 20)
    private String intent;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
