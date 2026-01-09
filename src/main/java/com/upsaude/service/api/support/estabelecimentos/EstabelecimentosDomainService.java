package com.upsaude.service.api.support.estabelecimentos;

import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EstabelecimentosDomainService {

    public void validarPodeInativar(Estabelecimentos entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar estabelecimento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Estabelecimento já está inativo");
        }
    }

    public void validarPodeDeletar(Estabelecimentos entity) {

    }
}
