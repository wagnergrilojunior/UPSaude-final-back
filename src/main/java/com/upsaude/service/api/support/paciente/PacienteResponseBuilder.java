package com.upsaude.service.api.support.paciente;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.api.response.paciente.PacienteSimplificadoResponse;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.paciente.PacienteMapper;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PacienteResponseBuilder {

    private final PacienteMapper pacienteMapper;
    private final PacienteCalculatorService calculatorService;

    public PacienteResponse build(Paciente paciente) {
        PacienteResponse response = pacienteMapper.toResponseCompleto(paciente);
        
        if (response != null && response.getDataNascimento() != null) {
            response.setIdade(calculatorService.calcularIdade(response.getDataNascimento()));
        } else if (response != null) {
            response.setIdade(null);
        }
        
        return response;
    }

    public PacienteSimplificadoResponse buildSimplificado(PacienteSimplificadoProjection projecao) {
        return pacienteMapper.fromProjection(projecao);
    }

    public PacienteSimplificadoResponse buildSimplificado(Paciente paciente) {
        return pacienteMapper.toSimplified(paciente);
    }
}
