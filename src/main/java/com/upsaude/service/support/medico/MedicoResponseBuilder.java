package com.upsaude.service.support.medico;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.MedicosResponse;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.mapper.profissional.MedicosMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicoResponseBuilder {

    private final MedicosMapper medicosMapper;

    public MedicosResponse build(Medicos medico) {
        return medicosMapper.toResponse(medico);
    }
}
