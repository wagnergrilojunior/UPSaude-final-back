package com.upsaude.service.support.especialidadesmedicas;

import com.upsaude.api.request.profissional.EspecialidadesMedicasRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EspecialidadesMedicasValidationService {

    public void validarObrigatorios(EspecialidadesMedicasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da especialidade médica são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome da especialidade é obrigatório");
        }
    }
}

