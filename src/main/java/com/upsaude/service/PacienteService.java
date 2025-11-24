package com.upsaude.service;

import com.upsaude.api.request.PacienteRequest;
import com.upsaude.api.response.PacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a Pacientes.
 * Define os contratos para criação, leitura, atualização e exclusão de pacientes.
 *
 * @author UPSaúde
 */
public interface PacienteService {

    /**
     * Cria um novo paciente com base nos dados fornecidos.
     *
     * @param request dados do paciente a ser criado
     * @return resposta contendo os dados do paciente criado
     * @throws BadRequestException se os dados fornecidos forem inválidos
     * @throws ConflictException se já existir um paciente com o mesmo CPF
     */
    PacienteResponse criar(PacienteRequest request);

    /**
     * Busca um paciente pelo seu identificador único.
     *
     * @param id identificador único do paciente
     * @return resposta contendo os dados do paciente encontrado
     * @throws NotFoundException se o paciente não for encontrado
     */
    PacienteResponse obterPorId(UUID id);

    /**
     * Lista todos os pacientes de forma paginada.
     *
     * @param pageable informações de paginação (página, tamanho, ordenação)
     * @return página contendo os pacientes encontrados
     */
    Page<PacienteResponse> listar(Pageable pageable);

    /**
     * Atualiza os dados de um paciente existente.
     *
     * @param id identificador único do paciente a ser atualizado
     * @param request dados atualizados do paciente
     * @return resposta contendo os dados atualizados do paciente
     * @throws NotFoundException se o paciente não for encontrado
     * @throws BadRequestException se os dados fornecidos forem inválidos
     * @throws ConflictException se já existir outro paciente com o mesmo CPF
     */
    PacienteResponse atualizar(UUID id, PacienteRequest request);

    /**
     * Exclui (desativa) um paciente do sistema.
     *
     * @param id identificador único do paciente a ser excluído
     * @throws NotFoundException se o paciente não for encontrado
     */
    void excluir(UUID id);
}

