package com.upsaude.service;

import com.upsaude.api.request.EnderecoRequest;
import com.upsaude.api.response.EnderecoResponse;
import com.upsaude.entity.Endereco;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EnderecoService {

    EnderecoResponse criar(EnderecoRequest request);

    EnderecoResponse obterPorId(UUID id);

    Page<EnderecoResponse> listar(Pageable pageable);

    EnderecoResponse atualizar(UUID id, EnderecoRequest request);

    void excluir(UUID id);

    Endereco findOrCreate(Endereco endereco);
}
