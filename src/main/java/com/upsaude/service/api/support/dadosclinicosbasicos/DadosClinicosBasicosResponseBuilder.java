package com.upsaude.service.api.support.dadosclinicosbasicos;

import com.upsaude.api.response.paciente.DadosClinicosBasicosResponse;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.entity.paciente.DadosClinicosBasicos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.paciente.DadosClinicosBasicosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DadosClinicosBasicosResponseBuilder {

    private final DadosClinicosBasicosMapper mapper;

    public DadosClinicosBasicosResponse build(DadosClinicosBasicos entity) {
        DadosClinicosBasicosResponse response = mapper.toResponse(entity);
        response.setPaciente(createPacienteResponseMinimal(entity.getPaciente()));
        return response;
    }

    private PacienteResponse createPacienteResponseMinimal(Paciente paciente) {
        if (paciente == null) {
            return null;
        }
        PacienteResponse response = new PacienteResponse();
        response.setId(paciente.getId());
        return response;
    }
}

