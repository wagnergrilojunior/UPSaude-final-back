package com.upsaude.service.support.acaopromocaoprevencao;

import com.upsaude.api.request.educacao.AcaoPromocaoPrevencaoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AcaoPromocaoPrevencaoValidationService {

    public void validarObrigatorios(AcaoPromocaoPrevencaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da ação de promoção e prevenção são obrigatórios");
        }
        if (request.getProfissionalResponsavel() == null) {
            throw new BadRequestException("Profissional responsável é obrigatório");
        }
        if (request.getTipoAcao() == null) {
            throw new BadRequestException("Tipo de ação é obrigatório");
        }
        if (!StringUtils.hasText(request.getNome())) {
            throw new BadRequestException("Nome é obrigatório");
        }
        if (request.getDataInicio() == null) {
            throw new BadRequestException("Data de início é obrigatória");
        }
    }

    public void sanitizarDefaults(AcaoPromocaoPrevencaoRequest request) {
        if (request == null) return;
        // defaults de entidade são tratados no Creator
    }
}
