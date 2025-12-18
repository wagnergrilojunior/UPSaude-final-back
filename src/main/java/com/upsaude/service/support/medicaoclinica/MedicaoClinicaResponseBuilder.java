package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.response.medicao.MedicaoClinicaResponse;
import com.upsaude.entity.medicao.MedicaoClinica;
import com.upsaude.mapper.MedicaoClinicaMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicaoClinicaResponseBuilder {

    private final MedicaoClinicaMapper mapper;

    public MedicaoClinicaResponse build(MedicaoClinica entity) {
        return mapper.toResponse(entity);
    }
}
