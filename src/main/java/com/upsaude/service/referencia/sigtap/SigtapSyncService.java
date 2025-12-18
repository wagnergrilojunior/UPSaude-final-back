package com.upsaude.service.referencia.sigtap;

public interface SigtapSyncService {

    void sincronizarTudo(String competencia);

    void sincronizarGrupos();

    void sincronizarSubgrupos();

    void sincronizarFormasOrganizacao();

    void sincronizarProcedimentos(String competencia);

    void detalharProcedimentos(String competencia);

    void sincronizarCompatibilidadesPossiveis();

    void sincronizarCompatibilidades(String competencia);
}

