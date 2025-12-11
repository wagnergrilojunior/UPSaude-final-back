package com.upsaude.service;

import com.upsaude.api.request.ReceitasMedicasRequest;
import com.upsaude.api.response.ReceitasMedicasResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ReceitasMedicasService {

    ReceitasMedicasResponse criar(ReceitasMedicasRequest request);

    ReceitasMedicasResponse obterPorId(UUID id);

    Page<ReceitasMedicasResponse> listar(Pageable pageable);

    Page<ReceitasMedicasResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    ReceitasMedicasResponse atualizar(UUID id, ReceitasMedicasRequest request);

    void excluir(UUID id);
}
