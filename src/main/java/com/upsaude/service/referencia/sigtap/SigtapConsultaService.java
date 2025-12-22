package com.upsaude.service.referencia.sigtap;

import com.upsaude.api.response.referencia.sigtap.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SigtapConsultaService {

    List<SigtapGrupoResponse> listarGrupos();

    Page<SigtapProcedimentoResponse> pesquisarProcedimentos(
            String q,
            String grupoCodigo,
            String subgrupoCodigo,
            String competencia,
            Pageable pageable
    );

    SigtapProcedimentoDetalhadoResponse obterProcedimentoDetalhado(String codigoProcedimento, String competencia);

    Page<SigtapCompatibilidadeResponse> pesquisarCompatibilidades(
            String codigoProcedimentoPrincipal,
            String competencia,
            Pageable pageable
    );

    // Serviços/Exames
    Page<SigtapServicoResponse> pesquisarServicos(String q, Pageable pageable);
    SigtapServicoResponse obterServicoPorCodigo(String codigoOficial);

    // RENASES
    Page<SigtapRenasesResponse> pesquisarRenases(String q, Pageable pageable);
    SigtapRenasesResponse obterRenasesPorCodigo(String codigoOficial);

    // Subgrupos
    Page<SigtapSubgrupoResponse> pesquisarSubgrupos(String q, String grupoCodigo, String subgrupoCodigo, String competencia, Pageable pageable);
    SigtapSubgrupoResponse obterSubgrupoPorCodigo(String codigoOficial, String grupoCodigo);

    // Formas de Organização
    Page<SigtapFormaOrganizacaoResponse> pesquisarFormasOrganizacao(String q, String subgrupoCodigo, String competencia, Pageable pageable);
    SigtapFormaOrganizacaoResponse obterFormaOrganizacaoPorCodigo(String codigoOficial, String subgrupoCodigo);

    // Habilitações
    Page<SigtapHabilitacaoResponse> pesquisarHabilitacoes(String q, String competencia, Pageable pageable);
    SigtapHabilitacaoResponse obterHabilitacaoPorCodigo(String codigoOficial, String competencia);

    // TUSS
    Page<SigtapTussResponse> pesquisarTuss(String q, Pageable pageable);
    SigtapTussResponse obterTussPorCodigo(String codigoOficial);

    // Ocupações
    Page<SigtapOcupacaoResponse> pesquisarOcupacoes(String q, Pageable pageable);
    SigtapOcupacaoResponse obterOcupacaoPorCodigo(String codigoOficial);

    // Modalidades
    Page<SigtapModalidadeResponse> pesquisarModalidades(String q, String competencia, Pageable pageable);
    SigtapModalidadeResponse obterModalidadePorCodigo(String codigoOficial, String competencia);
}

