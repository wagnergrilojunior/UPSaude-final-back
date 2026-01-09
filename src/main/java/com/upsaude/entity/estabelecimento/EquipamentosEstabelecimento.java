package com.upsaude.entity.estabelecimento;

import com.upsaude.entity.BaseEntity;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.estabelecimento.equipamento.Equipamentos;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

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
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class EquipamentosEstabelecimento extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estabelecimento_id", nullable = false)
    private Estabelecimentos estabelecimento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "equipamento_id", nullable = false)
    private Equipamentos equipamento;

    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Column(name = "numero_serie", length = 50)
    private String numeroSerie;

    @Column(name = "numero_patrimonio", length = 50)
    private String numeroPatrimonio;

    @Column(name = "numero_tombo", length = 50)
    private String numeroTombo;

    @Column(name = "quantidade", nullable = false)
    private Integer quantidade = 1;

    @Column(name = "quantidade_disponivel")
    private Integer quantidadeDisponivel;

    @Column(name = "quantidade_em_manutencao")
    private Integer quantidadeEmManutencao;

    @Column(name = "quantidade_inativa")
    private Integer quantidadeInativa;

    @Column(name = "setor_departamento", length = 255)
    private String setorDepartamento;

    @Column(name = "sala_ambiente", length = 255)
    private String salaAmbiente;

    @Column(name = "andar", length = 100)
    private String andar;

    @Column(name = "localizacao_fisica", length = 255)
    private String localizacaoFisica;

    @Convert(converter = StatusManutencaoEnumConverter.class)
    @Column(name = "status_manutencao", nullable = false)
    private StatusManutencaoEnum statusManutencao = StatusManutencaoEnum.OPERACIONAL;

    @Column(name = "data_ultima_manutencao")
    private LocalDate dataUltimaManutencao;

    @Column(name = "data_proxima_manutencao")
    private LocalDate dataProximaManutencao;

    @Column(name = "data_ultima_calibracao")
    private LocalDate dataUltimaCalibracao;

    @Column(name = "data_proxima_calibracao")
    private LocalDate dataProximaCalibracao;

    @Column(name = "empresa_manutencao", length = 255)
    private String empresaManutencao;

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

    @Column(name = "numero_nota_fiscal", length = 50)
    private String numeroNotaFiscal;

    @Column(name = "responsavel_tecnico", length = 255)
    private String responsavelTecnico;

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
