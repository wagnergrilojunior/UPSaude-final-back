package com.upsaude.entity.referencia.sia;

import com.upsaude.entity.BaseEntityWithoutTenant;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;


@Entity
@Immutable
@Table(name = "sia_pa_enriquecido_view", schema = "public")
@Data
@EqualsAndHashCode(callSuper = true)
public class SiaPaEnriquecido extends BaseEntityWithoutTenant {

    
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

    @Column(name = "procedimento_codigo", length = 10, nullable = false)
    private String procedimentoCodigo;

    @Column(name = "cid_principal_codigo", length = 10)
    private String cidPrincipalCodigo;

    @Column(name = "sexo", length = 1)
    private String sexo;

    @Column(name = "idade")
    private Integer idade;

    @Column(name = "quantidade_produzida")
    private Integer quantidadeProduzida;

    @Column(name = "quantidade_aprovada")
    private Integer quantidadeAprovada;

    @Column(name = "flag_erro", length = 1)
    private String flagErro;

    @Column(name = "valor_produzido", precision = 14, scale = 2)
    private BigDecimal valorProduzido;

    @Column(name = "valor_aprovado", precision = 14, scale = 2)
    private BigDecimal valorAprovado;

    
    @Column(name = "procedimento_nome")
    private String procedimentoNome;

    @Column(name = "procedimento_tipo_complexidade")
    private String procedimentoTipoComplexidade;

    @Column(name = "procedimento_sexo_permitido")
    private String procedimentoSexoPermitido;

    @Column(name = "procedimento_idade_minima")
    private Integer procedimentoIdadeMinima;

    @Column(name = "procedimento_idade_maxima")
    private Integer procedimentoIdadeMaxima;

    @Column(name = "procedimento_valor_sigtap_ambulatorial", precision = 14, scale = 2)
    private BigDecimal procedimentoValorSigtapAmbulatorial;

    @Column(name = "procedimento_forma_organizacao_nome")
    private String procedimentoFormaOrganizacaoNome;

    @Column(name = "procedimento_subgrupo_nome")
    private String procedimentoSubgrupoNome;

    @Column(name = "procedimento_grupo_nome")
    private String procedimentoGrupoNome;

    
    @Column(name = "cid_principal_descricao")
    private String cidPrincipalDescricao;

    
    @Column(name = "estabelecimento_nome")
    private String estabelecimentoNome;

    @Column(name = "estabelecimento_cnpj")
    private String estabelecimentoCnpj;

    @Column(name = "estabelecimento_codigo_ibge_municipio")
    private String estabelecimentoCodigoIbgeMunicipio;

    @Column(name = "estabelecimento_esfera_administrativa")
    private String estabelecimentoEsferaAdministrativa;
}

