package com.upsaude.service.support.doencas;

import com.upsaude.api.request.doencas.DoencasRequest;
import com.upsaude.entity.doencas.Doencas;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoencasRelacionamentosHandler {

    public Doencas processarRelacionamentos(Doencas doenca, DoencasRequest request) {
        // Relacionamentos removidos - CidDoencas foi deletado
        return doenca;
    }
}

