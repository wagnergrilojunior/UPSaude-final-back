package com.upsaude.service;

import com.upsaude.api.request.DoencasCronicasPacienteRequest;
import com.upsaude.api.response.DoencasCronicasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

/**
 * Interface de serviço para operações CRUD relacionadas a DoencasCronicasPaciente.
 *
 * @author UPSaúde
 */
public interface DoencasCronicasPacienteService {

    DoencasCronicasPacienteResponse criar(DoencasCronicasPacienteRequest request);

    DoencasCronicasPacienteResponse obterPorId(UUID id);

    Page<DoencasCronicasPacienteResponse> listar(Pageable pageable);

    DoencasCronicasPacienteResponse atualizar(UUID id, DoencasCronicasPacienteRequest request);

    void excluir(UUID id);
}
