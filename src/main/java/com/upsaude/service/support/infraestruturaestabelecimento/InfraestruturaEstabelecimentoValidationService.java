package com.upsaude.service.support.infraestruturaestabelecimento;

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class InfraestruturaEstabelecimentoValidationService {

    public void validarObrigatorios(InfraestruturaEstabelecimentoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da infraestrutura do estabelecimento são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
    }
}
