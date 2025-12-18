package com.upsaude.service.referencia.sigtap;

import com.upsaude.dto.referencia.sigtap.SigtapCompatibilidadeResponse;
import com.upsaude.dto.referencia.sigtap.SigtapGrupoResponse;
import com.upsaude.dto.referencia.sigtap.SigtapProcedimentoDetalhadoResponse;
import com.upsaude.dto.referencia.sigtap.SigtapProcedimentoResponse;
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

