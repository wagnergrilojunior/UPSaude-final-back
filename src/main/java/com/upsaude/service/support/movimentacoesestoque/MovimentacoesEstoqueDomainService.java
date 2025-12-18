package com.upsaude.service.support.movimentacoesestoque;

import com.upsaude.entity.estoque.MovimentacoesEstoque;
import org.springframework.stereotype.Service;

@Service
public class MovimentacoesEstoqueDomainService {

    public void aplicarDefaults(MovimentacoesEstoque entity) {
        // sem regras adicionais por enquanto
    }
}
