package com.upsaude.service.support.historicoclinico;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.prontuario.HistoricoClinicoRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class HistoricoClinicoValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID do histórico clínico é obrigatório");
        }
    }

    public void validarObrigatorios(HistoricoClinicoRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados do histórico clínico são obrigatórios");
        }

        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getDataRegistro() == null) {
            throw new BadRequestException("Data do registro é obrigatória");
        }
        if (request.getTipoRegistro() == null || request.getTipoRegistro().isBlank()) {
            throw new BadRequestException("Tipo de registro é obrigatório");
        }
        if (request.getTipoRegistro() != null && request.getTipoRegistro().length() > 50) {
            throw new BadRequestException("Tipo de registro deve ter no máximo 50 caracteres");
        }
        if (request.getDescricao() == null || request.getDescricao().isBlank()) {
            throw new BadRequestException("Descrição é obrigatória");
        }
    }
}
