package com.upsaude.service.support.historicohabilitacaoprofissional;

import com.upsaude.api.request.profissional.HistoricoHabilitacaoProfissionalRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class HistoricoHabilitacaoProfissionalValidationService {

    public void validarObrigatorios(HistoricoHabilitacaoProfissionalRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do histórico de habilitação profissional são obrigatórios");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (!StringUtils.hasText(request.getTipoEvento())) {
            throw new BadRequestException("Tipo de evento é obrigatório");
        }
    }
}
