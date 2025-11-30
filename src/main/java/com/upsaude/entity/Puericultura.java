package com.upsaude.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidade que representa o acompanhamento de puericultura (saúde da criança).
 * Armazena informações sobre o acompanhamento do crescimento e desenvolvimento infantil.
 * Conforme diretrizes do Ministério da Saúde para acompanhamento de crianças de 0 a 10 anos.
 *
 * @author UPSaúde
 */
@Entity
@Table(name = "puericultura", schema = "public",
       indexes = {
           @Index(name = "idx_puericultura_paciente", columnList = "paciente_id"),
           @Index(name = "idx_puericultura_data_inicio", columnList = "data_inicio_acompanhamento"),
           @Index(name = "idx_puericultura_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class Puericultura extends BaseEntity {

    // ========== RELACIONAMENTOS ==========

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente (criança) é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_responsavel_id")
    private ProfissionaisSaude profissionalResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude;

    // ========== DADOS DO NASCIMENTO ==========

    @Column(name = "data_nascimento")
    private LocalDate dataNascimento;

    @Column(name = "peso_nascimento", precision = 6, scale = 2)
    private BigDecimal pesoNascimento; // em gramas

    @Column(name = "comprimento_nascimento", precision = 4, scale = 1)
    private BigDecimal comprimentoNascimento; // em cm

    @Column(name = "perimetro_cefalico_nascimento", precision = 4, scale = 1)
    private BigDecimal perimetroCefalicoNascimento; // em cm

    @Column(name = "apgar_1_minuto")
    private Integer apgar1Minuto;

    @Column(name = "apgar_5_minutos")
    private Integer apgar5Minutos;

    @Size(max = 50, message = "Tipo de parto deve ter no máximo 50 caracteres")
    @Column(name = "tipo_parto", length = 50)
    private String tipoParto;

    @Column(name = "idade_gestacional_nascimento")
    private Integer idadeGestacionalNascimento; // em semanas

    @Column(name = "prematuro")
    private Boolean prematuro;

    // ========== DADOS DA MÃE ==========

    @Size(max = 255, message = "Nome da mãe deve ter no máximo 255 caracteres")
    @Column(name = "nome_mae", length = 255)
    private String nomeMae;

    @Size(max = 10, message = "Tipo sanguíneo da mãe deve ter no máximo 10 caracteres")
    @Column(name = "tipo_sanguineo_mae", length = 10)
    private String tipoSanguineoMae;

    @Column(name = "numero_consultas_pre_natal")
    private Integer numeroConsultasPreNatal;

    @Column(name = "intercorrencias_gestacao", columnDefinition = "TEXT")
    private String intercorrenciasGestacao;

    // ========== ALEITAMENTO ==========

    @Column(name = "aleitamento_materno_exclusivo")
    private Boolean aleitamentoMaternoExclusivo;

    @Column(name = "data_inicio_alimentacao_complementar")
    private LocalDate dataInicioAlimentacaoComplementar;

    @Column(name = "data_desmame")
    private LocalDate dataDesmame;

    @Size(max = 100, message = "Tipo de aleitamento deve ter no máximo 100 caracteres")
    @Column(name = "tipo_aleitamento_atual", length = 100)
    private String tipoAleitamentoAtual; // Exclusivo, Predominante, Complementado, Fórmula

    // ========== TRIAGEM NEONATAL ==========

    @Column(name = "teste_pezinho_realizado")
    private Boolean testePezinhoRealizado;

    @Column(name = "data_teste_pezinho")
    private LocalDate dataTestePezinho;

    @Size(max = 100, message = "Resultado do teste do pezinho deve ter no máximo 100 caracteres")
    @Column(name = "resultado_teste_pezinho", length = 100)
    private String resultadoTestePezinho;

    @Column(name = "teste_orelhinha_realizado")
    private Boolean testeOrelhinhaRealizado;

    @Column(name = "data_teste_orelhinha")
    private LocalDate dataTesteOrelhinha;

    @Size(max = 100, message = "Resultado do teste da orelhinha deve ter no máximo 100 caracteres")
    @Column(name = "resultado_teste_orelhinha", length = 100)
    private String resultadoTesteOrelhinha;

    @Column(name = "teste_olhinho_realizado")
    private Boolean testeOlhinhoRealizado;

    @Column(name = "data_teste_olhinho")
    private LocalDate dataTesteOlhinho;

    @Size(max = 100, message = "Resultado do teste do olhinho deve ter no máximo 100 caracteres")
    @Column(name = "resultado_teste_olhinho", length = 100)
    private String resultadoTesteOlhinho;

    @Column(name = "teste_coracaozinho_realizado")
    private Boolean testeCoracaoRealizado;

    @Column(name = "data_teste_coracaozinho")
    private LocalDate dataTesteCoracao;

    @Size(max = 100, message = "Resultado do teste do coraçãozinho deve ter no máximo 100 caracteres")
    @Column(name = "resultado_teste_coracaozinho", length = 100)
    private String resultadoTesteCoracao;

    @Column(name = "teste_linguinha_realizado")
    private Boolean testeLinguinhaRealizado;

    @Column(name = "data_teste_linguinha")
    private LocalDate dataTesteLinguinha;

    @Size(max = 100, message = "Resultado do teste da linguinha deve ter no máximo 100 caracteres")
    @Column(name = "resultado_teste_linguinha", length = 100)
    private String resultadoTesteLinguinha;

    // ========== STATUS DO ACOMPANHAMENTO ==========

    @Column(name = "data_inicio_acompanhamento")
    private LocalDate dataInicioAcompanhamento;

    @Column(name = "acompanhamento_ativo", nullable = false)
    private Boolean acompanhamentoAtivo = true;

    @Column(name = "data_encerramento")
    private LocalDate dataEncerramento;

    @Size(max = 100, message = "Motivo do encerramento deve ter no máximo 100 caracteres")
    @Column(name = "motivo_encerramento", length = 100)
    private String motivoEncerramento;

    // ========== INFORMAÇÕES ADICIONAIS ==========

    @Column(name = "antecedentes_familiares", columnDefinition = "TEXT")
    private String antecedentesFamiliares;

    @Column(name = "alergias_conhecidas", columnDefinition = "TEXT")
    private String alergiasConhecidas;

    @Column(name = "doencas_cronicas", columnDefinition = "TEXT")
    private String doencasCronicas;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}

