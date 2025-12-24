package com.upsaude.entity.referencia.sia;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * Produção Ambulatorial do SIA-SUS (Sistema de Informações Ambulatoriais).
 * 
 * <p>Representa registros de procedimentos ambulatoriais realizados no SUS.
 * Os dados são importados de arquivos CSV do DATASUS.</p>
 */
@Entity
@Table(
    name = "sia_pa",
    schema = "public",
    indexes = {
        @Index(name = "idx_sia_pa_competencia", columnList = "competencia"),
        @Index(name = "idx_sia_pa_uf", columnList = "uf"),
        @Index(name = "idx_sia_pa_procedimento", columnList = "procedimento_codigo"),
        @Index(name = "idx_sia_pa_cid_principal", columnList = "cid_principal_codigo"),
        @Index(name = "idx_sia_pa_cnes", columnList = "codigo_cnes"),
        @Index(name = "idx_sia_pa_competencia_uf", columnList = "competencia,uf"),
        @Index(name = "idx_sia_pa_mes_movimentacao", columnList = "mes_movimentacao")
    }
)
@Data
@EqualsAndHashCode(callSuper = true)
public class SiaPa extends BaseEntityWithoutTenant {

    // ========== COMPETÊNCIA E CONTROLE ==========
    
    @Column(name = "competencia", length = 6, nullable = false)
    private String competencia; // PA_CMP - formato AAAAMM

    @Column(name = "uf", length = 2, nullable = false)
    private String uf; // UF do arquivo/estado

    @Column(name = "mes_movimentacao", length = 6)
    private String mesMovimentacao; // PA_MVM

    // ========== ESTABELECIMENTO ==========
    
    @Column(name = "codigo_cnes", length = 7, nullable = false)
    private String codigoCnes; // PA_CODUNI

    @Column(name = "municipio_ufmun_codigo", length = 6)
    private String municipioUfmunCodigo; // PA_UFMUN

    @Column(name = "municipio_gestao_codigo", length = 6)
    private String municipioGestaoCodigo; // PA_GESTAO

    @Column(name = "condicao_estabelecimento", length = 2)
    private String condicaoEstabelecimento; // PA_CONDIC

    @Column(name = "regiao_controle", length = 4)
    private String regiaoControle; // PA_REGCT

    @Column(name = "inconsistencia_saida", length = 4)
    private String inconsistenciaSaida; // PA_INCOUT

    @Column(name = "inconsistencia_urgencia", length = 4)
    private String inconsistenciaUrgencia; // PA_INCURG

    @Column(name = "tipo_unidade", length = 2)
    private String tipoUnidade; // PA_TPUPS

    @Column(name = "tipo_prestador", length = 2)
    private String tipoPrestador; // PA_TIPPRE

    @Column(name = "indicador_manual", length = 1)
    private String indicadorManual; // PA_MN_IND

    @Column(name = "cnpj_cpf", length = 14)
    private String cnpjCpf; // PA_CNPJCPF

    @Column(name = "cnpj_mantenedor", length = 14)
    private String cnpjMantenedor; // PA_CNPJMNT

    @Column(name = "cnpj_contratante", length = 14)
    private String cnpjContratante; // PA_CNPJ_CC

    @Column(name = "natureza_juridica", length = 4)
    private String naturezaJuridica; // PA_NAT_JUR

    @Column(name = "codigo_orgao_contratante")
    private String codigoOrgaoContratante; // PA_CODOCO

    @Column(name = "servico_contratualizado")
    private String servicoContratualizado; // PA_SRV_C

    @Column(name = "codigo_ine")
    private String codigoIne; // PA_INE

    // ========== PROCEDIMENTO ==========
    
    @Column(name = "procedimento_codigo", length = 10, nullable = false)
    private String procedimentoCodigo; // PA_PROC_ID

    @Column(name = "nivel_complexidade", length = 1)
    private String nivelComplexidade; // PA_NIVCPL

    @Column(name = "indicador", length = 5)
    private String indicador; // PA_INDICA

    // ========== FINANCIAMENTO ==========
    
    @Column(name = "tipo_financiamento", length = 2)
    private String tipoFinanciamento; // PA_TPFIN

    @Column(name = "subfinanciamento", length = 4)
    private String subfinanciamento; // PA_SUBFIN

    // ========== DOCUMENTAÇÃO E AUTORIZAÇÃO ==========
    
    @Column(name = "documento_origem", length = 13)
    private String documentoOrigem; // PA_DOCORIG

