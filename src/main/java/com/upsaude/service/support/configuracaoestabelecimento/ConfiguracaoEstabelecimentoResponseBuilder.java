package com.upsaude.service.support.configuracaoestabelecimento;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.ConfiguracaoEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.mapper.estabelecimento.ConfiguracaoEstabelecimentoMapper;

import lombok.RequiredArgsConstructor;

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

