package com.upsaude.service.api.referencia.sigtap;

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
            String formaOrganizacaoCodigo,
            String competencia,
            Pageable pageable
    );

    SigtapProcedimentoDetalhadoResponse obterProcedimentoDetalhado(String codigoProcedimento, String competencia);

    Page<SigtapCompatibilidadeResponse> pesquisarCompatibilidades(
            String codigoProcedimentoPrincipal,
            String competencia,
            Pageable pageable
    );

    Page<SigtapServicoResponse> pesquisarServicos(String q, Pageable pageable);
    SigtapServicoResponse obterServicoPorCodigo(String codigoOficial);

    Page<SigtapRenasesResponse> pesquisarRenases(String q, Pageable pageable);
    SigtapRenasesResponse obterRenasesPorCodigo(String codigoOficial);

    Page<SigtapSubgrupoResponse> pesquisarSubgrupos(String q, String grupoCodigo, String subgrupoCodigo, String competencia, Pageable pageable);
    SigtapSubgrupoResponse obterSubgrupoPorCodigo(String codigoOficial, String grupoCodigo);

    Page<SigtapFormaOrganizacaoResponse> pesquisarFormasOrganizacao(String q, String grupoCodigo, String subgrupoCodigo, String competencia, Pageable pageable);
    SigtapFormaOrganizacaoResponse obterFormaOrganizacaoPorCodigo(String codigoOficial, String subgrupoCodigo);

    Page<SigtapHabilitacaoResponse> pesquisarHabilitacoes(String q, String competencia, Pageable pageable);
    SigtapHabilitacaoResponse obterHabilitacaoPorCodigo(String codigoOficial, String competencia);

    Page<SigtapTussResponse> pesquisarTuss(String q, Pageable pageable);
    SigtapTussResponse obterTussPorCodigo(String codigoOficial);

    Page<SigtapCboResponse> pesquisarCbo(String q, String grupo, Pageable pageable);
    SigtapCboResponse obterCboPorCodigo(String codigoOficial);
    List<SigtapCboResponse> listarCbo(String q, String grupo);

    List<SigtapGrupoCboResponse> listarGruposCbo();
    SigtapGrupoCboResponse obterGrupoCboPorCodigo(String codigoGrupo);
    Page<SigtapCboResponse> pesquisarCboPorGrupo(String codigoGrupo, String q, Pageable pageable);

    Page<SigtapModalidadeResponse> pesquisarModalidades(String q, String competencia, Pageable pageable);
    SigtapModalidadeResponse obterModalidadePorCodigo(String codigoOficial, String competencia);
}
