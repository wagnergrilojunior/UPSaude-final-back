package com.upsaude.entity;

import com.upsaude.enums.TipoMetodoContraceptivoEnum;
import com.upsaude.util.converter.TipoMetodoContraceptivoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
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

import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "planejamento_familiar", schema = "public",
       indexes = {
           @Index(name = "idx_planejamento_paciente", columnList = "paciente_id"),
           @Index(name = "idx_planejamento_metodo", columnList = "metodo_atual"),
           @Index(name = "idx_planejamento_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class PlanejamentoFamiliar extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_responsavel_id")
    private ProfissionaisSaude profissionalResponsavel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude;

    @Convert(converter = TipoMetodoContraceptivoEnumConverter.class)
    @Column(name = "metodo_atual")
    private TipoMetodoContraceptivoEnum metodoAtual;

    @Column(name = "data_inicio_metodo")
    private LocalDate dataInicioMetodo;

    @Size(max = 255, message = "Nome comercial deve ter no máximo 255 caracteres")
    @Column(name = "nome_comercial_metodo", length = 255)
    private String nomeComercialMetodo;

    @Column(name = "metodo_anterior")
    @Convert(converter = TipoMetodoContraceptivoEnumConverter.class)
    private TipoMetodoContraceptivoEnum metodoAnterior;

    @Column(name = "motivo_troca_metodo", columnDefinition = "TEXT")
    private String motivoTrocaMetodo;

    @Column(name = "numero_gestacoes")
    private Integer numeroGestacoes;

    @Column(name = "numero_partos")
    private Integer numeroPartos;

    @Column(name = "numero_abortos")
    private Integer numeroAbortos;

    @Column(name = "numero_filhos_vivos")
    private Integer numeroFilhosVivos;

    @Column(name = "data_ultimo_parto")
    private LocalDate dataUltimoParto;

    @Column(name = "ultima_gestacao_planejada")
    private Boolean ultimaGestacaoPlanejada;

    @Column(name = "deseja_engravidar")
    private Boolean desejaEngravidar;

    @Column(name = "prazo_desejo_gestacao")
    @Size(max = 50, message = "Prazo deve ter no máximo 50 caracteres")
    private String prazoDesejoGestacao;

    @Column(name = "deseja_metodo_definitivo")
    private Boolean desejaMetodoDefinitivo;

    @Column(name = "tem_contraindicacoes")
    private Boolean temContraindicacoes;

    @Column(name = "contraindicacoes", columnDefinition = "TEXT")
    private String contraindicacoes;

    @Column(name = "doencas_preexistentes", columnDefinition = "TEXT")
    private String doencasPreexistentes;

    @Column(name = "medicamentos_uso", columnDefinition = "TEXT")
    private String medicamentosUso;

    @Column(name = "alergias", columnDefinition = "TEXT")
    private String alergias;

    @Column(name = "ciclo_menstrual_regular")
    private Boolean cicloMenstrualRegular;

    @Column(name = "duracao_ciclo")
    private Integer duracaoCiclo;

    @Column(name = "data_ultima_menstruacao")
    private LocalDate dataUltimaMenstruacao;

    @Column(name = "dismenorreia")
    private Boolean dismenorreia;

    @Column(name = "sangramento_irregular")
    private Boolean sangramentoIrregular;

    @Column(name = "data_inicio_acompanhamento")
    private LocalDate dataInicioAcompanhamento;

    @Column(name = "acompanhamento_ativo", nullable = false)
    private Boolean acompanhamentoAtivo = true;

    @Column(name = "data_proxima_consulta")
    private OffsetDateTime dataProximaConsulta;

    @Column(name = "data_proxima_dispensacao")
    private LocalDate dataProximaDispensacao;

    @Column(name = "data_insercao_diu")
    private LocalDate dataInsercaoDiu;

    @Column(name = "data_validade_diu")
    private LocalDate dataValidadeDiu;

    @Column(name = "data_cirurgia")
    private LocalDate dataCirurgia;

    @Size(max = 255, message = "Local da cirurgia deve ter no máximo 255 caracteres")
    @Column(name = "local_cirurgia", length = 255)
    private String localCirurgia;

    @Column(name = "documentacao_completa")
    private Boolean documentacaoCompleta;

    @Column(name = "prazo_minimo_cumprido")
    private Boolean prazoMinimoCumprido;

    @Column(name = "orientacao_metodos_realizada")
    private Boolean orientacaoMetodosRealizada;

    @Column(name = "data_orientacao")
    private LocalDate dataOrientacao;

    @Column(name = "consentimento_informado")
    private Boolean consentimentoInformado;

    @Column(name = "data_consentimento")
    private LocalDate dataConsentimento;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
