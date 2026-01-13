package com.upsaude.service.api.financeiro;

import java.util.UUID;

public interface FinanceiroIntegrationService {

    void reservarOrcamento(UUID agendamentoId);

    void estornarReserva(UUID agendamentoId, String motivo);

    void consumirReserva(UUID atendimentoId);

    void estornarConsumo(UUID atendimentoId, String motivo);

    void fecharCompetencia(UUID competenciaFinanceiraId);
}

