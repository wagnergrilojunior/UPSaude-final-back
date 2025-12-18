package com.upsaude.service.support.escalatrabalho;

import com.upsaude.api.request.equipe.EscalaTrabalhoRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class EscalaTrabalhoValidationService {

    public void validarObrigatorios(EscalaTrabalhoRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da escala de trabalho são obrigatórios");
        }
        if (request.getProfissional() == null) {
            throw new BadRequestException("Profissional é obrigatório");
        }
        if (request.getDiaSemana() == null) {
            throw new BadRequestException("Dia da semana é obrigatório");
        }
        if (request.getHoraEntrada() == null) {
            throw new BadRequestException("Hora de entrada é obrigatória");
        }
        if (request.getHoraSaida() == null) {
            throw new BadRequestException("Hora de saída é obrigatória");
        }
        if (request.getHoraEntrada() != null && request.getHoraSaida() != null && !request.getHoraSaida().isAfter(request.getHoraEntrada())) {
            throw new BadRequestException("Hora de saída deve ser após a hora de entrada");
        }
    }
}
