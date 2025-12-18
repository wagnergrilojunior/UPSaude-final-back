package com.upsaude.service.support.vacinacoes;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.saude_publica.vacina.VacinacoesRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

@Service
public class VacinacoesValidationService {

    public void validarObrigatorios(VacinacoesRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da vacinação são obrigatórios");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getVacina() == null) {
            throw new BadRequestException("Vacina é obrigatória");
        }
        if (request.getNumeroDose() == null) {
            throw new BadRequestException("Número da dose é obrigatório");
        }
        if (request.getDataAplicacao() == null) {
            throw new BadRequestException("Data de aplicação é obrigatória");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
    }
}

