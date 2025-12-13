package com.upsaude.service.support.doencas;

import com.upsaude.api.request.DoencasRequest;
import com.upsaude.entity.CidDoencas;
import com.upsaude.entity.Doencas;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.CidDoencasRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DoencasRelacionamentosHandler {

    private final CidDoencasRepository cidDoencasRepository;

    public Doencas processarRelacionamentos(Doencas doenca, DoencasRequest request) {
        if (request.getCidPrincipal() != null) {
            CidDoencas cidPrincipal = cidDoencasRepository.findById(request.getCidPrincipal())
                .orElseThrow(() -> new NotFoundException("CID não encontrado com ID: " + request.getCidPrincipal()));
            doenca.setCidPrincipal(cidPrincipal);
        }
        return doenca;
    }
}

