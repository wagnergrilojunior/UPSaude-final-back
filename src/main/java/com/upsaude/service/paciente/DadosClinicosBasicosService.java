package com.upsaude.service.paciente;

import com.upsaude.api.request.paciente.DadosClinicosBasicosRequest;
import com.upsaude.api.response.paciente.DadosClinicosBasicosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DadosClinicosBasicosService {

    DadosClinicosBasicosResponse criar(DadosClinicosBasicosRequest request);

    DadosClinicosBasicosResponse obterPorId(UUID id);

    DadosClinicosBasicosResponse obterPorPacienteId(UUID pacienteId);

    Page<DadosClinicosBasicosResponse> listar(Pageable pageable);

    DadosClinicosBasicosResponse atualizar(UUID id, DadosClinicosBasicosRequest request);

    void excluir(UUID id);
}
