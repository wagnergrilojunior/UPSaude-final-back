package com.upsaude.service.support.movimentacoesestoque;

import com.upsaude.api.response.MovimentacoesEstoqueResponse;
import com.upsaude.entity.MovimentacoesEstoque;
import com.upsaude.mapper.MovimentacoesEstoqueMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueResponseBuilder {

    private final MovimentacoesEstoqueMapper mapper;

    public MovimentacoesEstoqueResponse build(MovimentacoesEstoque entity) {
        return mapper.toResponse(entity);
    }
}
