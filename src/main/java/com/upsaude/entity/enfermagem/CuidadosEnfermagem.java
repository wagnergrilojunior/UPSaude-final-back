package com.upsaude.entity.enfermagem;
import com.upsaude.entity.BaseEntity;

import com.upsaude.entity.profissional.ProfissionaisSaude;

import com.upsaude.entity.clinica.atendimento.Atendimento;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.enums.TipoCuidadoEnfermagemEnum;
import com.upsaude.util.converter.TipoCuidadoEnfermagemEnumConverter;
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

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@Entity
@Table(name = "cuidados_enfermagem", schema = "public",
       indexes = {
           @Index(name = "idx_cuidados_enf_paciente", columnList = "paciente_id"),
           @Index(name = "idx_cuidados_enf_profissional", columnList = "profissional_id"),
           @Index(name = "idx_cuidados_enf_tipo", columnList = "tipo_cuidado"),
           @Index(name = "idx_cuidados_enf_data", columnList = "data_hora"),
           @Index(name = "idx_cuidados_enf_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class CuidadosEnfermagem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional de saúde é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "atendimento_id")
    private Atendimento atendimento;

    @Convert(converter = TipoCuidadoEnfermagemEnumConverter.class)
    @Column(name = "tipo_cuidado", nullable = false)
    @NotNull(message = "Tipo de cuidado é obrigatório")
    private TipoCuidadoEnfermagemEnum tipoCuidado;

    @Size(max = 255, message = "Descrição do procedimento deve ter no máximo 255 caracteres")
    @Column(name = "descricao_procedimento", length = 255)
    private String descricaoProcedimento;

    @Column(name = "data_hora", nullable = false)
    @NotNull(message = "Data e hora são obrigatórios")
    private OffsetDateTime dataHora;

    @Column(name = "pressao_sistolica")
    private Integer pressaoSistolica;

    @Column(name = "pressao_diastolica")
    private Integer pressaoDiastolica;

    @Column(name = "frequencia_cardiaca")
    private Integer frequenciaCardiaca;

    @Column(name = "frequencia_respiratoria")
    private Integer frequenciaRespiratoria;

    @Column(name = "temperatura", precision = 4, scale = 1)
    private BigDecimal temperatura;

    @Column(name = "saturacao_oxigenio")
    private Integer saturacaoOxigenio;

    @Column(name = "glicemia_capilar")
    private Integer glicemiaCapilar;

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "altura", precision = 3, scale = 2)
    private BigDecimal altura;

    @Size(max = 100, message = "Localização deve ter no máximo 100 caracteres")
    @Column(name = "localizacao_ferida", length = 100)
    private String localizacaoFerida;

    @Size(max = 50, message = "Tipo de ferida deve ter no máximo 50 caracteres")
    @Column(name = "tipo_ferida", length = 50)
    private String tipoFerida;

    @Size(max = 50, message = "Tamanho da ferida deve ter no máximo 50 caracteres")
    @Column(name = "tamanho_ferida", length = 50)
    private String tamanhoFerida;

    @Column(name = "aspecto_ferida", columnDefinition = "TEXT")
    private String aspectoFerida;

    @Column(name = "secrecao_presente")
    private Boolean secrecaoPresente;

    @Size(max = 100, message = "Tipo de secreção deve ter no máximo 100 caracteres")
    @Column(name = "tipo_secrecao", length = 100)
    private String tipoSecrecao;

    @Column(name = "material_utilizado", columnDefinition = "TEXT")
    private String materialUtilizado;

    @Size(max = 255, message = "Medicamento deve ter no máximo 255 caracteres")
    @Column(name = "medicamento_administrado", length = 255)
    private String medicamentoAdministrado;

    @Size(max = 50, message = "Dose deve ter no máximo 50 caracteres")
    @Column(name = "dose", length = 50)
    private String dose;

    @Size(max = 50, message = "Via de administração deve ter no máximo 50 caracteres")
    @Column(name = "via_administracao", length = 50)
    private String viaAdministracao;

    @Size(max = 50, message = "Local de aplicação deve ter no máximo 50 caracteres")
    @Column(name = "local_aplicacao", length = 50)
    private String localAplicacao;

    @Size(max = 50, message = "Lote deve ter no máximo 50 caracteres")
    @Column(name = "lote_medicamento", length = 50)
    private String loteMedicamento;

    @Size(max = 100, message = "Tipo de teste deve ter no máximo 100 caracteres")
    @Column(name = "tipo_teste_rapido", length = 100)
    private String tipoTesteRapido;

    @Size(max = 50, message = "Resultado do teste deve ter no máximo 50 caracteres")
    @Column(name = "resultado_teste", length = 50)
    private String resultadoTeste;

    @Size(max = 50, message = "Lote do teste deve ter no máximo 50 caracteres")
    @Column(name = "lote_teste", length = 50)
    private String loteTeste;

    @Column(name = "queixa_paciente", columnDefinition = "TEXT")
    private String queixaPaciente;

    @Column(name = "evolucao", columnDefinition = "TEXT")
    private String evolucao;

    @Column(name = "intercorrencias", columnDefinition = "TEXT")
    private String intercorrencias;

    @Column(name = "reacao_adversa")
    private Boolean reacaoAdversa;

    @Column(name = "descricao_reacao", columnDefinition = "TEXT")
    private String descricaoReacao;

    @Column(name = "orientacoes", columnDefinition = "TEXT")
    private String orientacoes;

    @Column(name = "necessita_retorno")
    private Boolean necessitaRetorno;

    @Column(name = "data_retorno")
    private OffsetDateTime dataRetorno;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;
}
