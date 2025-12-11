package com.upsaude.entity;

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
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
@Data
@EqualsAndHashCode(callSuper = true)
public class Equipamentos extends BaseEntity {

    @NotBlank(message = "Nome do equipamento é obrigatório")
    @Size(max = 255, message = "Nome do equipamento deve ter no máximo 255 caracteres")
    @Column(name = "nome", nullable = false, length = 255)
    private String nome;

    @Size(max = 100, message = "Nome comercial deve ter no máximo 100 caracteres")
    @Column(name = "nome_comercial", length = 100)
    private String nomeComercial;

    @Size(max = 50, message = "Código interno deve ter no máximo 50 caracteres")
    @Column(name = "codigo_interno", length = 50)
    private String codigoInterno;

    @Size(max = 20, message = "Código CNES deve ter no máximo 20 caracteres")
    @Column(name = "codigo_cnes", length = 20, unique = true)
    private String codigoCnes;

    @Size(max = 50, message = "Registro ANVISA deve ter no máximo 50 caracteres")
    @Column(name = "registro_anvisa", length = 50, unique = true)
    private String registroAnvisa;

    @Convert(converter = TipoEquipamentoEnumConverter.class)
    @Column(name = "tipo", nullable = false)
    @NotNull(message = "Tipo de equipamento é obrigatório")
    private TipoEquipamentoEnum tipo;

    @Size(max = 100, message = "Categoria deve ter no máximo 100 caracteres")
    @Column(name = "categoria", length = 100)
    private String categoria;

    @Size(max = 100, message = "Subcategoria deve ter no máximo 100 caracteres")
    @Column(name = "subcategoria", length = 100)
    private String subcategoria;

    @Size(max = 50, message = "Classe de risco deve ter no máximo 50 caracteres")
    @Column(name = "classe_risco", length = 50)
    private String classeRisco;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fabricante_id")
    private FabricantesEquipamento fabricante;

    @Size(max = 50, message = "Modelo deve ter no máximo 50 caracteres")
    @Column(name = "modelo", length = 50)
    private String modelo;

    @Size(max = 50, message = "Versão deve ter no máximo 50 caracteres")
    @Column(name = "versao", length = 50)
    private String versao;

    @Column(name = "potencia", precision = 10, scale = 2)
    private BigDecimal potencia;

    @Size(max = 50, message = "Unidade de potência deve ter no máximo 50 caracteres")
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

    @Size(max = 50, message = "Tensão elétrica deve ter no máximo 50 caracteres")
    @Column(name = "tensao_eletrica", length = 50)
    private String tensaoEletrica;

    @Size(max = 50, message = "Frequência deve ter no máximo 50 caracteres")
    @Column(name = "frequencia", length = 50)
    private String frequencia;

    @Size(max = 50, message = "Corrente deve ter no máximo 50 caracteres")
    @Column(name = "corrente", length = 50)
    private String corrente;

    @Size(max = 100, message = "Tipo de alimentação deve ter no máximo 100 caracteres")
    @Column(name = "tipo_alimentacao", length = 100)
    private String tipoAlimentacao;

    @Size(max = 50, message = "Certificação ISO deve ter no máximo 50 caracteres")
    @Column(name = "certificacao_iso", length = 50)
    private String certificacaoIso;

    @Size(max = 50, message = "Certificação CE deve ter no máximo 50 caracteres")
    @Column(name = "certificacao_ce", length = 50)
    private String certificacaoCe;

    @Size(max = 50, message = "Certificação FDA deve ter no máximo 50 caracteres")
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

    @Size(max = 100, message = "Tipo de manutenção deve ter no máximo 100 caracteres")
    @Column(name = "tipo_manutencao", length = 100)
    private String tipoManutencao;

    @Column(name = "valor_aquisicao", precision = 12, scale = 2)
    private BigDecimal valorAquisicao;

    @Column(name = "data_aquisicao")
    private LocalDate dataAquisicao;

    @Size(max = 50, message = "Fornecedor deve ter no máximo 50 caracteres")
    @Column(name = "fornecedor", length = 50)
    private String fornecedor;

    @Size(max = 50, message = "Número da nota fiscal deve ter no máximo 50 caracteres")
    @Column(name = "numero_nota_fiscal", length = 50)
    private String numeroNotaFiscal;

    @Size(max = 50, message = "Número do contrato deve ter no máximo 50 caracteres")
    @Column(name = "numero_contrato", length = 50)
    private String numeroContrato;

    @Column(name = "tempo_garantia_meses")
    private Integer tempoGarantiaMeses;

    @Column(name = "data_inicio_garantia")
    private LocalDate dataInicioGarantia;

    @Column(name = "data_fim_garantia")
    private LocalDate dataFimGarantia;

    @Size(max = 255, message = "Condições de garantia deve ter no máximo 255 caracteres")
    @Column(name = "condicoes_garantia", length = 255)
    private String condicoesGarantia;

    @Convert(converter = StatusAtivoEnumConverter.class)
    @Column(name = "status", nullable = false)
    @NotNull(message = "Status é obrigatório")
    private StatusAtivoEnum status;

    @Column(name = "disponivel_uso", nullable = false)
    private Boolean disponivelUso = true;

    @Size(max = 255, message = "Manual técnico deve ter no máximo 255 caracteres")
    @Column(name = "manual_tecnico", length = 255)
    private String manualTecnico;

    @Size(max = 255, message = "Manual do usuário deve ter no máximo 255 caracteres")
    @Column(name = "manual_usuario", length = 255)
    private String manualUsuario;

    @Size(max = 255, message = "Ficha técnica deve ter no máximo 255 caracteres")
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
