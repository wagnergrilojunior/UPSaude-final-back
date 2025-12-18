package com.upsaude.service.cirurgia;

import com.upsaude.api.request.cirurgia.CirurgiaRequest;
import com.upsaude.api.response.cirurgia.CirurgiaResponse;
import com.upsaude.enums.StatusCirurgiaEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface CirurgiaService {

    CirurgiaResponse criar(CirurgiaRequest request);

    CirurgiaResponse obterPorId(UUID id);

    Page<CirurgiaResponse> listar(Pageable pageable);

    Page<CirurgiaResponse> listarPorPaciente(UUID pacienteId, Pageable pageable);

    Page<CirurgiaResponse> listarPorCirurgiaoPrincipal(UUID cirurgiaoPrincipalId, Pageable pageable);

    Page<CirurgiaResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<CirurgiaResponse> listarPorStatus(StatusCirurgiaEnum status, Pageable pageable);

    Page<CirurgiaResponse> listarPorPeriodo(OffsetDateTime inicio, OffsetDateTime fim, Pageable pageable);

    CirurgiaResponse atualizar(UUID id, CirurgiaRequest request);

    void excluir(UUID id);
}

