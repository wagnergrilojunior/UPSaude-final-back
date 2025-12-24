package com.upsaude.service.api.support.servicosestabelecimento;

import com.upsaude.entity.estabelecimento.ServicosEstabelecimento;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ServicosEstabelecimentoDomainService {

    public void validarPodeInativar(ServicosEstabelecimento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar serviço já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Serviço do estabelecimento já está inativo");
        }
    }
}

