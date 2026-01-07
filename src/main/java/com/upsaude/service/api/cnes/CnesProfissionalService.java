package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;

/**
 * Service para sincronização de profissionais com o CNES.
 */
public interface CnesProfissionalService {

    /**
     * Sincroniza profissional por CNS.
     */
    CnesSincronizacaoResponse sincronizarProfissionalPorCns(String numeroCns);

    /**
     * Sincroniza profissional por CPF.
     */
    CnesSincronizacaoResponse sincronizarProfissionalPorCpf(String numeroCpf);

    /**
     * Busca profissional no CNES (apenas busca, não sincroniza).
     */
    Object buscarProfissionalNoCnes(String cnsOuCpf);
}

