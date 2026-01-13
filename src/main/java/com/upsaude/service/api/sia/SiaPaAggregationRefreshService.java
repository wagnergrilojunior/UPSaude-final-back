package com.upsaude.service.api.sia;

public interface SiaPaAggregationRefreshService {

    void refreshAll();

    void refreshEstabelecimento();

    void refreshProcedimento();

    void refreshCid();

    void refreshTemporal();
}

