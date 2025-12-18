package com.upsaude.service.support.medicacao;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.medicacao.MedicacaoRequest;
import com.upsaude.entity.clinica.medicacao.Medicacao;
import com.upsaude.entity.referencia.fabricante.FabricantesMedicamento;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.referencia.fabricante.FabricantesMedicamentoRepository;

import lombok.RequiredArgsConstructor;

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

