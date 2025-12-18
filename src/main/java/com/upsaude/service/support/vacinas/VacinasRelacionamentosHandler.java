package com.upsaude.service.support.vacinas;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.saude_publica.vacina.VacinasRequest;
import com.upsaude.entity.saude_publica.vacina.FabricantesVacina;
import com.upsaude.entity.saude_publica.vacina.Vacinas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.saude_publica.vacina.FabricantesVacinaRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class VacinasRelacionamentosHandler {

    private final FabricantesVacinaRepository fabricantesVacinaRepository;

    public Vacinas processarRelacionamentos(Vacinas vacina, VacinasRequest request) {
        if (request.getFabricante() != null) {
            FabricantesVacina fabricante = fabricantesVacinaRepository.findById(request.getFabricante())
                    .orElseThrow(() -> new NotFoundException("Fabricante não encontrado com ID: " + request.getFabricante()));
            vacina.setFabricante(fabricante);
        } else {
            vacina.setFabricante(null);
        }
        return vacina;
    }
}
