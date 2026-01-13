package com.upsaude.service.api.support.financeiro.orcamento;

import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrcamentoCompetenciaDomainService {

    public void aplicarDefaults(OrcamentoCompetencia entity) {
        if (entity.getSaldoAnterior() == null) entity.setSaldoAnterior(BigDecimal.ZERO);
        if (entity.getCreditos() == null) entity.setCreditos(BigDecimal.ZERO);
        if (entity.getReservasAtivas() == null) entity.setReservasAtivas(BigDecimal.ZERO);
        if (entity.getConsumos() == null) entity.setConsumos(BigDecimal.ZERO);
        if (entity.getEstornos() == null) entity.setEstornos(BigDecimal.ZERO);
        if (entity.getDespesasAdmin() == null) entity.setDespesasAdmin(BigDecimal.ZERO);
        if (entity.getSaldoFinal() == null) entity.setSaldoFinal(entity.calcularSaldoDisponivel());
    }

    public void validarSaldoNaoNegativo(OrcamentoCompetencia entity) {
        if (entity == null) throw new BadRequestException("Orçamento é obrigatório");
        if (entity.calcularSaldoDisponivel().compareTo(BigDecimal.ZERO) < 0) {
            throw new BadRequestException("Saldo orçamentário insuficiente para operação");
        }
    }

    public void validarPodeInativar(OrcamentoCompetencia entity) {
        if (entity == null) throw new BadRequestException("Orçamento é obrigatório");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar orçamento já inativo. ID: {}", entity.getId());
            throw new BadRequestException("Orçamento já está inativo");
        }
    }

    public void validarPodeDeletar(OrcamentoCompetencia entity) {
        if (entity == null) throw new BadRequestException("Orçamento é obrigatório");
    }
}

