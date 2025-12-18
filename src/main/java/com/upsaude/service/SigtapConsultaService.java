package com.upsaude.service;

import com.upsaude.dto.sigtap.SigtapCompatibilidadeResponse;
import com.upsaude.dto.sigtap.SigtapGrupoResponse;
import com.upsaude.dto.sigtap.SigtapProcedimentoDetalhadoResponse;
import com.upsaude.dto.sigtap.SigtapProcedimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SigtapConsultaService {

    List<SigtapGrupoResponse> listarGrupos();

    Page<SigtapProcedimentoResponse> pesquisarProcedimentos(
            String q,
            String competencia,
            Pageable pageable
    );

    SigtapProcedimentoDetalhadoResponse obterProcedimentoDetalhado(String codigoProcedimento, String competencia);

    Page<SigtapCompatibilidadeResponse> pesquisarCompatibilidades(
            String codigoProcedimentoPrincipal,
            String competencia,
            Pageable pageable
    );
}

