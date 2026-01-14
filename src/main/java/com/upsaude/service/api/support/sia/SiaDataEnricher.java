package com.upsaude.service.api.support.sia;

import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;

/**
 * Service para enriquecimento de dados SIA com informações de outras entidades.
 */
public interface SiaDataEnricher {

    /**
     * Enriquece dados com informações do procedimento do SIGTAP.
     * 
     * @param codigoProcedimento Código do procedimento (10 dígitos)
     * @return Nome do procedimento ou null se não encontrado
     */
    String enriquecerComProcedimento(String codigoProcedimento);

    /**
     * Enriquece dados com informações do CID10.
     * 
     * @param codigoCid Código do CID (subcategoria)
     * @return Descrição do CID ou null se não encontrado
     */
    String enriquecerComCid(String codigoCid);

    /**
     * Enriquece dados com informações do estabelecimento.
     * 
     * @param cnes Código CNES (7 dígitos)
     * @return Dados do estabelecimento ou null se não encontrado
     */
    EstabelecimentosResponse enriquecerComEstabelecimento(String cnes);

    /**
     * Enriquece dados com informações do médico/profissional.
     * 
     * @param cns Código CNS do profissional
     * @return Dados do profissional ou null se não encontrado
     */
    ProfissionaisSaudeResponse enriquecerComMedico(String cns);
}
