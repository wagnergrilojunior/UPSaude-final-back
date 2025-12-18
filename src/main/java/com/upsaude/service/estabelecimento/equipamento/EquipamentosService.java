package com.upsaude.service.estabelecimento.equipamento;

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.api.response.estabelecimento.equipamento.EquipamentosResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EquipamentosService {

    EquipamentosResponse criar(EquipamentosRequest request);

    EquipamentosResponse obterPorId(UUID id);

    Page<EquipamentosResponse> listar(Pageable pageable);

    EquipamentosResponse atualizar(UUID id, EquipamentosRequest request);

    void excluir(UUID id);
}
