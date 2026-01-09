package com.upsaude.service.api.support.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConvenioRelacionamentosHandler {

    private final EstabelecimentosRepository estabelecimentosRepository;

    public void resolver(Convenio entity, ConvenioRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        if (request.getEstabelecimentoId() != null) {
            Estabelecimentos estabelecimento = estabelecimentosRepository.findById(request.getEstabelecimentoId())
                    .orElseThrow(() -> new BadRequestException("Estabelecimento não encontrado com ID: " + request.getEstabelecimentoId()));

            if (!estabelecimento.getTenant().getId().equals(tenantId)) {
                throw new BadRequestException("Estabelecimento não pertence ao tenant atual");
            }
            entity.setEstabelecimento(estabelecimento);
        }
    }
}
