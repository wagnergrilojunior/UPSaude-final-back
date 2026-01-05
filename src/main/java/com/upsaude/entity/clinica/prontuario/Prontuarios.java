package com.upsaude.entity.clinica.prontuario;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.entity.farmacia.Receita;
import com.upsaude.entity.farmacia.Dispensacao;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

@Entity
@Table(name = "prontuarios", schema = "public",
       indexes = {
           @Index(name = "idx_prontuario_paciente", columnList = "paciente_id"),
           @Index(name = "idx_prontuario_atendimento", columnList = "atendimento_id"),
           @Index(name = "idx_prontuario_consulta", columnList = "consulta_id"),
           @Index(name = "idx_prontuario_receita", columnList = "receita_id"),
           @Index(name = "idx_prontuario_dispensacao", columnList = "dispensacao_id"),
           @Index(name = "idx_prontuario_tipo_data", columnList = "tipo_registro,data_registro")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Prontuarios extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "consulta_id")
    private Consultas consulta;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id")
    private Receita receita;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "dispensacao_id")
    private Dispensacao dispensacao;

    @Column(name = "tipo_registro", length = 100)
    private String tipoRegistro;

    @Column(name = "tipo_registro_enum", length = 50)
    private String tipoRegistroEnum;

    @Column(name = "data_registro")
    private OffsetDateTime dataRegistro;

    @Column(name = "resumo", columnDefinition = "TEXT")
    private String resumo;

    @Column(name = "conteudo", columnDefinition = "jsonb")
    private String conteudo;

    @Column(name = "criado_por")
    private java.util.UUID criadoPor;
}
