package com.upsaude.service.api.support.sia;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;


public interface SiaDataEnricher {

    
    String enriquecerComProcedimento(String codigoProcedimento);

    
    String enriquecerComCid(String codigoCid);

    
    EstabelecimentosResponse enriquecerComEstabelecimento(String cnes);

    
    ProfissionaisSaudeResponse enriquecerComMedico(String cns);
}
