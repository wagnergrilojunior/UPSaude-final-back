package com.upsaude.service.alergia;

import com.upsaude.api.request.alergia.AlergiasPacienteRequest;
import com.upsaude.api.request.AlergiasPacienteSimplificadoRequest;
import com.upsaude.api.response.alergia.AlergiasPacienteResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface AlergiasPacienteService {

    AlergiasPacienteResponse criar(AlergiasPacienteRequest request);

    AlergiasPacienteResponse criarSimplificado(AlergiasPacienteSimplificadoRequest request);

    AlergiasPacienteResponse obterPorId(UUID id);

    Page<AlergiasPacienteResponse> listar(Pageable pageable);

    AlergiasPacienteResponse atualizar(UUID id, AlergiasPacienteRequest request);

    void excluir(UUID id);
}
