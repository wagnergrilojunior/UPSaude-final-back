package com.upsaude.service.api.support.lgpdconsentimento;

import com.upsaude.api.response.sistema.lgpd.LGPDConsentimentoResponse;
import com.upsaude.entity.sistema.lgpd.LGPDConsentimento;
import com.upsaude.mapper.sistema.lgpd.LGPDConsentimentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LGPDConsentimentoResponseBuilder {

    private final LGPDConsentimentoMapper mapper;

    public LGPDConsentimentoResponse build(LGPDConsentimento entity) {
        return mapper.toResponse(entity);
    }
}
