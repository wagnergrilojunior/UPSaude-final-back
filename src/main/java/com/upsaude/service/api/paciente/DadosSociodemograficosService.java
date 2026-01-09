package com.upsaude.service.api.paciente;

import com.upsaude.api.request.paciente.DadosSociodemograficosRequest;
import com.upsaude.api.response.paciente.DadosSociodemograficosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DadosSociodemograficosService {

    DadosSociodemograficosResponse criar(DadosSociodemograficosRequest request);

    DadosSociodemograficosResponse obterPorId(UUID id);

    DadosSociodemograficosResponse obterPorPacienteId(UUID pacienteId);

    Page<DadosSociodemograficosResponse> listar(Pageable pageable);

    DadosSociodemograficosResponse atualizar(UUID id, DadosSociodemograficosRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
