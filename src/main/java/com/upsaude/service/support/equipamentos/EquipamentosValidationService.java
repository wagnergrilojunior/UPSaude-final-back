package com.upsaude.service.support.equipamentos;

import com.upsaude.api.request.equipamento.EquipamentosRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EquipamentosValidationService {

    public void validarObrigatorios(EquipamentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do equipamento são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do equipamento é obrigatório");
        }
        if (request.getTipo() == null) {
            throw new BadRequestException("Tipo de equipamento é obrigatório");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status do equipamento é obrigatório");
        }
    }
}
