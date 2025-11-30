package com.upsaude.service;

import com.upsaude.api.response.EnumInfoResponse;
import com.upsaude.api.response.EnumsResponse;

/**
 * Interface de serviço para operações relacionadas a Enums.
 *
 * @author UPSaúde
 */
public interface EnumsService {

    /**
     * Lista todas as informações de todos os enums do sistema.
     *
     * @return Response contendo todos os enums e seus valores
     */
    EnumsResponse listarTodosEnums();

    /**
     * Lista os nomes de todos os enums disponíveis.
     *
     * @return Lista com os nomes dos enums
     */
    java.util.List<String> listarNomesEnums();

    /**
     * Obtém informações de um enum específico pelo nome da classe.
     *
     * @param nomeEnum Nome da classe do enum (ex: "SexoEnum", "StatusAtivoEnum")
     * @return Informações do enum ou null se não encontrado
     */
    EnumInfoResponse obterEnumPorNome(String nomeEnum);
}

