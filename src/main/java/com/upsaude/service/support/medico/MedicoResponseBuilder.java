package com.upsaude.service.support.medico;

import com.upsaude.api.response.MedicosResponse;
import com.upsaude.entity.Medicos;
import com.upsaude.mapper.MedicosMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicoResponseBuilder {

    private final MedicosMapper medicosMapper;

    public MedicosResponse build(Medicos medico) {
        return medicosMapper.toResponse(medico);
    }
}
