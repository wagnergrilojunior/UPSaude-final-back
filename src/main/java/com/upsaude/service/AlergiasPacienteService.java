package com.upsaude.service;

import com.upsaude.api.request.AlergiasPacienteRequest;
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

    AlergiasPacienteResponse obterPorId(UUID id);

    Page<AlergiasPacienteResponse> listar(Pageable pageable);

    AlergiasPacienteResponse atualizar(UUID id, AlergiasPacienteRequest request);

    void excluir(UUID id);
}
