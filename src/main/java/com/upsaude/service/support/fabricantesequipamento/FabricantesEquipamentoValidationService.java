package com.upsaude.service.support.fabricantesequipamento;

import com.upsaude.api.request.estabelecimento.equipamento.FabricantesEquipamentoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class FabricantesEquipamentoValidationService {

    public void validarObrigatorios(FabricantesEquipamentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do fabricante de equipamento são obrigatórios");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome do fabricante é obrigatório");
        }
    }
}
