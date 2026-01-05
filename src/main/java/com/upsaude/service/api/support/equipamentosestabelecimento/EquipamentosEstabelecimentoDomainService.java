package com.upsaude.service.api.support.equipamentosestabelecimento;

import com.upsaude.entity.estabelecimento.EquipamentosEstabelecimento;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class EquipamentosEstabelecimentoDomainService {

    public void validarPodeInativar(EquipamentosEstabelecimento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar equipamento do estabelecimento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Equipamento do estabelecimento já está inativo");
        }
    }

    public void validarPodeDeletar(EquipamentosEstabelecimento entity) {
        log.debug("Validando se equipamento do estabelecimento pode ser deletado. ID: {}", entity.getId());
        // Adicione aqui qualquer lógica de validação antes de um hard delete.
    }
}

