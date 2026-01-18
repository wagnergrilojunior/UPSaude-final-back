package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;


public interface CnesEquipeService {

    
    CnesSincronizacaoResponse sincronizarEquipesPorEstabelecimento(String codigoCnes, boolean persistir);

    
    CnesSincronizacaoResponse sincronizarEquipe(String codigoCnes, String ine, boolean persistir);
}
