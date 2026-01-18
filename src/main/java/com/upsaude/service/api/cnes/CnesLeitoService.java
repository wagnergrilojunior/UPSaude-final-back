package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;


public interface CnesLeitoService {

    
    CnesSincronizacaoResponse sincronizarLeitosPorEstabelecimento(String codigoCnes, boolean persistir);

    
    java.util.List<Object> listarLeitosPorCnes(String codigoCnes);
}
