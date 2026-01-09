package com.upsaude.service.api.geral;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.upsaude.api.request.geral.EnderecoRequest;
import com.upsaude.api.response.geral.EnderecoResponse;
import com.upsaude.entity.paciente.Endereco;

public interface EnderecoService {

    EnderecoResponse criar(EnderecoRequest request);

    EnderecoResponse obterPorId(UUID id);

    Page<EnderecoResponse> listar(Pageable pageable);

    EnderecoResponse atualizar(UUID id, EnderecoRequest request);

    void excluir(UUID id);

    Endereco findOrCreate(Endereco endereco);
}
