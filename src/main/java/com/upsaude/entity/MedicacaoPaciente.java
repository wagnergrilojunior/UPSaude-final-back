package com.upsaude.entity;

import com.upsaude.enums.FrequenciaMedicacaoEnum;
import com.upsaude.enums.ViaAdministracaoEnum;
import com.upsaude.util.converter.FrequenciaMedicacaoEnumConverter;
import com.upsaude.util.converter.ViaAdministracaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Entidade que representa a ligação entre um paciente e uma medicação contínua.
 * Armazena informações sobre dose, frequência, via, CID relacionado, período de uso e observações.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "medicacoes_paciente", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class MedicacaoPaciente extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "medicacao_id", nullable = false)
    private Medicacao medicacao;

    @Column(name = "dose", length = 100)
    private String dose;

    @Convert(converter = FrequenciaMedicacaoEnumConverter.class)
    @Column(name = "frequencia")
    private FrequenciaMedicacaoEnum frequencia;

    @Convert(converter = ViaAdministracaoEnumConverter.class)
    @Column(name = "via")
    private ViaAdministracaoEnum via;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cid_relacionado_id")
    private CidDoencas cidRelacionado;

    @Column(name = "data_inicio")
    private java.time.LocalDate dataInicio;

    @Column(name = "data_fim")
    private java.time.LocalDate dataFim;

    @Column(name = "medicacao_ativa", nullable = false)
    private Boolean medicacaoAtiva = true;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

