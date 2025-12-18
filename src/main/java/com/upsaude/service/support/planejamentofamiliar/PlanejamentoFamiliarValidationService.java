package com.upsaude.service.support.planejamentofamiliar;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.planejamento.PlanejamentoFamiliarRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PlanejamentoFamiliarValidationService {

    public void validarObrigatorios(PlanejamentoFamiliarRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do planejamento familiar são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
    }

    public void validarConsistencias(PlanejamentoFamiliarRequest request) {
        if (request == null) return;

        validarNaoNegativo(request.getNumeroGestacoes(), "Número de gestações não pode ser negativo");
        validarNaoNegativo(request.getNumeroPartos(), "Número de partos não pode ser negativo");
        validarNaoNegativo(request.getNumeroAbortos(), "Número de abortos não pode ser negativo");
        validarNaoNegativo(request.getNumeroFilhosVivos(), "Número de filhos vivos não pode ser negativo");

        if (request.getDataInicioMetodo() != null && request.getDataInicioAcompanhamento() != null
            && request.getDataInicioMetodo().isBefore(request.getDataInicioAcompanhamento().minusDays(3650))) {
            // sanity check leve (não impede muito, só evita dados absurdos)
            throw new BadRequestException("Data de início do método parece inválida");
        }
    }

    private void validarNaoNegativo(Integer value, String message) {
        if (value != null && value < 0) {
            throw new BadRequestException(message);
        }
    }
}

