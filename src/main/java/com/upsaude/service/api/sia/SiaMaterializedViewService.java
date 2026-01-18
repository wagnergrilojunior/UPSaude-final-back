package com.upsaude.service.api.sia;


public interface SiaMaterializedViewService {

    
    void refreshAllViews();

    
    void refreshView(String viewName);

    
    boolean isViewUpToDate(String viewName);
}
