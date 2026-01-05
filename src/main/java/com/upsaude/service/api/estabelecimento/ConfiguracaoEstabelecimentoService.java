package com.upsaude.service.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.ConfiguracaoEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.ConfiguracaoEstabelecimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConfiguracaoEstabelecimentoService {

    ConfiguracaoEstabelecimentoResponse criar(ConfiguracaoEstabelecimentoRequest request);

    ConfiguracaoEstabelecimentoResponse obterPorId(UUID id);

    ConfiguracaoEstabelecimentoResponse obterPorEstabelecimento(UUID estabelecimentoId);

    Page<ConfiguracaoEstabelecimentoResponse> listar(Pageable pageable, UUID estabelecimentoId);

    ConfiguracaoEstabelecimentoResponse atualizar(UUID id, ConfiguracaoEstabelecimentoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

