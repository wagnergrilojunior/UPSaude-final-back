package com.upsaude.service;

import com.upsaude.api.request.MedicaoClinicaRequest;
import com.upsaude.api.response.MedicaoClinicaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Medições Clínicas.
 *
 * @author UPSaúde
 */
public interface MedicaoClinicaService {

    /**
     * Cria uma nova medição clínica no sistema.
     *
     * @param request Dados da medição a ser criada
     * @return Resposta com os dados da medição criada
     */
    MedicaoClinicaResponse criar(MedicaoClinicaRequest request);

    /**
     * Obtém uma medição clínica pelo seu ID.
     *
     * @param id ID da medição
     * @return Resposta com os dados da medição
     */
    MedicaoClinicaResponse obterPorId(UUID id);

    /**
     * Lista todas as medições clínicas de forma paginada.
     *
     * @param pageable Parâmetros de paginação
     * @return Página com as medições
     */
    Page<MedicaoClinicaResponse> listar(Pageable pageable);

    /**
     * Lista todas as medições clínicas de um paciente, ordenadas por data/hora decrescente.
     *
     * @param pacienteId ID do paciente
     * @param pageable Parâmetros de paginação
     * @return Página com as medições do paciente
     */
    Page<MedicaoClinicaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    /**
     * Atualiza uma medição clínica existente.
     *
     * @param id ID da medição a ser atualizada
     * @param request Dados atualizados da medição
     * @return Resposta com os dados da medição atualizada
     */
    MedicaoClinicaResponse atualizar(UUID id, MedicaoClinicaRequest request);

    /**
     * Exclui (desativa) uma medição clínica do sistema.
     *
     * @param id ID da medição a ser excluída
     */
    void excluir(UUID id);
}

