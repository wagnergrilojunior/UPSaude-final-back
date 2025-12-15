package com.upsaude.service.support.profissionaissaude;

import com.upsaude.api.response.ProfissionaisSaudeResponse;
import com.upsaude.entity.ProfissionaisSaude;
import com.upsaude.mapper.ProfissionaisSaudeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfissionaisSaudeResponseBuilder {

    private final ProfissionaisSaudeMapper profissionaisSaudeMapper;

    public ProfissionaisSaudeResponse build(ProfissionaisSaude profissional) {
        return profissionaisSaudeMapper.toResponse(profissional);
    }
}
