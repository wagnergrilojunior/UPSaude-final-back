package com.upsaude.service.support.lgpdconsentimento;

import com.upsaude.api.response.LGPDConsentimentoResponse;
import com.upsaude.entity.LGPDConsentimento;
import com.upsaude.mapper.LGPDConsentimentoMapper;
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
