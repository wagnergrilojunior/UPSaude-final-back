package com.upsaude.service.api.support.equipamentos;

import com.upsaude.api.request.estabelecimento.equipamento.EquipamentosRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EquipamentosValidationService {

    public void validarObrigatorios(EquipamentosRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do equipamento são obrigatórios");
        }
        if (request.getDadosIdentificacao() == null) {
            throw new BadRequestException("Dados de identificação do equipamento são obrigatórios");
        }
        if (!StringUtils.hasText(request.getDadosIdentificacao().getNome())) {
            throw new BadRequestException("Nome do equipamento é obrigatório");
        }
        if (request.getDadosIdentificacao().getTipo() == null) {
            throw new BadRequestException("Tipo de equipamento é obrigatório");
        }
    }
}
