package com.upsaude.service;

import com.upsaude.api.request.AtendimentoRequest;
import com.upsaude.api.response.AtendimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Atendimentos.
 *
 * @author UPSaúde
 */
public interface AtendimentoService {

    /**
     * Cria um novo atendimento no sistema.
     *
     * @param request Dados do atendimento a ser criado
     * @return Resposta com os dados do atendimento criado
     */
    AtendimentoResponse criar(AtendimentoRequest request);

    /**
     * Obtém um atendimento pelo seu ID.
     *
     * @param id ID do atendimento
     * @return Resposta com os dados do atendimento
     */
    AtendimentoResponse obterPorId(UUID id);

    /**
     * Lista todos os atendimentos de forma paginada.
     *
     * @param pageable Parâmetros de paginação
     * @return Página com os atendimentos
     */
    Page<AtendimentoResponse> listar(Pageable pageable);

    /**
     * Lista todos os atendimentos de um paciente, ordenados por data/hora decrescente.
     *
     * @param pacienteId ID do paciente
     * @param pageable Parâmetros de paginação
     * @return Página com os atendimentos do paciente
     */
    Page<AtendimentoResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    /**
     * Lista todos os atendimentos realizados por um profissional, ordenados por data/hora decrescente.
     *
     * @param profissionalId ID do profissional de saúde
     * @param pageable Parâmetros de paginação
     * @return Página com os atendimentos do profissional
     */
    Page<AtendimentoResponse> listarPorProfissional(UUID profissionalId, Pageable pageable);

    /**
     * Lista todos os atendimentos de um estabelecimento, ordenados por data/hora decrescente.
     *
     * @param estabelecimentoId ID do estabelecimento
     * @param pageable Parâmetros de paginação
     * @return Página com os atendimentos do estabelecimento
     */
    Page<AtendimentoResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    /**
     * Atualiza um atendimento existente.
     *
     * @param id ID do atendimento a ser atualizado
     * @param request Dados atualizados do atendimento
     * @return Resposta com os dados do atendimento atualizado
     */
    AtendimentoResponse atualizar(UUID id, AtendimentoRequest request);

    /**
     * Exclui (desativa) um atendimento do sistema.
     *
     * @param id ID do atendimento a ser excluído
     */
    void excluir(UUID id);
}

