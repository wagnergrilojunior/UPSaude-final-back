package com.upsaude.service.support.configuracaoestabelecimento;

import com.upsaude.entity.ConfiguracaoEstabelecimento;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ConfiguracaoEstabelecimentoDomainService {

    public void validarPodeInativar(ConfiguracaoEstabelecimento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar configuração já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Configuração do estabelecimento já está inativa");
        }
    }
}

