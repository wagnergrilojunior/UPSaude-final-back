package com.upsaude.service.support.educacaosaude;

import com.upsaude.api.response.EducacaoSaudeResponse;
import com.upsaude.entity.EducacaoSaude;
import com.upsaude.mapper.EducacaoSaudeMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducacaoSaudeResponseBuilder {

    private final EducacaoSaudeMapper mapper;

    public EducacaoSaudeResponse build(EducacaoSaude entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getProfissionalResponsavel());
            Hibernate.initialize(entity.getEquipeSaude());
            Hibernate.initialize(entity.getParticipantes());
            Hibernate.initialize(entity.getProfissionaisParticipantes());
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}
