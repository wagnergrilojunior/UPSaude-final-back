package com.upsaude.service.paciente;

import com.upsaude.api.request.paciente.EnderecoRequest;
import com.upsaude.api.response.paciente.EnderecoResponse;
import com.upsaude.entity.paciente.Endereco;
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
