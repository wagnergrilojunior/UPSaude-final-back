package com.upsaude.integration.cnes.service;

import com.upsaude.api.response.cnes.CnesEstabelecimentoDTO;
import com.upsaude.api.response.cnes.CnesSyncResultDTO;
import java.util.UUID;

public interface CnesIntegrationService {

    /**
     * Importa dados de um estabelecimento do CNES pelo número (apenas consulta e
     * retorno, sem persistência forçada).
     * Usado para preencher formulários de cadastro.
     */
    CnesEstabelecimentoDTO importarDadosCnes(String cnes);

    /**
     * Sincroniza dados oficiais de um estabelecimento já cadastrado (persiste
     * atualizações).
     */
    CnesSyncResultDTO sincronizarEstabelecimento(UUID estabelecimentoId);

    /**
     * Valida se existem divergências entre o cadastro local e o CNES.
     */
    CnesSyncResultDTO validarEstabelecimento(UUID estabelecimentoId);

    /**
     * Consulta dados detalhados do CNES (proxy).
     */
    CnesEstabelecimentoDTO consultarEstabelecimento(String cnes);
}
