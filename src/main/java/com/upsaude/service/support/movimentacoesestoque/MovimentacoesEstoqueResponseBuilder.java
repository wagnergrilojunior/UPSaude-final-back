package com.upsaude.service.support.movimentacoesestoque;

import org.hibernate.Hibernate;
import org.springframework.stereotype.Service;

import com.upsaude.api.response.estabelecimento.estoque.MovimentacoesEstoqueResponse;
import com.upsaude.entity.estabelecimento.estoque.MovimentacoesEstoque;
import com.upsaude.mapper.estabelecimento.estoque.MovimentacoesEstoqueMapper;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MovimentacoesEstoqueResponseBuilder {

    private final MovimentacoesEstoqueMapper mapper;

    public MovimentacoesEstoqueResponse build(MovimentacoesEstoque entity) {
        if (entity != null) {
            if (entity.getEstoqueVacina() != null) Hibernate.initialize(entity.getEstoqueVacina());
        }
        return mapper.toResponse(entity);
    }
}
