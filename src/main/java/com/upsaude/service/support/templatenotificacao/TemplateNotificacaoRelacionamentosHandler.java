package com.upsaude.service.support.templatenotificacao;

import com.upsaude.api.request.notificacao.TemplateNotificacaoRequest;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.notificacao.TemplateNotificacao;
import com.upsaude.entity.sistema.Tenant;
import com.upsaude.service.support.estabelecimentos.EstabelecimentosTenantEnforcer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TemplateNotificacaoRelacionamentosHandler {

    private final EstabelecimentosTenantEnforcer estabelecimentosTenantEnforcer;

    public void resolver(TemplateNotificacao entity, TemplateNotificacaoRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        if (request.getEstabelecimento() != null) {
            Estabelecimentos estabelecimento = estabelecimentosTenantEnforcer.validarAcesso(request.getEstabelecimento(), tenantId);
            entity.setEstabelecimento(estabelecimento);
        } else {
            entity.setEstabelecimento(null);
        }

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
    }
}

