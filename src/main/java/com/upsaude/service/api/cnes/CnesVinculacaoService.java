package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;


public interface CnesVinculacaoService {

    
    CnesSincronizacaoResponse sincronizarVinculacoesPorProfissional(String cpfOuCns, boolean persistir);

    
    CnesSincronizacaoResponse sincronizarVinculacoesPorEstabelecimento(String codigoCnes, boolean persistir);
}
