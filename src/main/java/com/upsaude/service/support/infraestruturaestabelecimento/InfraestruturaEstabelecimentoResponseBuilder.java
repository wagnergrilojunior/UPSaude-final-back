package com.upsaude.service.support.infraestruturaestabelecimento;

import com.upsaude.api.response.estabelecimento.InfraestruturaEstabelecimentoResponse;
import com.upsaude.entity.estabelecimento.InfraestruturaEstabelecimento;
import com.upsaude.mapper.InfraestruturaEstabelecimentoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class InfraestruturaEstabelecimentoResponseBuilder {

    private final InfraestruturaEstabelecimentoMapper mapper;

    public InfraestruturaEstabelecimentoResponse build(InfraestruturaEstabelecimento entity) {
        return mapper.toResponse(entity);
    }
}
