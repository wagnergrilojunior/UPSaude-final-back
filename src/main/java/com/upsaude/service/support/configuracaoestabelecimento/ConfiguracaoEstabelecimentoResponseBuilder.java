package com.upsaude.service.support.configuracaoestabelecimento;

import com.upsaude.api.response.estabelecimento.ConfiguracaoEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.mapper.ConfiguracaoEstabelecimentoMapper;
import lombok.RequiredArgsConstructor;
import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConfiguracaoEstabelecimentoResponseBuilder {

    private final ConfiguracaoEstabelecimentoMapper mapper;

    public ConfiguracaoEstabelecimentoResponse build(ConfiguracaoEstabelecimento entity) {
        if (entity != null) {
            Hibernate.initialize(entity.getEstabelecimento());
        }
        return mapper.toResponse(entity);
    }
}

