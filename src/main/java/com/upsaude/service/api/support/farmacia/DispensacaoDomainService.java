package com.upsaude.service.api.support.farmacia;

import com.upsaude.entity.farmacia.Dispensacao;
import com.upsaude.entity.farmacia.DispensacaoItem;
import com.upsaude.entity.farmacia.ReceitaItem;
import com.upsaude.exception.BadRequestException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
public class DispensacaoDomainService {

    public void validarQuantidadeDispensada(ReceitaItem receitaItem, BigDecimal quantidadeDispensada) {
        if (receitaItem == null) {
            return;
        }

        BigDecimal quantidadePrescrita = receitaItem.getQuantidadePrescrita();
        if (quantidadePrescrita == null) {
            throw new BadRequestException("ReceitaItem não possui quantidade prescrita definida");
        }

        BigDecimal quantidadeJaDispensada = calcularQuantidadeJaDispensada(receitaItem);
        BigDecimal quantidadeDisponivel = quantidadePrescrita.subtract(quantidadeJaDispensada);

        if (quantidadeDispensada.compareTo(quantidadeDisponivel) > 0) {
            log.warn("Tentativa de dispensar quantidade maior que disponível. " +
                    "ReceitaItem ID: {}, Quantidade Prescrita: {}, Já Dispensada: {}, Tentativa: {}",
                    receitaItem.getId(), quantidadePrescrita, quantidadeJaDispensada, quantidadeDispensada);
            throw new BadRequestException(
                    String.format("Quantidade a dispensar (%.3f) excede a quantidade disponível (%.3f) do item prescrito (%.3f)",
                            quantidadeDispensada, quantidadeDisponivel, quantidadePrescrita));
        }
    }

    public BigDecimal calcularQuantidadeJaDispensada(ReceitaItem receitaItem) {
        if (receitaItem == null || receitaItem.getDispensacoes() == null || receitaItem.getDispensacoes().isEmpty()) {
            return BigDecimal.ZERO;
        }

        return receitaItem.getDispensacoes().stream()
                .filter(di -> di.getQuantidadeDispensada() != null)
                .map(DispensacaoItem::getQuantidadeDispensada)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public void validarPodeInativar(Dispensacao dispensacao) {
        if (Boolean.FALSE.equals(dispensacao.getActive())) {
            log.warn("Tentativa de inativar dispensação já inativa. ID: {}", dispensacao.getId());
            throw new BadRequestException("Dispensação já está inativa");
        }
    }

    public void validarPodeDeletar(Dispensacao dispensacao) {
        log.debug("Validando se dispensação pode ser deletada. ID: {}", dispensacao.getId());
    }
}

