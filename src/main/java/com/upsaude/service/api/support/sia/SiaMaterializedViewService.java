package com.upsaude.service.api.support.sia;


public interface SiaMaterializedViewService {

    
    void refreshAllViews();

    
    void refreshProducaoMensalView();

    
    void refreshTopProcedimentosView();

    
    void refreshTopCidView();

    
    void refreshKpiGeralView();
}
