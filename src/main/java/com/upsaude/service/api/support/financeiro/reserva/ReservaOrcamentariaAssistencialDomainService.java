package com.upsaude.service.api.support.financeiro.reserva;

import com.upsaude.entity.financeiro.OrcamentoCompetencia;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.exception.BadRequestException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservaOrcamentariaAssistencialDomainService {

    public void aplicarDefaults(ReservaOrcamentariaAssistencial entity) {
        if (entity.getIdempotencyKey() == null) {
            entity.setIdempotencyKey(UUID.randomUUID().toString());
        }
        if (entity.getStatus() == null) {
            entity.setStatus("ATIVA");
        }
    }

    public void validarSaldoDisponivel(OrcamentoCompetencia orcamento, BigDecimal valor) {
        if (orcamento == null) {
            throw new BadRequestException("Orçamento da competência não encontrado para o tenant");
        }
        if (!orcamento.temSaldoDisponivel(valor)) {
            throw new BadRequestException("Saldo insuficiente para reservar o valor informado");
        }
    }

    public void validarPodeInativar(ReservaOrcamentariaAssistencial entity) {
        if (entity == null) throw new BadRequestException("Reserva é obrigatória");
        if (Boolean.FALSE.equals(entity.getActive())) {
            log.warn("Tentativa de inativar reserva já inativa. ID: {}", entity.getId());
            throw new BadRequestException("Reserva já está inativa");
        }
    }

    public void validarPodeDeletar(ReservaOrcamentariaAssistencial entity) {
        if (entity == null) throw new BadRequestException("Reserva é obrigatória");
    }
}

