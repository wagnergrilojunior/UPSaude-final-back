package com.upsaude.service.profissional;

import com.upsaude.api.request.profissional.ConselhosProfissionaisRequest;
import com.upsaude.api.response.profissional.ConselhosProfissionaisResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ConselhosProfissionaisService {

    ConselhosProfissionaisResponse criar(ConselhosProfissionaisRequest request);

    ConselhosProfissionaisResponse obterPorId(UUID id);

    Page<ConselhosProfissionaisResponse> listar(Pageable pageable);

    ConselhosProfissionaisResponse atualizar(UUID id, ConselhosProfissionaisRequest request);

    void excluir(UUID id);
}
