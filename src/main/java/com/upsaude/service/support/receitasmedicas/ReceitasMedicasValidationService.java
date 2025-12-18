package com.upsaude.service.support.receitasmedicas;

import com.upsaude.entity.paciente.Paciente;

import com.upsaude.api.request.clinica.medicacao.ReceitasMedicasRequest;
import com.upsaude.exception.BadRequestException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;

@Service
public class ReceitasMedicasValidationService {

    public void validarObrigatorios(ReceitasMedicasRequest request) {
        if (request == null) {
            throw new BadRequestException("Dados da receita médica são obrigatórios");
        }
        if (request.getMedico() == null) {
            throw new BadRequestException("Médico é obrigatório");
        }
        if (request.getPaciente() == null) {
            throw new BadRequestException("Paciente é obrigatório");
        }
        if (request.getEstabelecimento() == null) {
            throw new BadRequestException("Estabelecimento é obrigatório");
        }
        if (request.getNumeroReceita() == null || request.getNumeroReceita().isBlank()) {
            throw new BadRequestException("Número da receita é obrigatório");
        }
        if (request.getDataPrescricao() == null) {
            throw new BadRequestException("Data de prescrição é obrigatória");
        }
        if (request.getDataValidade() == null) {
            throw new BadRequestException("Data de validade é obrigatória");
        }
        if (request.getUsoContinuo() == null) {
            throw new BadRequestException("Indicação de uso contínuo é obrigatória");
        }
        if (request.getStatus() == null) {
            throw new BadRequestException("Status é obrigatório");
        }
    }

    public void validarPeriodo(OffsetDateTime inicio, OffsetDateTime fim) {
        if (inicio == null || fim == null) {
            throw new BadRequestException("Início e fim são obrigatórios para o filtro por período");
        }
        if (inicio.isAfter(fim)) {
            throw new BadRequestException("Data de início não pode ser maior que a data de fim");
        }
    }
}

