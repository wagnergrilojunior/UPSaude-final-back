package com.upsaude.service.support.convenio;

import com.upsaude.api.request.ConvenioRequest;
import com.upsaude.entity.Convenio;
import com.upsaude.entity.Endereco;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.EnderecoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ConvenioRelacionamentosHandler {

    private final EnderecoRepository enderecoRepository;

    public Convenio processarRelacionamentos(Convenio convenio, ConvenioRequest request, UUID tenantId) {
        if (request.getEndereco() != null) {
            Endereco endereco = enderecoRepository.findById(request.getEndereco())
                .orElseThrow(() -> new NotFoundException("Endereço não encontrado com ID: " + request.getEndereco()));

            if (endereco.getTenant() == null || endereco.getTenant().getId() == null || !endereco.getTenant().getId().equals(tenantId)) {
                throw new NotFoundException("Endereço não encontrado com ID: " + request.getEndereco());
            }

            convenio.setEndereco(endereco);
        } else {
            convenio.setEndereco(null);
        }
        return convenio;
    }
}

