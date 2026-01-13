package com.upsaude.service.api.financeiro;

import com.upsaude.api.request.financeiro.ExtratoBancarioImportadoRequest;
import com.upsaude.api.response.financeiro.ExtratoBancarioImportadoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ExtratoBancarioImportadoService {

    ExtratoBancarioImportadoResponse criar(ExtratoBancarioImportadoRequest request);

    ExtratoBancarioImportadoResponse obterPorId(UUID id);

    Page<ExtratoBancarioImportadoResponse> listar(Pageable pageable);

    ExtratoBancarioImportadoResponse atualizar(UUID id, ExtratoBancarioImportadoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}

