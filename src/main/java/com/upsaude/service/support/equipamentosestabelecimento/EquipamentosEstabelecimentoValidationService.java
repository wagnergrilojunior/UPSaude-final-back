package com.upsaude.service.support.equipamentosestabelecimento;

import com.upsaude.api.request.EquipamentosEstabelecimentoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class EquipamentosEstabelecimentoValidationService {

    public void validarObrigatorios(EquipamentosEstabelecimentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do equipamento do estabelecimento são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getEquipamento() == null) {
            throw new BadRequestException("Equipamento é obrigatório");
        }
    }
}
