package com.upsaude.service.api.sistema.auditoria;

import com.upsaude.api.request.sistema.auditoria.LogsAuditoriaRequest;
import com.upsaude.api.response.sistema.auditoria.LogsAuditoriaResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface LogsAuditoriaService {

    LogsAuditoriaResponse criar(LogsAuditoriaRequest request);

    LogsAuditoriaResponse obterPorId(UUID id);

    Page<LogsAuditoriaResponse> listar(Pageable pageable);

    LogsAuditoriaResponse atualizar(UUID id, LogsAuditoriaRequest request);

    void excluir(UUID id);

    void inativar(UUID id);
}
