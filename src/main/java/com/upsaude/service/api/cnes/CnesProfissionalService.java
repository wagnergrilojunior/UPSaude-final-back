package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;

/**
 * Service para sincronização de profissionais com o CNES.
 */
public interface CnesProfissionalService {

    /**
     * Sincroniza profissional por CNS.
     */
    CnesSincronizacaoResponse sincronizarProfissionalPorCns(String numeroCns, boolean persistir);

    /**
     * Sincroniza profissional por CPF.
     */
    CnesSincronizacaoResponse sincronizarProfissionalPorCpf(String numeroCpf, boolean persistir);

    /**
     * Busca profissional no CNES (apenas busca, não sincroniza).
     */
    Object buscarProfissionalNoCnes(String cnsOuCpf);

    /**
     * Busca profissional sincronizado no banco local por CPF.
     */
    Object buscarProfissionalPorCpf(String cpf);

    /**
     * Busca profissional sincronizado no banco local por CNS.
     */
    Object buscarProfissionalPorCns(String cns);

    /**
     * Lista profissionais sincronizados com paginação.
     */
    Object listarProfissionais(int page, int size);
}
