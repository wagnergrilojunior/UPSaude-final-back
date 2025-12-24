package com.upsaude.service.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.ServicosEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ServicosEstabelecimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ServicosEstabelecimentoService {

    ServicosEstabelecimentoResponse criar(ServicosEstabelecimentoRequest request);

    ServicosEstabelecimentoResponse obterPorId(UUID id);

    Page<ServicosEstabelecimentoResponse> listar(Pageable pageable,
                                                UUID estabelecimentoId,
                                                String nome,
                                                String codigoCnes,
                                                Boolean apenasAtivos);

    ServicosEstabelecimentoResponse atualizar(UUID id, ServicosEstabelecimentoRequest request);

    void excluir(UUID id);
}

