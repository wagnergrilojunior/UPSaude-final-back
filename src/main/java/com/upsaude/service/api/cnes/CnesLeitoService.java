package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;

/**
 * Service para sincronização de leitos com o CNES.
 */
public interface CnesLeitoService {

    /**
     * Sincroniza leitos de um estabelecimento.
     */
    CnesSincronizacaoResponse sincronizarLeitosPorEstabelecimento(String codigoCnes);
}

