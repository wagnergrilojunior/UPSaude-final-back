package com.upsaude.service;

import com.upsaude.api.request.AlergiasPacienteRequest;
import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.response.AlergiasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a AlergiasPaciente.
 *
 * @author UPSaúde
 */
public interface AlergiasPacienteService {

    AlergiasPacienteResponse criar(AlergiasPacienteRequest request);

    /**
     * Cria uma nova alergia de paciente de forma simplificada.
     * Recebe apenas os IDs necessários e cria o registro com valores padrão.
     *
     * @param request request simplificado contendo apenas paciente, tenant e alergia
     * @return resposta contendo os dados da alergia de paciente criada
     * @throws NotFoundException se paciente, tenant ou alergia não forem encontrados
     * @throws BadRequestException se os dados fornecidos forem inválidos
     */
    AlergiasPacienteResponse criarSimplificado(AlergiasPacienteSimplificadoRequest request);

    AlergiasPacienteResponse obterPorId(UUID id);

    Page<AlergiasPacienteResponse> listar(Pageable pageable);

    AlergiasPacienteResponse atualizar(UUID id, AlergiasPacienteRequest request);

    void excluir(UUID id);
}
