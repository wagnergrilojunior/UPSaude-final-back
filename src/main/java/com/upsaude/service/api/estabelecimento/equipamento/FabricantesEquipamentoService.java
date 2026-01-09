package com.upsaude.service.api.estabelecimento.equipamento;

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.api.response.estabelecimento.equipamento.FabricantesEquipamentoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface FabricantesEquipamentoService {

    FabricantesEquipamentoResponse criar(FabricantesEquipamentoRequest request);

    FabricantesEquipamentoResponse obterPorId(UUID id);

    Page<FabricantesEquipamentoResponse> listar(Pageable pageable);

    FabricantesEquipamentoResponse atualizar(UUID id, FabricantesEquipamentoRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
