package com.upsaude.service.support.paciente;

import com.upsaude.api.response.PacienteResponse;
import com.upsaude.api.response.PacienteSimplificadoResponse;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.PacienteMapper;
import com.upsaude.repository.projection.PacienteSimplificadoProjection;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PacienteResponseBuilder {

    private final PacienteMapper pacienteMapper;
    private final PacienteCalculatorService calculatorService;

    public PacienteResponse build(Paciente paciente) {
        PacienteResponse response = pacienteMapper.toResponse(paciente);
        
        if (response.getDataNascimento() != null) {
            response.setIdade(calculatorService.calcularIdade(response.getDataNascimento()));
        } else {
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
