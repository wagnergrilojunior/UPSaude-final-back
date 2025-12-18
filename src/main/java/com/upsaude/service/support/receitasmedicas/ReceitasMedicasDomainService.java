package com.upsaude.service.support.receitasmedicas;

import com.upsaude.entity.clinica.medicacao.ReceitasMedicas;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceitasMedicasDomainService {

    public void validarPodeInativar(ReceitasMedicas entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar receita médica já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Receita médica já está inativa");
        }
    }
}

