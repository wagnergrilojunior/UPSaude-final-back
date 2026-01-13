package com.upsaude.service.api.support.financeiro.reserva;

import com.upsaude.api.request.financeiro.ReservaOrcamentariaAssistencialRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.entity.financeiro.ReservaOrcamentariaAssistencial;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.financeiro.GuiaAtendimentoAmbulatorialRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReservaOrcamentariaAssistencialRelacionamentosHandler {

    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final GuiaAtendimentoAmbulatorialRepository guiaRepository;
    private final DocumentoFaturamentoRepository documentoRepository;

    public void resolver(ReservaOrcamentariaAssistencial entity, ReservaOrcamentariaAssistencialRequest request, UUID tenantId, Tenant tenant) {
        if (request == null) return;
        entity.setTenant(Objects.requireNonNull(tenant, "tenant é obrigatório"));

        CompetenciaFinanceira competencia = competenciaFinanceiraRepository.findById(request.getCompetencia())
                .orElseThrow(() -> new BadRequestException("Competência financeira não encontrada com ID: " + request.getCompetencia()));
        entity.setCompetencia(competencia);

        if (request.getAgendamento() != null) {
            Agendamento ag = agendamentoRepository.findByIdAndTenant(request.getAgendamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Agendamento não encontrado com ID: " + request.getAgendamento()));
            entity.setAgendamento(ag);
        } else {
            entity.setAgendamento(null);
        }

        if (request.getGuiaAmbulatorial() != null) {
            GuiaAtendimentoAmbulatorial guia = guiaRepository.findByIdAndTenant(request.getGuiaAmbulatorial(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Guia ambulatorial não encontrada com ID: " + request.getGuiaAmbulatorial()));
            entity.setGuiaAmbulatorial(guia);
        } else {
            entity.setGuiaAmbulatorial(null);
        }

        if (request.getDocumentoFaturamento() != null) {
            DocumentoFaturamento doc = documentoRepository.findByIdAndTenant(request.getDocumentoFaturamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Documento de faturamento não encontrado com ID: " + request.getDocumentoFaturamento()));
            entity.setDocumentoFaturamento(doc);
        } else {
            entity.setDocumentoFaturamento(null);
        }
    }
}

