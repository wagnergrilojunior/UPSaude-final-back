package com.upsaude.service.api.support.cidades;

import com.upsaude.entity.referencia.geografico.Cidades;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class CidadesDomainService {

    public void validarPodeInativar(Cidades entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar cidade já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Cidade já está inativa");
        }
    }

    public void validarPodeDeletar(Cidades entity) {
        log.debug("Validando se cidade pode ser deletada. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}

