package com.upsaude.service.api.support.consultas;

import com.upsaude.entity.clinica.atendimento.Consultas;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConsultasDomainService {

    public void aplicarDefaults(Consultas entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }

    public void validarPodeInativar(Consultas entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar consulta já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Consulta já está inativa");
        }
    }

    public void validarPodeDeletar(Consultas entity) {
        log.debug("Validando se consulta pode ser deletada. ID: {}", entity.getId());

    }
}
