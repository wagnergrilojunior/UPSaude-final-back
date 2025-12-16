package com.upsaude.service.support.medicaoclinica;

import com.upsaude.api.response.MedicaoClinicaResponse;
import com.upsaude.entity.MedicaoClinica;
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
