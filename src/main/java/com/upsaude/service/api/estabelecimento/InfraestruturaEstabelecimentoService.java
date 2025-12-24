package com.upsaude.service.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.InfraestruturaEstabelecimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface InfraestruturaEstabelecimentoService {

    InfraestruturaEstabelecimentoResponse criar(InfraestruturaEstabelecimentoRequest request);

    InfraestruturaEstabelecimentoResponse obterPorId(UUID id);

    Page<InfraestruturaEstabelecimentoResponse> listar(Pageable pageable);

    InfraestruturaEstabelecimentoResponse atualizar(UUID id, InfraestruturaEstabelecimentoRequest request);

    void excluir(UUID id);
}
