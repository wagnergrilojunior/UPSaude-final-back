package com.upsaude.service.api.support.farmacia;

import com.upsaude.entity.farmacia.Receita;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class ReceitaDomainService {

    public void validarReceitaValida(Receita receita) {
        // Validação de regra de negócio - implementar conforme necessário
        if (receita == null) {
            throw new IllegalArgumentException("Receita não pode ser nula");
        }
        // Adicionar outras validações conforme necessário
    }
}
