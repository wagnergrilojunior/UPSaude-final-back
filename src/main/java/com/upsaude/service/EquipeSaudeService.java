package com.upsaude.service;

import com.upsaude.api.request.EquipeSaudeRequest;
import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.enums.StatusAtivoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface EquipeSaudeService {

    EquipeSaudeResponse criar(EquipeSaudeRequest request);

    EquipeSaudeResponse obterPorId(UUID id);

    Page<EquipeSaudeResponse> listar(Pageable pageable);

    Page<EquipeSaudeResponse> listarPorEstabelecimento(UUID estabelecimentoId, Pageable pageable);

    Page<EquipeSaudeResponse> listarPorStatus(StatusAtivoEnum status, UUID estabelecimentoId, Pageable pageable);

    EquipeSaudeResponse atualizar(UUID id, EquipeSaudeRequest request);

    void excluir(UUID id);
}
