package com.upsaude.service.support.educacaosaude;

import com.upsaude.api.request.educacao.EducacaoSaudeRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class EducacaoSaudeValidationService {

    public void validarObrigatorios(EducacaoSaudeRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da educação em saúde são obrigatórios");
        }
        if (request.getProfissionalResponsavel() == null) {
            throw new BadRequestException("Profissional responsável é obrigatório");
        }
        if (request.getTipoAtividade() == null) {
            throw new BadRequestException("Tipo de atividade é obrigatório");
        }
        if (!StringUtils.hasText(request.getTitulo())) {
            throw new BadRequestException("Título é obrigatório");
        }
        if (request.getDataHoraInicio() == null) {
            throw new BadRequestException("Data e hora de início são obrigatórias");
        }
    }
}
