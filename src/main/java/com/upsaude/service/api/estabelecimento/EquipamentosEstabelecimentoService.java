package com.upsaude.service.api.estabelecimento;

import com.upsaude.api.request.estabelecimento.EquipamentosEstabelecimentoRequest;
import com.upsaude.api.response.estabelecimento.EquipamentosEstabelecimentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EquipamentosEstabelecimentoService {

    EquipamentosEstabelecimentoResponse criar(EquipamentosEstabelecimentoRequest request);

    EquipamentosEstabelecimentoResponse obterPorId(UUID id);

    Page<EquipamentosEstabelecimentoResponse> listar(Pageable pageable);

    EquipamentosEstabelecimentoResponse atualizar(UUID id, EquipamentosEstabelecimentoRequest request);

    void excluir(UUID id);
}
