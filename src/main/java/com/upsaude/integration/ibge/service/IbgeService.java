package com.upsaude.integration.ibge.service;

import com.upsaude.api.response.integracoes.IbgeSincronizacaoResponse;
import com.upsaude.integration.ibge.dto.IbgeMunicipioDTO;

/**
 * Interface do serviço de integração com IBGE
 */
public interface IbgeService {

    /**
     * Executa sincronização completa (todas as etapas na ordem correta)
     */
    IbgeSincronizacaoResponse sincronizarTudo(boolean sincronizarRegioes, boolean sincronizarEstados, 
                                               boolean sincronizarMunicipios, boolean atualizarPopulacao);

    /**
     * Sincroniza apenas as regiões
     */
    IbgeSincronizacaoResponse sincronizarRegioes();

    /**
     * Sincroniza apenas os estados (requer regiões já sincronizadas)
     */
    IbgeSincronizacaoResponse sincronizarEstados();

    /**
     * Sincroniza apenas os municípios (requer estados já sincronizados)
     */
    IbgeSincronizacaoResponse sincronizarMunicipios();

    /**
     * Atualiza apenas a população dos municípios (requer municípios já sincronizados)
     */
    IbgeSincronizacaoResponse atualizarPopulacao();

    /**
     * Valida um município específico por código IBGE (validação pontual)
     */
    IbgeMunicipioDTO validarMunicipioPorCodigoIbge(String codigoIbge);
}

