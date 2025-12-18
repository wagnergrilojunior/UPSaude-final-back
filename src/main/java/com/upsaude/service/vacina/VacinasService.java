package com.upsaude.service.vacina;

import com.upsaude.api.request.vacina.VacinasRequest;
import com.upsaude.api.response.vacina.VacinasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface VacinasService {

    VacinasResponse criar(VacinasRequest request);

    VacinasResponse obterPorId(UUID id);

    Page<VacinasResponse> listar(Pageable pageable);

    VacinasResponse atualizar(UUID id, VacinasRequest request);

    void excluir(UUID id);
}
