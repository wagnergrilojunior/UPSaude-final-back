package com.upsaude.service;

import com.upsaude.api.request.ProfissionaisSaudeRequest;
import com.upsaude.api.response.ProfissionaisSaudeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProfissionaisSaudeService {

    ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request);

    ProfissionaisSaudeResponse obterPorId(UUID id);

    Page<ProfissionaisSaudeResponse> listar(Pageable pageable);

    ProfissionaisSaudeResponse atualizar(UUID id, ProfissionaisSaudeRequest request);

    void excluir(UUID id);

    void inativar(UUID id);

    void deletarPermanentemente(UUID id);
}
