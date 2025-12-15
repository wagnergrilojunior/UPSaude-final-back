package com.upsaude.service.support.medicacao;

import com.upsaude.api.request.MedicacaoRequest;
import com.upsaude.entity.FabricantesMedicamento;
import com.upsaude.entity.Medicacao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.FabricantesMedicamentoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicacaoRelacionamentosHandler {

    private final FabricantesMedicamentoRepository fabricantesMedicamentoRepository;

    public Medicacao processarRelacionamentos(Medicacao medicacao, MedicacaoRequest request) {
        if (request.getFabricanteEntity() != null) {
            FabricantesMedicamento fabricante = fabricantesMedicamentoRepository.findById(request.getFabricanteEntity())
                .orElseThrow(() -> new NotFoundException("Fabricante não encontrado com ID: " + request.getFabricanteEntity()));
            medicacao.setFabricanteEntity(fabricante);
        } else {
            medicacao.setFabricanteEntity(null);
        }
        return medicacao;
    }
}

