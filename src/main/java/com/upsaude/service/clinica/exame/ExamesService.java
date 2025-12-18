package com.upsaude.service.clinica.exame;

import com.upsaude.api.request.clinica.exame.ExamesRequest;
import com.upsaude.api.response.clinica.exame.ExamesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface ExamesService {

    ExamesResponse criar(ExamesRequest request);

    ExamesResponse obterPorId(UUID id);

    Page<ExamesResponse> listar(Pageable pageable);

    Page<ExamesResponse> listar(Pageable pageable, UUID estabelecimentoId, UUID pacienteId, OffsetDateTime dataInicio, OffsetDateTime dataFim);

    Page<ExamesResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ExamesResponse atualizar(UUID id, ExamesRequest request);

    void excluir(UUID id);
}
