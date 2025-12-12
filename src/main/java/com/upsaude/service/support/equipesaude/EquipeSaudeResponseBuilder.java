package com.upsaude.service.support.equipesaude;

import com.upsaude.api.response.EquipeSaudeResponse;
import com.upsaude.entity.EquipeSaude;
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
