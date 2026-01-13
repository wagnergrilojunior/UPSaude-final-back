package com.upsaude.service.api.support.consultas;

import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.enums.StatusConsultaEnum;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ConsultasDomainService {

    private final ConsultaStatusValidator statusValidator;

    public void aplicarDefaults(Consulta entity) {
        if (entity == null) return;
        if (entity.getActive() == null) {
            entity.setActive(true);
        }
    }

    public void validarTransicaoStatus(Consulta entity, StatusConsultaEnum novoStatus) {
        statusValidator.validarTransicao(entity, novoStatus);
    }

    public void validarPodeInativar(Consulta entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar consulta já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Consulta já está inativa");
        }
    }

    public void validarPodeDeletar(Consulta entity) {
        log.debug("Validando se consulta pode ser deletada. ID: {}", entity.getId());

    }
}
