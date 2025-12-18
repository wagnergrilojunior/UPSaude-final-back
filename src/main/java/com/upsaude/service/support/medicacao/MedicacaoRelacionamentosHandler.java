package com.upsaude.service.support.medicacao;

import com.upsaude.api.request.medicacao.MedicacaoRequest;
import com.upsaude.entity.fabricante.FabricantesMedicamento;
import com.upsaude.entity.medicacao.Medicacao;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.fabricante.FabricantesMedicamentoRepository;
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

