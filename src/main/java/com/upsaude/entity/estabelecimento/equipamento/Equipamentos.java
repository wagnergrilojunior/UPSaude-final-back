package com.upsaude.entity.estabelecimento.equipamento;
import com.upsaude.entity.BaseEntity;

import com.upsaude.enums.StatusAtivoEnum;
import com.upsaude.enums.TipoEquipamentoEnum;
import com.upsaude.util.converter.StatusAtivoEnumConverter;
import com.upsaude.util.converter.TipoEquipamentoEnumConverter;
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
@Table(name = "equipamentos", schema = "public",
       uniqueConstraints = {
           @UniqueConstraint(name = "uk_equipamento_codigo_cnes", columnNames = {"codigo_cnes"}),
           @UniqueConstraint(name = "uk_equipamento_registro_anvisa", columnNames = {"registro_anvisa"})
       },
       indexes = {
           @Index(name = "idx_equipamento_nome", columnList = "nome"),
           @Index(name = "idx_equipamento_tipo", columnList = "tipo"),
           @Index(name = "idx_equipamento_fabricante", columnList = "fabricante_id"),
           @Index(name = "idx_equipamento_codigo_cnes", columnList = "codigo_cnes"),
           @Index(name = "idx_equipamento_status", columnList = "status")
       })
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
public class Equipamentos extends BaseEntity {

    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Column(name = "nome_comercial", length = 100)
    private String nomeComercial;

    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Column(name = "codigo_cnes", length = 20, unique = true)
    private String codigoCnes;

    @Column(name = "registro_anvisa", length = 50, unique = true)
    private String registroAnvisa;

    @Convert(converter = TipoEquipamentoEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    private TipoEquipamentoEnum tipo;

    @Column(name = "categoria", length = 100)
    private String categoria;

    @Column(name = "subcategoria", length = 100)
    private String subcategoria;

    @Column(name = "classe_risco", length = 50)
    private String classeRisco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricantesEquipamento fabricante;

    @Column(name = "modelo", length = 50)
    private String modelo;

    @Column(name = "versao", length = 50)
    private String versao;

    @Column(name = "potencia", precision = 10, scale = 2)
    private BigDecimal potencia;

    @Column(name = "unidade_potencia", length = 50)
    private String unidadePotencia;

    @Column(name = "peso", precision = 10, scale = 2)
    private BigDecimal peso;

    @Column(name = "altura", precision = 10, scale = 2)
    private BigDecimal altura;

    @Column(name = "largura", precision = 10, scale = 2)
    private BigDecimal largura;

    @Column(name = "profundidade", precision = 10, scale = 2)
    private BigDecimal profundidade;

    @Column(name = "tensao_eletrica", length = 50)
    private String tensaoEletrica;

    @Column(name = "frequencia", length = 50)
    private String frequencia;

    @Column(name = "corrente", length = 50)
    private String corrente;

    @Column(name = "tipo_alimentacao", length = 100)
    private String tipoAlimentacao;

    @Column(name = "certificacao_iso", length = 50)
    private String certificacaoIso;

    @Column(name = "certificacao_ce", length = 50)
    private String certificacaoCe;

    @Column(name = "certificacao_fda", length = 50)
    private String certificacaoFda;

    @Column(name = "data_certificacao")
    private LocalDate dataCertificacao;

    @Column(name = "data_validade_certificacao")
    private LocalDate dataValidadeCertificacao;

    @Column(name = "requer_calibracao", nullable = false)
    private Boolean requerCalibracao = false;

    @Column(name = "periodo_calibracao_meses")
    private Integer periodoCalibracaoMeses;

    @Column(name = "requer_manutencao_preventiva", nullable = false)
    private Boolean requerManutencaoPreventiva = false;

    @Column(name = "periodo_manutencao_meses")
    private Integer periodoManutencaoMeses;

    @Column(name = "tipo_manutencao", length = 100)
    private String tipoManutencao;

    @Column(name = "valor_aquisicao", precision = 12, scale = 2)
    private BigDecimal valorAquisicao;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Column(name = "fornecedor", length = 50)
    private String fornecedor;

    @Column(name = "numero_nota_fiscal", length = 50)
    private String numeroNotaFiscal;

    @Column(name = "numero_contrato", length = 50)
    private String numeroContrato;

    @Column(name = "tempo_garantia_meses")
    private Integer tempoGarantiaMeses;

    @Column(name = "data_inicio_garantia")
    private LocalDate dataInicioGarantia;

    @Column(name = "data_fim_garantia")
    private LocalDate dataFimGarantia;

    @Column(name = "condicoes_garantia", length = 255)
    private String condicoesGarantia;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    private StatusAtivoEnum status;

    @Column(name = "disponivel_uso", nullable = false)
    private Boolean disponivelUso = true;

    @Column(name = "manual_tecnico", length = 255)
    private String manualTecnico;

    @Column(name = "manual_usuario", length = 255)
    private String manualUsuario;

    @Column(name = "ficha_tecnica", length = 255)
    private String fichaTecnica;

    @Column(name = "descricao", columnDefinition = "TEXT")
    private String descricao;

    @Column(name = "caracteristicas", columnDefinition = "TEXT")
    private String caracteristicas;

    @Column(name = "aplicacoes", columnDefinition = "TEXT")
    private String aplicacoes;

    @Column(name = "observacoes", columnDefinition = "TEXT")
    private String observacoes;

}
