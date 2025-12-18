package com.upsaude.service.support.equipesaude;

import org.springframework.stereotype.Service;

import com.upsaude.api.response.profissional.equipe.EquipeSaudeResponse;
import com.upsaude.entity.profissional.equipe.EquipeSaude;
import com.upsaude.mapper.profissional.equipe.EquipeSaudeMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EquipeSaudeResponseBuilder {

    private final EquipeSaudeMapper mapper;

    public EquipeSaudeResponse build(EquipeSaude entity) {
        return mapper.toResponse(entity);
    }
}
