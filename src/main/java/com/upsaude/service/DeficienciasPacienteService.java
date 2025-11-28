package com.upsaude.service;

import com.upsaude.api.request.DeficienciasPacienteRequest;
import com.upsaude.api.response.DeficienciasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Deficiências de Paciente.
 *
 * @author UPSaúde
 */
public interface DeficienciasPacienteService {

    /**
     * Cria uma nova ligação entre paciente e deficiência no sistema.
     *
     * @param request Dados da ligação a ser criada
     * @return Resposta com os dados da ligação criada
     */
    DeficienciasPacienteResponse criar(DeficienciasPacienteRequest request);

    /**
     * Obtém uma ligação paciente-deficiência pelo seu ID.
     *
     * @param id ID da ligação
     * @return Resposta com os dados da ligação
     */
    DeficienciasPacienteResponse obterPorId(UUID id);

    /**
     * Lista todas as ligações paciente-deficiência de forma paginada.
     *
     * @param pageable Parâmetros de paginação
     * @return Página com as ligações
     */
    Page<DeficienciasPacienteResponse> listar(Pageable pageable);

    /**
     * Atualiza uma ligação paciente-deficiência existente.
     *
     * @param id ID da ligação a ser atualizada
     * @param request Dados atualizados da ligação
     * @return Resposta com os dados da ligação atualizada
     */
    DeficienciasPacienteResponse atualizar(UUID id, DeficienciasPacienteRequest request);

    /**
     * Exclui (desativa) uma ligação paciente-deficiência do sistema.
     *
     * @param id ID da ligação a ser excluída
     */
    void excluir(UUID id);
}

