package com.upsaude.entity;

import com.upsaude.enums.StatusManutencaoEnum;
import com.upsaude.util.converter.StatusManutencaoEnumConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.OffsetDateTime;

@Entity
@Table(name = "equipamentos_estabelecimento", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_equipamento_estabelecimento_serie", columnNames = {"equipamento_id", "estabelecimento_id", "numero_serie"})
       },
       indexes = {
           @Index(name = "idx_equipamento_estabelecimento_estabelecimento", columnList = "estabelecimento_id"),
           @Index(name = "idx_equipamento_estabelecimento_equipamento", columnList = "equipamento_id"),
           @Index(name = "idx_equipamento_estabelecimento_numero_serie", columnList = "numero_serie"),
           @Index(name = "idx_equipamento_estabelecimento_status_manutencao", columnList = "status_manutencao"),
           @Index(name = "idx_equipamento_estabelecimento_setor", columnList = "setor_departamento")
       })
@Data
@EqualsAndHashCode(callSuper = true)
public class EquipamentosEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    @NotNull(message = "Estabelecimento é obrigatório")
    private Estabelecimentos estabelecimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false)
    @NotNull(message = "Equipamento é obrigatório")
    private Equipamentos equipamento;

    @Size(max = 50, message = "Código interno do estabelecimento deve ter no máximo 50 caracteres")
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Size(max = 50, message = "Número de série deve ter no máximo 50 caracteres")
    @Column(name = "numero_serie", length = 50)
    private String numeroSerie;

    @Size(max = 50, message = "Número de patrimônio deve ter no máximo 50 caracteres")
    @Column(name = "numero_patrimonio", length = 50)
    private String numeroPatrimonio;

    @Size(max = 50, message = "Número de tombo deve ter no máximo 50 caracteres")
    @Column(name = "numero_tombo", length = 50)
    private String numeroTombo;

    @Column(name = "quantidade", nullable = false)
    @NotNull(message = "Quantidade é obrigatória")
    private Integer quantidade = 1;

    @Column(name = "quantidade_disponivel")
    private Integer quantidadeDisponivel;

    @Column(name = "quantidade_em_manutencao")
    private Integer quantidadeEmManutencao;

    @Column(name = "quantidade_inativa")
    private Integer quantidadeInativa;

    @Size(max = 255, message = "Setor/Departamento deve ter no máximo 255 caracteres")
    @Column(name = "setor_departamento", length = 255)
    private String setorDepartamento;

    @Size(max = 255, message = "Sala/Ambiente deve ter no máximo 255 caracteres")
    @Column(name = "sala_ambiente", length = 255)
    private String salaAmbiente;

    @Size(max = 100, message = "Andar deve ter no máximo 100 caracteres")
    @Column(name = "andar", length = 100)
    private String andar;

    @Size(max = 255, message = "Localização física deve ter no máximo 255 caracteres")
    @Column(name = "localizacao_fisica", length = 255)
    private String localizacaoFisica;

    @Convert(converter = StatusManutencaoEnumConverter.class)
    @Column(name = "status_manutencao", nullable = false)
    @NotNull(message = "Status de manutenção é obrigatório")
    private StatusManutencaoEnum statusManutencao = StatusManutencaoEnum.OPERACIONAL;

    @Column(name = "data_ultima_manutencao")
    private LocalDate dataUltimaManutencao;

    @Column(name = "data_proxima_manutencao")
    private LocalDate dataProximaManutencao;

    @Column(name = "data_ultima_calibracao")
    private LocalDate dataUltimaCalibracao;

    @Column(name = "data_proxima_calibracao")
    private LocalDate dataProximaCalibracao;

    @Size(max = 255, message = "Empresa de manutenção deve ter no máximo 255 caracteres")
    @Column(name = "empresa_manutencao", length = 255)
    private String empresaManutencao;

    @Size(max = 50, message = "Contato manutenção deve ter no máximo 50 caracteres")
    @Column(name = "contato_manutencao", length = 50)
    private String contatoManutencao;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Column(name = "data_instalacao")
    private LocalDate dataInstalacao;

    @Column(name = "data_inicio_garantia")
    private LocalDate dataInicioGarantia;

    @Column(name = "data_fim_garantia")
    private LocalDate dataFimGarantia;

    @Column(name = "valor_aquisicao", precision = 12, scale = 2)
    private BigDecimal valorAquisicao;

    @Size(max = 50, message = "Número da nota fiscal deve ter no máximo 50 caracteres")
    @Column(name = "numero_nota_fiscal", length = 50)
    private String numeroNotaFiscal;

    @Size(max = 255, message = "Responsável técnico deve ter no máximo 255 caracteres")
    @Column(name = "responsavel_tecnico", length = 255)
    private String responsavelTecnico;

    @Size(max = 50, message = "Registro do responsável deve ter no máximo 50 caracteres")
    @Column(name = "registro_responsavel", length = 50)
    private String registroResponsavel;

    @Column(name = "horas_uso_total")
    private Integer horasUsoTotal;

    @Column(name = "horas_uso_ultimo_mes")
    private Integer horasUsoUltimoMes;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

    @Column(name = "historico_manutencao", columnDefinition = "TEXT")
    private String historicoManutencao;

    @Column(name = "problemas_conhecidos", columnDefinition = "TEXT")
    private String problemasConhecidos;
}
