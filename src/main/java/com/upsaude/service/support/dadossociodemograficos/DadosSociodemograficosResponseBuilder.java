package com.upsaude.service.support.dadossociodemograficos;

import com.upsaude.api.response.DadosSociodemograficosResponse;
import com.upsaude.api.response.PacienteResponse;
import com.upsaude.entity.DadosSociodemograficos;
import com.upsaude.entity.Paciente;
import com.upsaude.mapper.DadosSociodemograficosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DadosSociodemograficosResponseBuilder {

    private final DadosSociodemograficosMapper mapper;

    public DadosSociodemograficosResponse build(DadosSociodemograficos entity) {
        DadosSociodemograficosResponse response = mapper.toResponse(entity);
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

