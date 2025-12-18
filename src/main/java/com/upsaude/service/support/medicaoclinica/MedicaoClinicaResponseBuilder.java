package com.upsaude.service.support.medicaoclinica;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.medicao.MedicaoClinicaResponse;
import com.upsaude.entity.profissional.medicao.MedicaoClinica;
import com.upsaude.mapper.profissional.medicao.MedicaoClinicaMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicaoClinicaResponseBuilder {

    private final MedicaoClinicaMapper mapper;

    public MedicaoClinicaResponse build(MedicaoClinica entity) {
        if (entity != null) {
            if (entity.getPaciente() != null) Hibernate.initialize(entity.getPaciente());
        }
        return mapper.toResponse(entity);
    }
}
