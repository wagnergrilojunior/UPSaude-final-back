package com.upsaude.service.doencas;

import com.upsaude.api.request.doencas.DoencasRequest;
import com.upsaude.api.response.doencas.DoencasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface DoencasService {

    DoencasResponse criar(DoencasRequest request);

    DoencasResponse obterPorId(UUID id);

    Page<DoencasResponse> listar(Pageable pageable);

    Page<DoencasResponse> listarPorNome(String nome, Pageable pageable);

    Page<DoencasResponse> listarPorCodigoCid(String codigoCid, Pageable pageable);

    DoencasResponse atualizar(UUID id, DoencasRequest request);

    void excluir(UUID id);
}
