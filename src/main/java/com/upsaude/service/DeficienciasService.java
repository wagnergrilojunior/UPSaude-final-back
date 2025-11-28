package com.upsaude.service;

import com.upsaude.api.request.DeficienciasRequest;
import com.upsaude.api.response.DeficienciasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Deficiências.
 *
 * @author UPSaúde
 */
public interface DeficienciasService {

    /**
     * Cria uma nova deficiência no sistema.
     *
     * @param request Dados da deficiência a ser criada
     * @return Resposta com os dados da deficiência criada
     */
    DeficienciasResponse criar(DeficienciasRequest request);

    /**
     * Obtém uma deficiência pelo seu ID.
     *
     * @param id ID da deficiência
     * @return Resposta com os dados da deficiência
     */
    DeficienciasResponse obterPorId(UUID id);

    /**
     * Lista todas as deficiências de forma paginada.
     *
     * @param pageable Parâmetros de paginação
     * @return Página com as deficiências
     */
    Page<DeficienciasResponse> listar(Pageable pageable);

    /**
     * Atualiza uma deficiência existente.
     *
     * @param id ID da deficiência a ser atualizada
     * @param request Dados atualizados da deficiência
     * @return Resposta com os dados da deficiência atualizada
     */
    DeficienciasResponse atualizar(UUID id, DeficienciasRequest request);

    /**
     * Exclui (desativa) uma deficiência do sistema.
     *
     * @param id ID da deficiência a ser excluída
     */
    void excluir(UUID id);
}

