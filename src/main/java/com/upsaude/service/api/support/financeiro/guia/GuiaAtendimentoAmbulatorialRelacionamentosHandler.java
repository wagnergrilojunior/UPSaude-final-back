package com.upsaude.service.api.support.financeiro.guia;

import com.upsaude.api.request.financeiro.GuiaAtendimentoAmbulatorialRequest;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.financeiro.CompetenciaFinanceira;
import com.upsaude.entity.financeiro.GuiaAtendimentoAmbulatorial;
import com.upsaude.entity.faturamento.DocumentoFaturamento;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.entity.sistema.multitenancy.Tenant;
import com.upsaude.exception.BadRequestException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.faturamento.DocumentoFaturamentoRepository;
import com.upsaude.repository.financeiro.CompetenciaFinanceiraRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuiaAtendimentoAmbulatorialRelacionamentosHandler {

    private final CompetenciaFinanceiraRepository competenciaFinanceiraRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final PacienteRepository pacienteRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final DocumentoFaturamentoRepository documentoFaturamentoRepository;

    public void resolver(GuiaAtendimentoAmbulatorial entity, GuiaAtendimentoAmbulatorialRequest request, UUID tenantId, Tenant tenant) {
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

        if (request.getAtendimento() != null) {
            Atendimento at = atendimentoRepository.findByIdAndTenant(request.getAtendimento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Atendimento não encontrado com ID: " + request.getAtendimento()));
            entity.setAtendimento(at);
        } else {
            entity.setAtendimento(null);
        }

        
        Paciente paciente = pacienteRepository.findById(request.getPaciente())
                .orElseThrow(() -> new BadRequestException("Paciente não encontrado com ID: " + request.getPaciente()));
        entity.setPaciente(paciente);

        if (request.getEstabelecimento() != null) {
            Estabelecimentos est = estabelecimentosRepository.findByIdAndTenant(request.getEstabelecimento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Estabelecimento não encontrado com ID: " + request.getEstabelecimento()));
            entity.setEstabelecimento(est);
        } else {
            entity.setEstabelecimento(null);
        }

        if (request.getDocumentoFaturamento() != null) {
            DocumentoFaturamento doc = documentoFaturamentoRepository.findByIdAndTenant(request.getDocumentoFaturamento(), tenantId)
                    .orElseThrow(() -> new BadRequestException("Documento de faturamento não encontrado com ID: " + request.getDocumentoFaturamento()));
            entity.setDocumentoFaturamento(doc);
        } else {
            entity.setDocumentoFaturamento(null);
        }
    }
}

