package com.upsaude.service.support.plantao;

import com.upsaude.api.request.PlantaoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class PlantaoValidationService {

    public void validarObrigatorios(PlantaoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados do plantão são obrigatórios");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getDataHoraInicio() == null) {
            throw new BadRequestException("Data/hora de início é obrigatória");
        }
        if (request.getDataHoraFimPrevisto() == null) {
            throw new BadRequestException("Data/hora de fim previsto é obrigatória");
        }
        if (request.getTipoPlantao() == null) {
            throw new BadRequestException("Tipo de plantão é obrigatório");
        }
    }
}

