package com.upsaude.service.medicao;

import com.upsaude.api.request.medicao.MedicaoClinicaRequest;
import com.upsaude.api.response.medicao.MedicaoClinicaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MedicaoClinicaService {

    MedicaoClinicaResponse criar(MedicaoClinicaRequest request);

    MedicaoClinicaResponse obterPorId(UUID id);

    Page<MedicaoClinicaResponse> listar(Pageable pageable);

    Page<MedicaoClinicaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    MedicaoClinicaResponse atualizar(UUID id, MedicaoClinicaRequest request);

    void excluir(UUID id);
}
