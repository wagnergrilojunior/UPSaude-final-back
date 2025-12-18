package com.upsaude.service.equipe;

import com.upsaude.api.request.equipe.EquipeSaudeRequest;
import com.upsaude.api.response.equipe.EquipeSaudeResponse;
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
