package com.upsaude.service.api.sistema.notificacao;

import com.upsaude.api.request.sistema.notificacao.NotificacaoRequest;
import com.upsaude.api.response.sistema.notificacao.NotificacaoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.OffsetDateTime;
import java.util.UUID;

public interface NotificacaoService {

    NotificacaoResponse criar(NotificacaoRequest request);

    NotificacaoResponse obterPorId(UUID id);

    Page<NotificacaoResponse> listar(Pageable pageable,
                                    UUID estabelecimentoId,
                                    UUID pacienteId,
                                    UUID profissionalId,
                                    UUID agendamentoId,
                                    String statusEnvio,
                                    OffsetDateTime inicio,
                                    OffsetDateTime fim,
                                    Boolean usarPrevista);

    NotificacaoResponse atualizar(UUID id, NotificacaoRequest request);

    void excluir(UUID id);
}

