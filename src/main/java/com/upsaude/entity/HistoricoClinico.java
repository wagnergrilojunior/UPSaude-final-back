package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.OffsetDateTime;

/**
 * Entidade que representa um registro no histórico clínico do paciente.
 * Armazena todas as informações relevantes do histórico do paciente de forma consolidada.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "historico_clinico", schema = "public",
       indexes = {
           @Index(name = "idx_historico_paciente", columnList = "paciente_id"),
           @Index(name = "idx_historico_data", columnList = "data_registro"),
           @Index(name = "idx_historico_tipo", columnList = "tipo_registro"),
           @Index(name = "idx_historico_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_historico_profissional", columnList = "profissional_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class HistoricoClinico extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id")
    private ProfissionaisSaude profissional; // Profissional que registrou

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento; // Link para atendimento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "agendamento_id")
    private Agendamento agendamento; // Link para agendamento

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "exame_id")
    private Exames exame; // Link para exame

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "receita_id")
    private ReceitasMedicas receita; // Link para receita

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cirurgia_id")
    private Cirurgia cirurgia; // Link para cirurgia

    // ========== DADOS DO REGISTRO ==========

    @Column(name = "data_registro", nullable = false)
    @NotNull(message = "Data do registro é obrigatória")
    private OffsetDateTime dataRegistro;

    @Column(name = "tipo_registro", nullable = false, length = 50)
    @NotNull(message = "Tipo de registro é obrigatório")
    private String tipoRegistro; // CONSULTA, EXAME, PROCEDIMENTO, MEDICACAO, CIRURGIA, OBSERVACAO, etc.

    @Column(name = "titulo", length = 255)
    private String titulo; // Título do registro

    @Column(name = "descricao", nullable = false, columnDefinition = "TEXT")
    @NotNull(message = "Descrição é obrigatória")
    private String descricao; // Descrição completa do registro

    // ========== INFORMAÇÕES COMPLEMENTARES ==========

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas; // Observações internas (não visíveis ao paciente)

    @Column(name = "tags", length = 500)
    private String tags; // Tags para busca/filtro (separadas por vírgula)

    // ========== AUDITORIA ==========

    @Column(name = "registrado_por")
    private java.util.UUID registradoPor; // ID do usuário que registrou

    @Column(name = "revisado_por")
    private java.util.UUID revisadoPor; // ID do usuário que revisou

    @Column(name = "data_revisao")
    private OffsetDateTime dataRevisao;

    @Column(name = "versao")
    private Integer versao; // Versão do registro (para controle de alterações)

    // ========== VISIBILIDADE ==========

    @Column(name = "visivel_para_paciente")
    private Boolean visivelParaPaciente; // Se é visível para o paciente no portal

    @Column(name = "compartilhado_outros_estabelecimentos")
    private Boolean compartilhadoOutrosEstabelecimentos; // Se é compartilhado com outros estabelecimentos
}

