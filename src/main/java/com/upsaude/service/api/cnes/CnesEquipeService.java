package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;

/**
 * Service para sincronização de equipes com o CNES.
 */
public interface CnesEquipeService {

    /**
     * Sincroniza equipes de um estabelecimento.
     */
    CnesSincronizacaoResponse sincronizarEquipesPorEstabelecimento(String codigoCnes);

    /**
     * Sincroniza uma equipe específica.
     */
    CnesSincronizacaoResponse sincronizarEquipe(String codigoCnes, String ine);
}

