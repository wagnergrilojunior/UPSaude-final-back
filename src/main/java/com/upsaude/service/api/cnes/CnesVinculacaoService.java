package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;

/**
 * Service para sincronização de vinculações profissionais com o CNES.
 */
public interface CnesVinculacaoService {

    /**
     * Sincroniza vinculações de um profissional.
     */
    CnesSincronizacaoResponse sincronizarVinculacoesPorProfissional(String cpfOuCns, boolean persistir);

    /**
     * Sincroniza vinculações de um estabelecimento.
     */
    CnesSincronizacaoResponse sincronizarVinculacoesPorEstabelecimento(String codigoCnes, boolean persistir);
}
