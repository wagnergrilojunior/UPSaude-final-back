package com.upsaude.service.api.clinica.prontuario;

import java.util.UUID;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.upsaude.api.request.clinica.prontuario.DoencaPacienteRequest;
import com.upsaude.api.response.clinica.prontuario.DoencaPacienteResponse;

public interface DoencaPacienteService {
    DoencaPacienteResponse criar(DoencaPacienteRequest request);

    DoencaPacienteResponse obterPorId(UUID id);

    Page<DoencaPacienteResponse> listar(Pageable pageable);

    Page<DoencaPacienteResponse> listarPorProntuario(UUID prontuarioId, Pageable pageable);

    DoencaPacienteResponse atualizar(UUID id, DoencaPacienteRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
