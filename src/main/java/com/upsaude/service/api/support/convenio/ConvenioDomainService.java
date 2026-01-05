package com.upsaude.service.api.support.convenio;

import com.upsaude.entity.convenio.Convenio;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConvenioDomainService {

    public void validarPodeInativar(Convenio entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar convênio já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Convênio já está inativo");
        }
    }

    public void validarPodeDeletar(Convenio entity) {
        // Validações adicionais podem ser adicionadas aqui se necessário
        // Por exemplo, verificar se há registros relacionados que impedem a exclusão
    }
}
