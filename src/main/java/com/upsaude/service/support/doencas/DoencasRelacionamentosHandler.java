package com.upsaude.service.support.doencas;

import org.springframework.stereotype.Service;

import com.upsaude.api.request.clinica.doencas.DoencasRequest;
import com.upsaude.entity.clinica.doencas.Doencas;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DoencasRelacionamentosHandler {

    public Doencas processarRelacionamentos(Doencas doenca, DoencasRequest request) {
        return doenca;
    }
}

