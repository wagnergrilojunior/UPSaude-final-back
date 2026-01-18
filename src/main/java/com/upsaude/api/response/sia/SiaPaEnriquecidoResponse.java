package com.upsaude.api.response.sia;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SiaPaEnriquecidoResponse {

    
    private UUID id;
    private OffsetDateTime criadoEm;
    private OffsetDateTime atualizadoEm;
    private Boolean ativo;

    private String competencia;
    private String uf;
    private String mesMovimentacao;

    private String codigoCnes;
    private String municipioUfmunCodigo;
    private String municipioGestaoCodigo;

    private String procedimentoCodigo;
    private String cidPrincipalCodigo;
    private String sexo;
    private Integer idade;

    private Integer quantidadeProduzida;
    private Integer quantidadeAprovada;
    private String flagErro;
    private BigDecimal valorProduzido;
    private BigDecimal valorAprovado;

    
    private String procedimentoNome;
    private String procedimentoTipoComplexidade;
    private String procedimentoSexoPermitido;
    private Integer procedimentoIdadeMinima;
    private Integer procedimentoIdadeMaxima;
    private BigDecimal procedimentoValorSigtapAmbulatorial;
    private String procedimentoFormaOrganizacaoNome;
    private String procedimentoSubgrupoNome;
    private String procedimentoGrupoNome;

    
    private String cidPrincipalDescricao;

    
    private String estabelecimentoNome;
    private String estabelecimentoCnpj;
    private String estabelecimentoCodigoIbgeMunicipio;
    private String estabelecimentoEsferaAdministrativa;
}

