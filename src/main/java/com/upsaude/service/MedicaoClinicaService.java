package com.upsaude.service;

import com.upsaude.api.request.MedicaoClinicaRequest;
import com.upsaude.api.response.MedicaoClinicaResponse;
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
