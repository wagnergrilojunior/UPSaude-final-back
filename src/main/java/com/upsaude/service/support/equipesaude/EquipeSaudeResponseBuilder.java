package com.upsaude.service.support.equipesaude;

import com.upsaude.api.response.equipe.EquipeSaudeResponse;
import com.upsaude.entity.equipe.EquipeSaude;
import com.upsaude.mapper.EquipeSaudeMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EquipeSaudeResponseBuilder {

    private final EquipeSaudeMapper mapper;

    public EquipeSaudeResponse build(EquipeSaude entity) {
        return mapper.toResponse(entity);
    }
}
