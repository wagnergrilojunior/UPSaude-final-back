package com.upsaude.service.clinica.medicacao;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.clinica.medicacao.MedicacaoPacienteRequest;
import com.upsaude.api.request.clinica.medicacao.MedicacaoPacienteSimplificadoRequest;
import com.upsaude.api.response.clinica.medicacao.MedicacaoPacienteResponse;

public interface MedicacaoPacienteService {

    MedicacaoPacienteResponse criar(MedicacaoPacienteRequest request);

    MedicacaoPacienteResponse criarSimplificado(MedicacaoPacienteSimplificadoRequest request);

    MedicacaoPacienteResponse obterPorId(UUID id);

    Page<MedicacaoPacienteResponse> listar(Pageable pageable);

    MedicacaoPacienteResponse atualizar(UUID id, MedicacaoPacienteRequest request);

    void excluir(UUID id);
}
