package com.upsaude.service.medicacao;

import com.upsaude.api.request.medicacao.MedicacoesContinuasPacienteRequest;
import com.upsaude.api.response.medicacao.MedicacoesContinuasPacienteResponse;
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
