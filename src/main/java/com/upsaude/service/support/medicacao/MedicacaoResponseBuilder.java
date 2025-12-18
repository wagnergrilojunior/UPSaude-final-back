package com.upsaude.service.support.medicacao;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.clinica.medicacao.MedicacaoResponse;
import com.upsaude.entity.clinica.medicacao.Medicacao;
import com.upsaude.mapper.clinica.medicacao.MedicacaoMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MedicacaoResponseBuilder {

    private final MedicacaoMapper mapper;

    public MedicacaoResponse build(Medicacao entity) {
        return mapper.toResponse(entity);
    }
}

