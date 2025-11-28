package com.upsaude.service;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.api.response.MedicacaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Medicações.
 *
 * @author UPSaúde
 */
public interface MedicacaoService {

    /**
     * Cria uma nova medicação no sistema.
     *
     * @param request Dados da medicação a ser criada
     * @return Resposta com os dados da medicação criada
     */
    MedicacaoResponse criar(MedicacaoRequest request);

    /**
     * Obtém uma medicação pelo seu ID.
     *
     * @param id ID da medicação
     * @return Resposta com os dados da medicação
     */
    MedicacaoResponse obterPorId(UUID id);

    /**
     * Lista todas as medicações de forma paginada.
     *
     * @param pageable Parâmetros de paginação
     * @return Página com as medicações
     */
    Page<MedicacaoResponse> listar(Pageable pageable);

    /**
     * Atualiza uma medicação existente.
     *
     * @param id ID da medicação a ser atualizada
     * @param request Dados atualizados da medicação
     * @return Resposta com os dados da medicação atualizada
     */
    MedicacaoResponse atualizar(UUID id, MedicacaoRequest request);

    /**
     * Exclui (desativa) uma medicação do sistema.
     *
     * @param id ID da medicação a ser excluída
     */
    void excluir(UUID id);
}

