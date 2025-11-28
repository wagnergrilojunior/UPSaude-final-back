package com.upsaude.service;

import com.upsaude.api.request.ConsultasRequest;
import com.upsaude.api.response.ConsultasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Consultas (Agendamentos).
 *
 * @author UPSaúde
 */
public interface ConsultasService {

    ConsultasResponse criar(ConsultasRequest request);

    ConsultasResponse obterPorId(UUID id);

    Page<ConsultasResponse> listar(Pageable pageable);

    /**
     * Lista todas as consultas de um estabelecimento, ordenadas por data decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable Parâmetros de paginação
     * @return Página com as consultas do estabelecimento
     */
    Page<ConsultasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ConsultasResponse atualizar(UUID id, ConsultasRequest request);

    void excluir(UUID id);
}

