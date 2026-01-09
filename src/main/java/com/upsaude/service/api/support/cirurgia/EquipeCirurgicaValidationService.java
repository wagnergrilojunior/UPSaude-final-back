package com.upsaude.service.api.support.cirurgia;

import com.upsaude.api.request.clinica.cirurgia.EquipeCirurgicaRequest;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EquipeCirurgicaValidationService {

    public void validarObrigatorios(EquipeCirurgicaRequest request) {
        if (request == null) {
            throw new BadRequestException("Request não pode ser nulo");
        }

        if (request.getCirurgia() == null) {
            throw new BadRequestException("Cirurgia é obrigatória");
        }

        if ((request.getProfissionais() == null || request.getProfissionais().isEmpty()) &&
            (request.getMedicos() == null || request.getMedicos().isEmpty())) {
            throw new BadRequestException("Equipe deve ter pelo menos um profissional ou médico");
        }
    }
}

