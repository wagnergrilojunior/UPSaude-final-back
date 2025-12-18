package com.upsaude.service.clinica.medicacao;

import com.upsaude.api.request.clinica.medicacao.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacoesContinuasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MedicacoesContinuasPacienteService {

    MedicacoesContinuasPacienteResponse criar(MedicacoesContinuasPacienteRequest request);

    MedicacoesContinuasPacienteResponse obterPorId(UUID id);

    Page<MedicacoesContinuasPacienteResponse> listar(Pageable pageable);

    MedicacoesContinuasPacienteResponse atualizar(UUID id, MedicacoesContinuasPacienteRequest request);

    void excluir(UUID id);
}
