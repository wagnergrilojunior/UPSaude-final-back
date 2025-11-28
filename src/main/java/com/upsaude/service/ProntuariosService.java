package com.upsaude.service;

import com.upsaude.api.request.ProntuariosRequest;
import com.upsaude.api.response.ProntuariosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Prontuarios.
 *
 * @author UPSaúde
 */
public interface ProntuariosService {

    ProntuariosResponse criar(ProntuariosRequest request);

    ProntuariosResponse obterPorId(UUID id);

    Page<ProntuariosResponse> listar(Pageable pageable);

    /**
     * Lista todos os prontuários de um estabelecimento.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable Parâmetros de paginação
     * @return Página com os prontuários do estabelecimento
     */
    Page<ProntuariosResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ProntuariosResponse atualizar(UUID id, ProntuariosRequest request);

    void excluir(UUID id);
}
