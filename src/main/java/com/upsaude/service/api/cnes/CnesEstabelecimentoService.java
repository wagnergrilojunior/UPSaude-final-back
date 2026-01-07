package com.upsaude.service.api.cnes;

import com.upsaude.api.response.cnes.CnesSincronizacaoResponse;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;

import java.util.List;
import java.util.UUID;

/**
 * Service para sincronização de estabelecimentos com o CNES.
 */
public interface CnesEstabelecimentoService {

    /**
     * Sincroniza um estabelecimento específico por código CNES.
     * 
     * @param codigoCnes  Código CNES (7 dígitos)
     * @param competencia Competência no formato AAAAMM (opcional)
     * @return Resposta com detalhes da sincronização
     */
    CnesSincronizacaoResponse sincronizarEstabelecimentoPorCnes(String codigoCnes, String competencia,
            boolean persistir);

    /**
     * Sincroniza estabelecimentos de um município.
     * 
     * @param codigoMunicipio Código do município (IBGE)
     * @param competencia     Competência no formato AAAAMM (opcional)
     * @return Lista de respostas de sincronização
     */
    List<CnesSincronizacaoResponse> sincronizarEstabelecimentosPorMunicipio(String codigoMunicipio, String competencia,
            boolean persistir);

    /**
     * Atualiza dados complementares de estabelecimento.
     * 
     * @param codigoMunicipio Código do município (IBGE)
     * @param competencia     Competência no formato AAAAMM (opcional)
     * @return Resposta com detalhes da sincronização
     */
    CnesSincronizacaoResponse atualizarDadosComplementares(String codigoMunicipio, String competencia,
            boolean persistir);

    /**
     * Busca estabelecimento no CNES (apenas busca, não sincroniza).
     * 
     * @param codigoCnes Código CNES (7 dígitos)
     * @return Dados do estabelecimento no CNES
     */
    EstabelecimentosResponse buscarEstabelecimentoNoCnes(String codigoCnes);
}
