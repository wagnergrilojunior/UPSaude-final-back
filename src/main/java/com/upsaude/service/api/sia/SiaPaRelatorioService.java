package com.upsaude.service.api.sia;

import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioProducaoResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopCidResponse;
import com.upsaude.api.response.sia.relatorios.SiaPaRelatorioTopProcedimentosResponse;

public interface SiaPaRelatorioService {

    SiaPaRelatorioProducaoResponse gerarRelatorioProducaoMensal(String uf, String competenciaInicio, String competenciaFim);

    SiaPaRelatorioTopProcedimentosResponse gerarTopProcedimentos(String uf, String competencia, Integer limit);

    SiaPaRelatorioTopCidResponse gerarTopCid(String uf, String competencia, Integer limit);
}

