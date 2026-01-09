package com.upsaude.service.api.profissional;

import com.upsaude.api.request.profissional.ProfissionaisSaudeRequest;
import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface ProfissionaisSaudeService {

    ProfissionaisSaudeResponse criar(ProfissionaisSaudeRequest request);

    ProfissionaisSaudeResponse obterPorId(UUID id);

    ProfissionaisSaudeResponse obterPorCpf(String cpf);

    ProfissionaisSaudeResponse obterPorCns(String cns);

    Page<ProfissionaisSaudeResponse> listar(Pageable pageable);

    ProfissionaisSaudeResponse atualizar(UUID id, ProfissionaisSaudeRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
