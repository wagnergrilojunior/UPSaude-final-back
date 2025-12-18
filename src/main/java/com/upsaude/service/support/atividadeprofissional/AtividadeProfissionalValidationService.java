package com.upsaude.service.support.atividadeprofissional;

import com.upsaude.api.request.profissional.AtividadeProfissionalRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AtividadeProfissionalValidationService {

    public void validarObrigatorios(AtividadeProfissionalRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da atividade profissional são obrigatórios");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getDataHora() == null) {
            throw new BadRequestException("Data e hora da atividade são obrigatórias");
        }
        if (request.getDataAtividade() == null) {
            throw new BadRequestException("Data da atividade é obrigatória");
        }
        if (request.getTipoAtividade() == null) {
            throw new BadRequestException("Tipo de atividade é obrigatório");
        }
        if (!StringUtils.hasText(request.getDescricao())) {
            throw new BadRequestException("Descrição da atividade é obrigatória");
        }
    }

    public void validarConsistencia(AtividadeProfissionalRequest request) {
        if (request == null) return;

        if (request.getDuracaoMinutos() != null && request.getDuracaoMinutos() < 0) {
            throw new BadRequestException("Duração (minutos) não pode ser negativa");
        }
        if (request.getQuantidadeAtendimentos() != null && request.getQuantidadeAtendimentos() < 0) {
            throw new BadRequestException("Quantidade de atendimentos não pode ser negativa");
        }
        if (request.getDataHoraInicio() != null && request.getDataHoraFim() != null
            && request.getDataHoraInicio().isAfter(request.getDataHoraFim())) {
            throw new BadRequestException("Data/hora início não pode ser maior que data/hora fim");
        }
    }
}

