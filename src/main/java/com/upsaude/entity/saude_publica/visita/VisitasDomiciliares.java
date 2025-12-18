package com.upsaude.entity.saude_publica.visita;
import java.math.BigDecimal;
import java.time.OffsetDateTime;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.profissional.ProfissionaisSaude;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.enums.TipoVisitaDomiciliarEnum;
import com.upsaude.util.converter.TipoVisitaDomiciliarEnumConverter;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "visitas_domiciliares", schema = "public",
       indexes = {
           @Index(name = "idx_visita_paciente", columnList = "paciente_id"),
           @Index(name = "idx_visita_profissional", columnList = "profissional_id"),
           @Index(name = "idx_visita_data", columnList = "data_visita"),
           @Index(name = "idx_visita_tipo", columnList = "tipo_visita"),
           @Index(name = "idx_visita_estabelecimento", columnList = "estabelecimento_id")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class VisitasDomiciliares extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "paciente_id", nullable = false)
    @NotNull(message = "Paciente é obrigatório")
    private Paciente paciente;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "profissional_id", nullable = false)
    @NotNull(message = "Profissional é obrigatório")
    private ProfissionaisSaude profissional;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipe_saude_id")
    private EquipeSaude equipeSaude;

    @Convert(converter = TipoVisitaDomiciliarEnumConverter.class)
    @Column(name = "tipo_visita", nullable = false)
    @NotNull(message = "Tipo de visita é obrigatório")
    private TipoVisitaDomiciliarEnum tipoVisita;

    @Column(name = "data_visita", nullable = false)
    @NotNull(message = "Data da visita é obrigatória")
    private OffsetDateTime dataVisita;

    @Size(max = 20, message = "Turno deve ter no máximo 20 caracteres")
    @Column(name = "turno", length = 20)
    private String turno;

    @Column(name = "duracao_minutos")
    @Min(value = 0, message = "Duração não pode ser negativa")
    private Integer duracaoMinutos;

    @Size(max = 500, message = "Endereço deve ter no máximo 500 caracteres")
    @Column(name = "endereco", length = 500)
    private String endereco;

    @Size(max = 100, message = "Bairro deve ter no máximo 100 caracteres")
    @Column(name = "bairro", length = 100)
    private String bairro;

    @Size(max = 20, message = "CEP deve ter no máximo 20 caracteres")
    @Column(name = "cep", length = 20)
    private String cep;

    @Column(name = "latitude", precision = 10, scale = 8)
    private BigDecimal latitude;

    @Column(name = "longitude", precision = 11, scale = 8)
    private BigDecimal longitude;

    @Column(name = "motivo", columnDefinition = "TEXT")
    private String motivo;

    @Column(name = "busca_ativa")
    private Boolean buscaAtiva = false;

    @Column(name = "acompanhamento_programado")
    private Boolean acompanhamentoProgramado = false;

    @Column(name = "visita_realizada", nullable = false)
    private Boolean visitaRealizada = true;

    @Size(max = 50, message = "Motivo não realização deve ter no máximo 50 caracteres")
    @Column(name = "motivo_nao_realizacao", length = 50)
    private String motivoNaoRealizacao;

    @Column(name = "pessoa_presente", nullable = false)
    private Boolean pessoaPresente = true;

    @Size(max = 100, message = "Nome do informante deve ter no máximo 100 caracteres")
    @Column(name = "informante_nome", length = 100)
    private String informanteNome;

    @Size(max = 50, message = "Parentesco deve ter no máximo 50 caracteres")
    @Column(name = "informante_parentesco", length = 50)
    private String informanteParentesco;

    @Column(name = "acompanhamento_gestante")
    private Boolean acompanhamentoGestante = false;

    @Column(name = "acompanhamento_puerpera")
    private Boolean acompanhamentoPuerpera = false;

    @Column(name = "acompanhamento_recem_nascido")
    private Boolean acompanhamentoRecemNascido = false;

    @Column(name = "acompanhamento_crianca")
    private Boolean acompanhamentoCrianca = false;

    @Column(name = "acompanhamento_desnutricao")
    private Boolean acompanhamentoDesnutricao = false;

    @Column(name = "acompanhamento_reabilitacao")
    private Boolean acompanhamentoReabilitacao = false;

    @Column(name = "acompanhamento_hipertensao")
    private Boolean acompanhamentoHipertensao = false;

    @Column(name = "acompanhamento_diabetes")
    private Boolean acompanhamentoDiabetes = false;

    @Column(name = "acompanhamento_asma")
    private Boolean acompanhamentoAsma = false;

    @Column(name = "acompanhamento_dpoc")
    private Boolean acompanhamentoDpoc = false;

    @Column(name = "acompanhamento_cancer")
    private Boolean acompanhamentoCancer = false;

    @Column(name = "acompanhamento_saude_mental")
    private Boolean acompanhamentoSaudeMental = false;

    @Column(name = "acompanhamento_acamado")
    private Boolean acompanhamentoAcamado = false;

    @Column(name = "acompanhamento_domiciliado")
    private Boolean acompanhamentoDomiciliado = false;

    @Column(name = "acompanhamento_tabagismo")
    private Boolean acompanhamentoTabagismo = false;

    @Column(name = "acompanhamento_alcool")
    private Boolean acompanhamentoAlcool = false;

    @Column(name = "acompanhamento_drogas")
    private Boolean acompanhamentoDrogas = false;

    @Column(name = "pressao_sistolica")
    private Integer pressaoSistolica;

    @Column(name = "pressao_diastolica")
    private Integer pressaoDiastolica;

    @Column(name = "glicemia_capilar")
    private Integer glicemiaCapilar;

    @Column(name = "temperatura", precision = 4, scale = 1)
    private BigDecimal temperatura;

    @Column(name = "peso", precision = 5, scale = 2)
    private BigDecimal peso;

    @Column(name = "curativo_realizado")
    private Boolean curativoRealizado = false;

    @Column(name = "medicamento_administrado")
    private Boolean medicamentoAdministrado = false;

    @Column(name = "coleta_exame_realizada")
    private Boolean coletaExameRealizada = false;

    @Column(name = "orientacao_realizada")
    private Boolean orientacaoRealizada = false;

    @Column(name = "vacina_aplicada")
    private Boolean vacinaAplicada = false;

    @Column(name = "procedimentos_detalhes", columnDefinition = "TEXT")
    private String procedimentosDetalhes;

    @Column(name = "encaminhamento_ubs")
    private Boolean encaminhamentoUbs = false;

    @Column(name = "encaminhamento_upa")
    private Boolean encaminhamentoUpa = false;

    @Column(name = "encaminhamento_hospital")
    private Boolean encaminhamentoHospital = false;

    @Column(name = "encaminhamento_especialista")
    private Boolean encaminhamentoEspecialista = false;

    @Column(name = "encaminhamento_detalhes", columnDefinition = "TEXT")
    private String encaminhamentoDetalhes;

    @Column(name = "desfecho", columnDefinition = "TEXT")
    private String desfecho;

    @Column(name = "necessita_nova_visita")
    private Boolean necessitaNovaVisita = false;

    @Column(name = "data_proxima_visita")
    private OffsetDateTime dataProximaVisita;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "observacoes_internas", columnDefinition = "TEXT")
    private String observacoesInternas;
}
