package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;


public interface CnesEquipamentoService {

    
    CnesSincronizacaoResponse sincronizarEquipamentosPorEstabelecimento(String codigoCnes, boolean persistir);
}
