package com.upsaude.service;

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

