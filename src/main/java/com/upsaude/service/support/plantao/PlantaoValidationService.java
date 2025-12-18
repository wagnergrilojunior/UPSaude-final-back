package com.upsaude.service.support.plantao;

import com.upsaude.api.request.profissional.equipe.PlantaoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PlantaoValidationService {

    public void validarObrigatorios(PlantaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do plantão são obrigatórios");
        }
        if (request.getProfissional() == null && request.getMedico() == null) {
            throw new BadRequestException("Profissional ou médico é obrigatório");
        }
        if (request.getDataHoraInicio() == null) {
            throw new BadRequestException("Data e hora de início são obrigatórias");
        }
        if (request.getTipoPlantao() == null) {
            throw new BadRequestException("Tipo de plantão é obrigatório");
        }
    }
}
