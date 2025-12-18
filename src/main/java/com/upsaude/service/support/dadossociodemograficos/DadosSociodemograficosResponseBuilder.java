package com.upsaude.service.support.dadossociodemograficos;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.paciente.DadosSociodemograficosResponse;
import com.upsaude.api.response.paciente.PacienteResponse;
import com.upsaude.entity.paciente.DadosSociodemograficos;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.mapper.paciente.DadosSociodemograficosMapper;

import lombok.RequiredArgsConstructor;

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

