package com.upsaude.service.api.support.historicoclinico;

import com.upsaude.api.request.clinica.prontuario.HistoricoClinicoRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class HistoricoClinicoValidationService {

    public void validarObrigatorios(HistoricoClinicoRequest request) {
        if (request == null) {
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
        if (request.getDescricao() == null || request.getDescricao().isBlank()) {
            throw new BadRequestException("Descrição é obrigatória");
        }
    }
}
