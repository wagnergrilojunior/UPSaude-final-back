package com.upsaude.service.support.medicacao;

import com.upsaude.api.response.MedicacaoResponse;
import com.upsaude.entity.Medicacao;
import com.upsaude.mapper.MedicacaoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MedicacaoResponseBuilder {

    private final MedicacaoMapper mapper;

    public MedicacaoResponse build(Medicacao entity) {
        return mapper.toResponse(entity);
    }
}

