package com.upsaude.service.medicacao;

import com.upsaude.api.request.medicacao.MedicacaoPacienteRequest;
import com.upsaude.api.request.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.medicacao.MedicacaoPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface MedicacaoPacienteService {

    MedicacaoPacienteResponse criar(MedicacaoPacienteRequest request);

    MedicacaoPacienteResponse criarSimplificado(MedicacaoPacienteSimplificadoRequest request);

    MedicacaoPacienteResponse obterPorId(UUID id);

    Page<MedicacaoPacienteResponse> listar(Pageable pageable);

    MedicacaoPacienteResponse atualizar(UUID id, MedicacaoPacienteRequest request);

    void excluir(UUID id);
}
