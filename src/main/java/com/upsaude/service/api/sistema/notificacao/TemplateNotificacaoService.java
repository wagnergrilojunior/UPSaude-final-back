package com.upsaude.service.api.sistema.notificacao;

import com.upsaude.api.request.sistema.notificacao.TemplateNotificacaoRequest;
import com.upsaude.api.response.sistema.notificacao.TemplateNotificacaoResponse;
import com.upsaude.enums.CanalNotificacaoEnum;
import com.upsaude.enums.TipoNotificacaoEnum;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface TemplateNotificacaoService {

    TemplateNotificacaoResponse criar(TemplateNotificacaoRequest request);

    TemplateNotificacaoResponse obterPorId(UUID id);

    Page<TemplateNotificacaoResponse> listar(Pageable pageable, UUID estabelecimentoId, TipoNotificacaoEnum tipoNotificacao, CanalNotificacaoEnum canal, String nome);

    TemplateNotificacaoResponse atualizar(UUID id, TemplateNotificacaoRequest request);

    void excluir(UUID id);
}

