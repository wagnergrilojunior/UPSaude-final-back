package com.upsaude.entity.referencia.sia;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

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

    @Column(name = "competencia", length = 6, nullable = false)
    private String competencia; 

    @Column(name = "uf", length = 2, nullable = false)
    private String uf; 

    @Column(name = "mes_movimentacao", length = 6)
    private String mesMovimentacao; 

    @Column(name = "codigo_cnes", length = 7, nullable = false)
    private String codigoCnes; 

    @Column(name = "municipio_ufmun_codigo", length = 6)
    private String municipioUfmunCodigo; 

    @Column(name = "municipio_gestao_codigo", length = 6)
    private String municipioGestaoCodigo; 

    @Column(name = "condicao_estabelecimento", length = 2)
    private String condicaoEstabelecimento; 

    @Column(name = "regiao_controle", length = 4)
    private String regiaoControle; 

    @Column(name = "inconsistencia_saida", length = 4)
    private String inconsistenciaSaida; 

    @Column(name = "inconsistencia_urgencia", length = 4)
    private String inconsistenciaUrgencia; 

    @Column(name = "tipo_unidade", length = 2)
    private String tipoUnidade; 

    @Column(name = "tipo_prestador", length = 2)
    private String tipoPrestador; 

    @Column(name = "indicador_manual", length = 1)
    private String indicadorManual; 

    @Column(name = "cnpj_cpf", length = 14)
    private String cnpjCpf; 

    @Column(name = "cnpj_mantenedor", length = 14)
    private String cnpjMantenedor; 

    @Column(name = "cnpj_contratante", length = 14)
    private String cnpjContratante; 

    @Column(name = "natureza_juridica", length = 4)
    private String naturezaJuridica; 

    @Column(name = "codigo_orgao_contratante")
    private String codigoOrgaoContratante; 

    @Column(name = "servico_contratualizado")
    private String servicoContratualizado; 

    @Column(name = "codigo_ine")
    private String codigoIne; 

    @Column(name = "procedimento_codigo", length = 10, nullable = false)
    private String procedimentoCodigo; 

    @Column(name = "nivel_complexidade", length = 1)
    private String nivelComplexidade; 

    @Column(name = "indicador", length = 5)
    private String indicador; 

    @Column(name = "tipo_financiamento", length = 2)
    private String tipoFinanciamento; 

    @Column(name = "subfinanciamento", length = 4)
    private String subfinanciamento; 

    @Column(name = "documento_origem", length = 13)
    private String documentoOrigem; 

    @Column(name = "numero_autorizacao", length = 15)
    private String numeroAutorizacao; 

    @Column(name = "tipo_documento_origem", length = 1)
    private String tipoDocumentoOrigem; 

    @Column(name = "cns_profissional", length = 15)
    private String cnsProfissional; 

    @Column(name = "cbo_codigo", length = 6)
    private String cboCodigo; 

    @Column(name = "cid_principal_codigo", length = 10)
    private String cidPrincipalCodigo; 

    @Column(name = "cid_secundario_codigo", length = 10)
    private String cidSecundarioCodigo; 

    @Column(name = "cid_causa_codigo", length = 10)
    private String cidCausaCodigo; 

    @Column(name = "carater_atendimento", length = 2)
    private String caraterAtendimento; 

    @Column(name = "idade")
    private Integer idade; 

    @Column(name = "idade_minima")
    private Integer idadeMinima; 

    @Column(name = "idade_maxima")
    private Integer idadeMaxima; 

    @Column(name = "flag_idade", length = 1)
    private String flagIdade; 

    @Column(name = "sexo", length = 1)
    private String sexo; 

    @Column(name = "raca_cor", length = 2)
    private String racaCor; 

    @Column(name = "etnia")
    private String etnia; 

    @Column(name = "municipio_paciente_codigo", length = 6)
    private String municipioPacienteCodigo; 

    @Column(name = "motivo_saida", length = 2)
    private String motivoSaida; 

    @Column(name = "flag_obito", length = 1)
    private String flagObito; 

    @Column(name = "flag_encerramento", length = 1)
    private String flagEncerramento; 

    @Column(name = "flag_permanencia", length = 1)
    private String flagPermanencia; 

    @Column(name = "flag_alta", length = 1)
    private String flagAlta; 

    @Column(name = "flag_transferencia", length = 1)
    private String flagTransferencia; 

    @Column(name = "quantidade_produzida")
    private Integer quantidadeProduzida; 

    @Column(name = "quantidade_aprovada")
    private Integer quantidadeAprovada; 

    @Column(name = "flag_quantidade", length = 1)
    private String flagQuantidade; 

    @Column(name = "flag_erro", length = 1)
    private String flagErro; 

    @Column(name = "valor_produzido", precision = 14, scale = 2)
    private BigDecimal valorProduzido; 

    @Column(name = "valor_aprovado", precision = 14, scale = 2)
    private BigDecimal valorAprovado; 

    @Column(name = "valor_cofinanciado", precision = 14, scale = 2)
    private BigDecimal valorCofinanciado; 

    @Column(name = "valor_clinico", precision = 14, scale = 2)
    private BigDecimal valorClinico; 

    @Column(name = "valor_incrementado", precision = 14, scale = 2)
    private BigDecimal valorIncrementado; 

    @Column(name = "valor_total_vpa", precision = 14, scale = 2)
    private BigDecimal valorTotalVpa; 

    @Column(name = "total_pa", precision = 14, scale = 2)
    private BigDecimal totalPa; 

    @Column(name = "uf_diferente", length = 1)
    private String ufDiferente; 

    @Column(name = "municipio_diferente", length = 1)
    private String municipioDiferente; 

    @Column(name = "diferenca_valor", precision = 14, scale = 2)
    private BigDecimal diferencaValor; 
}
