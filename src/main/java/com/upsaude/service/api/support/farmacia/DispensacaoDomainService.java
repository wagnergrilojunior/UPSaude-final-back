package com.upsaude.service.api.support.farmacia;

import com.upsaude.entity.farmacia.DispensacaoItem;
import com.upsaude.entity.farmacia.ReceitaItem;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DispensacaoDomainService {

    public void validarQuantidadeDispensada(ReceitaItem receitaItem, java.math.BigDecimal quantidadeDispensada) {
        // Validação de regra de negócio - implementar conforme necessário
        if (quantidadeDispensada == null || quantidadeDispensada.compareTo(java.math.BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Quantidade dispensada deve ser maior que zero");
        }
        // Adicionar outras validações conforme necessário
    }
}
