package com.upsaude.service;

import com.upsaude.api.request.ExamesRequest;
import com.upsaude.api.response.ExamesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Exames.
 *
 * @author UPSaúde
 */
public interface ExamesService {

    ExamesResponse criar(ExamesRequest request);

    ExamesResponse obterPorId(UUID id);

    Page<ExamesResponse> listar(Pageable pageable);

    /**
     * Lista todos os exames de um estabelecimento, ordenados por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable Parâmetros de paginação
     * @return Página com os exames do estabelecimento
     */
    Page<ExamesResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ExamesResponse atualizar(UUID id, ExamesRequest request);

    void excluir(UUID id);
}
