package com.upsaude.service.saude_publica.vacina;

import com.upsaude.api.request.saude_publica.vacina.VacinacoesRequest;
import com.upsaude.api.response.saude_publica.vacina.VacinacoesResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface VacinacoesService {

    VacinacoesResponse criar(VacinacoesRequest request);

    VacinacoesResponse obterPorId(UUID id);

    Page<VacinacoesResponse> listar(Pageable pageable,
                                   UUID estabelecimentoId,
                                   UUID pacienteId,
                                   UUID vacinaId,
                                   OffsetDateTime inicio,
                                   OffsetDateTime fim);

    VacinacoesResponse atualizar(UUID id, VacinacoesRequest request);

    void excluir(UUID id);
}
