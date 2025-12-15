package com.upsaude.service.support.dadosclinicosbasicos;

import com.upsaude.api.response.DadosClinicosBasicosResponse;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.entity.DadosClinicosBasicos;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.DadosClinicosBasicosMapper;
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

