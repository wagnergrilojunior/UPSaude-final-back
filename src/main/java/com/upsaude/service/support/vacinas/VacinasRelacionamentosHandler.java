package com.upsaude.service.support.vacinas;

import com.upsaude.api.request.VacinasRequest;
import com.upsaude.entity.FabricantesVacina;
import com.upsaude.entity.Vacinas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.FabricantesVacinaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

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
