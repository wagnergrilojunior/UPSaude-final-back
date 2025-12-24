package com.upsaude.service.api.support.paciente;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Period;

@Service
public class PacienteCalculatorService {

    public int calcularIdade(LocalDate nascimento) {
        LocalDate hoje = LocalDate.now();
        return Period.between(nascimento, hoje).getYears();
    }
}
