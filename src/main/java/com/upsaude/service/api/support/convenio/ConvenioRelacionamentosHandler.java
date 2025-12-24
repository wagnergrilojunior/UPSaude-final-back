package com.upsaude.service.api.support.convenio;

import com.upsaude.api.request.convenio.ConvenioRequest;
import com.upsaude.entity.convenio.Convenio;
import com.upsaude.entity.paciente.Endereco;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.paciente.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConvenioRelacionamentosHandler {

    private final EnderecoRepository enderecoRepository;

    public void processarEndereco(ConvenioRequest request, Convenio convenio, UUID tenantId, Tenant tenant) {
        if (request.getEndereco() != null) {
            UUID enderecoId = request.getEndereco();
            Endereco endereco = enderecoRepository.findById(enderecoId)
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + enderecoId));

            if (endereco.getTenant() != null && endereco.getTenant().getId() != null
                && !endereco.getTenant().getId().equals(tenantId)) {
                throw new NotFoundException("Endereço não encontrado com ID: " + enderecoId);
            }

            convenio.setEndereco(endereco);
        } else {
            convenio.setEndereco(null);
        }
    }

    public void resolver(Convenio entity, ConvenioRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;

        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));
        processarEndereco(request, entity, tenantId, tenant);
    }
}
