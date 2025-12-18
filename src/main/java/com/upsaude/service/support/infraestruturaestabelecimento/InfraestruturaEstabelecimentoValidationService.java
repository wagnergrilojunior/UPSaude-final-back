package com.upsaude.service.support.infraestruturaestabelecimento;

import com.upsaude.api.request.estabelecimento.InfraestruturaEstabelecimentoRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class InfraestruturaEstabelecimentoValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID da infraestrutura é obrigatório");
        }
    }

    public void validarObrigatorios(InfraestruturaEstabelecimentoRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados da infraestrutura são obrigatórios");
        }

        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getTipo() == null || request.getTipo().isBlank()) {
            throw new BadRequestException("Tipo é obrigatório");
        }
        if (request.getTipo() != null && request.getTipo().length() > 255) {
            throw new BadRequestException("Tipo deve ter no máximo 255 caracteres");
        }
        if (request.getCodigo() != null && request.getCodigo().length() > 50) {
            throw new BadRequestException("Código deve ter no máximo 50 caracteres");
        }
        if (request.getCodigoCnes() != null && request.getCodigoCnes().length() > 10) {
            throw new BadRequestException("Código CNES deve ter no máximo 10 caracteres");
        }
    }
}
