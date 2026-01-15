package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.kpi.SiaPaKpiResponse;

import java.util.UUID;

public interface SiaPaKpiService {

    SiaPaKpiResponse kpiGeral(String competencia, String uf);

    SiaPaKpiResponse kpiPorEstabelecimento(String competencia, String uf, String codigoCnes);

    SiaPaKpiResponse kpiPorProcedimento(String competencia, String uf, String procedimentoCodigo);

    /**
     * KPIs do SIA-PA para o tenant atual.
     * 
     * @param competencia Competência no formato AAAAMM
     * @param uf UF (2 letras). Se omitido, tenta inferir pelo tenant
     * @return KPIs agregados do tenant
     */
    SiaPaKpiResponse kpiPorTenant(String competencia, String uf);

    /**
     * KPIs do SIA-PA para um estabelecimento específico.
     * 
     * @param estabelecimentoId ID do estabelecimento
     * @param competencia Competência no formato AAAAMM
     * @param uf UF (2 letras). Se omitido, tenta inferir pelo tenant
     * @return KPIs do estabelecimento
     */
    SiaPaKpiResponse kpiPorEstabelecimentoId(UUID estabelecimentoId, String competencia, String uf);

    /**
     * KPIs do SIA-PA para um médico específico (via CNS).
     * 
     * @param medicoId ID do médico
     * @param competencia Competência no formato AAAAMM
     * @param uf UF (2 letras). Se omitido, tenta inferir pelo tenant
     * @return KPIs do médico
     */
    SiaPaKpiResponse kpiPorMedicoId(UUID medicoId, String competencia, String uf);
}

