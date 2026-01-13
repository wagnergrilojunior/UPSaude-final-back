package com.upsaude.service.api.support.financeiro.contacontabil;

import com.upsaude.api.request.financeiro.ContaContabilRequest;
import com.upsaude.entity.financeiro.ContaContabil;
import com.upsaude.entity.financeiro.PlanoContas;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.financeiro.ContaContabilRepository;
import com.upsaude.repository.financeiro.PlanoContasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ContaContabilRelacionamentosHandler {

    private final PlanoContasRepository planoContasRepository;
    private final ContaContabilRepository contaContabilRepository;

    public void resolver(ContaContabil entity, ContaContabilRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        PlanoContas planoContas = planoContasRepository.findByIdAndTenant(request.getPlanoContas(), tenantId)
                .orElseThrow(() -> new BadRequestException("Plano de contas não encontrado com ID: " + request.getPlanoContas()));
        entity.setPlanoContas(planoContas);

        if (request.getContaPai() != null) {
            ContaContabil contaPai = contaContabilRepository.findByIdAndTenant(request.getContaPai(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Conta pai não encontrada com ID: " + request.getContaPai()));
            if (!contaPai.getPlanoContas().getId().equals(planoContas.getId())) {
                throw new BadRequestException("Conta pai deve pertencer ao mesmo plano de contas");
            }
            entity.setContaPai(contaPai);
        } else {
            entity.setContaPai(null);
        }
    }
}

