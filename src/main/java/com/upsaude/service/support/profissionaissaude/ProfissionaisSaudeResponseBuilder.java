package com.upsaude.service.support.profissionaissaude;

import com.upsaude.api.response.profissional.ProfissionaisSaudeResponse;
import com.upsaude.entity.profissional.ProfissionaisSaude;
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
