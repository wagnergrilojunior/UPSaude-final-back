package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;

/**
 * Service para sincronização de equipamentos com o CNES.
 */
public interface CnesEquipamentoService {

    /**
     * Sincroniza equipamentos de um estabelecimento.
     */
    CnesSincronizacaoResponse sincronizarEquipamentosPorEstabelecimento(String codigoCnes, boolean persistir);
}