    @Column(name = "numero_autorizacao", length = 15)
    private String numeroAutorizacao; // PA_AUTORIZ

    @Column(name = "tipo_documento_origem", length = 1)
    private String tipoDocumentoOrigem; // PA_DOCORIG (tipo)

    // ========== PROFISSIONAL ==========
    
    @Column(name = "cns_profissional", length = 15)
    private String cnsProfissional; // PA_CNSMED

    @Column(name = "cbo_codigo", length = 6)
    private String cboCodigo; // PA_CBOCOD

    // ========== CID (DIAGNÓSTICO) ==========
    
    @Column(name = "cid_principal_codigo", length = 10)
    private String cidPrincipalCodigo; // PA_CIDPRI

    @Column(name = "cid_secundario_codigo", length = 10)
    private String cidSecundarioCodigo; // PA_CIDSEC

    @Column(name = "cid_causa_codigo", length = 10)
    private String cidCausaCodigo; // PA_CIDCAS

    // ========== ATENDIMENTO ==========
    
    @Column(name = "carater_atendimento", length = 2)
    private String caraterAtendimento; // PA_CATEND

    // ========== USUÁRIO/PACIENTE ==========
    
    @Column(name = "idade")
    private Integer idade; // PA_IDADE

    @Column(name = "idade_minima")
    private Integer idadeMinima; // IDADEMIN

    @Column(name = "idade_maxima")
    private Integer idadeMaxima; // IDADEMAX

    @Column(name = "flag_idade", length = 1)
    private String flagIdade; // PA_FLIDADE

    @Column(name = "sexo", length = 1)
    private String sexo; // PA_SEXO

    @Column(name = "raca_cor", length = 2)
    private String racaCor; // PA_RACACOR

    @Column(name = "etnia")
    private String etnia; // PA_ETNIA

    @Column(name = "municipio_paciente_codigo", length = 6)
    private String municipioPacienteCodigo; // PA_MUNPCN

    // ========== CONTROLE DE FLUXO/STATUS ==========
    
    @Column(name = "motivo_saida", length = 2)
    private String motivoSaida; // PA_MOTSAI

    @Column(name = "flag_obito", length = 1)
    private String flagObito; // PA_OBITO

    @Column(name = "flag_encerramento", length = 1)
    private String flagEncerramento; // PA_ENCERR

    @Column(name = "flag_permanencia", length = 1)
    private String flagPermanencia; // PA_PERMAN

    @Column(name = "flag_alta", length = 1)
    private String flagAlta; // PA_ALTA

    @Column(name = "flag_transferencia", length = 1)
    private String flagTransferencia; // PA_TRANSF

    // ========== PRODUÇÃO E QUANTIDADES ==========
    
    @Column(name = "quantidade_produzida")
    private Integer quantidadeProduzida; // PA_QTDPRO

    @Column(name = "quantidade_aprovada")
    private Integer quantidadeAprovada; // PA_QTDAPR

    @Column(name = "flag_quantidade", length = 1)
    private String flagQuantidade; // PA_FLQT

    @Column(name = "flag_erro", length = 1)
    private String flagErro; // PA_FLER

    // ========== VALORES FINANCEIROS ==========
    
    @Column(name = "valor_produzido", precision = 14, scale = 2)
    private BigDecimal valorProduzido; // PA_VALPRO

    @Column(name = "valor_aprovado", precision = 14, scale = 2)
    private BigDecimal valorAprovado; // PA_VALAPR

    @Column(name = "valor_cofinanciado", precision = 14, scale = 2)
    private BigDecimal valorCofinanciado; // PA_VL_CF

    @Column(name = "valor_clinico", precision = 14, scale = 2)
    private BigDecimal valorClinico; // PA_VL_CL

    @Column(name = "valor_incrementado", precision = 14, scale = 2)
    private BigDecimal valorIncrementado; // PA_VL_INC

    @Column(name = "valor_total_vpa", precision = 14, scale = 2)
    private BigDecimal valorTotalVpa; // NU_VPA_TOT

    @Column(name = "total_pa", precision = 14, scale = 2)
    private BigDecimal totalPa; // NU_PA_TOT

    // ========== DIFERENÇAS ==========
    
    @Column(name = "uf_diferente", length = 1)
    private String ufDiferente; // PA_UFDIF

    @Column(name = "municipio_diferente", length = 1)
    private String municipioDiferente; // PA_MNDIF

    @Column(name = "diferenca_valor", precision = 14, scale = 2)
    private BigDecimal diferencaValor; // PA_DIF_VAL
}

