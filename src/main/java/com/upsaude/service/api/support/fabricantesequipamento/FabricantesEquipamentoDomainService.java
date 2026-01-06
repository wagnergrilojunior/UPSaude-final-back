package com.upsaude.service.api.support.fabricantesequipamento;

import com.upsaude.entity.estabelecimento.equipamento.FabricantesEquipamento;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class FabricantesEquipamentoDomainService {

    public void validarPodeInativar(FabricantesEquipamento entity) {
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar fabricante de equipamento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Fabricante de equipamento já está inativo");
        }
    }

    public void validarPodeDeletar(FabricantesEquipamento entity) {
        log.debug("Validando se fabricante de equipamento pode ser deletado. ID: {}", entity.getId());

    }
}
