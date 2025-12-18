package com.upsaude.service.estabelecimento;

import com.upsaude.api.request.estabelecimento.EstabelecimentosRequest;
import com.upsaude.api.response.estabelecimento.EstabelecimentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EstabelecimentosService {

    EstabelecimentosResponse criar(EstabelecimentosRequest request);

    EstabelecimentosResponse obterPorId(UUID id);

    Page<EstabelecimentosResponse> listar(Pageable pageable);

    EstabelecimentosResponse atualizar(UUID id, EstabelecimentosRequest request);

    void excluir(UUID id);
}
