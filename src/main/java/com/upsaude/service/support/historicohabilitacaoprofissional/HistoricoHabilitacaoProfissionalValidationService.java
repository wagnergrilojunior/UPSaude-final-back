package com.upsaude.service.support.historicohabilitacaoprofissional;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class HistoricoHabilitacaoProfissionalValidationService {

    public void validarId(UUID id) {
        if (id == null) {
            log.warn("ID nulo recebido");
            throw new BadRequestException("ID do histórico de habilitação é obrigatório");
        }
    }

    public void validarObrigatorios(HistoricoHabilitacaoProfissionalRequest request) {
        if (request == null) {
            log.warn("Request nulo recebido");
            throw new BadRequestException("Dados do histórico de habilitação são obrigatórios");
        }

        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getTipoEvento() == null || request.getTipoEvento().isBlank()) {
            throw new BadRequestException("Tipo de evento é obrigatório");
        }
        if (request.getTipoEvento() != null && request.getTipoEvento().length() > 50) {
            throw new BadRequestException("Tipo de evento deve ter no máximo 50 caracteres");
        }
        if (request.getDataEvento() == null) {
            throw new BadRequestException("Data do evento é obrigatória");
        }
    }
}
