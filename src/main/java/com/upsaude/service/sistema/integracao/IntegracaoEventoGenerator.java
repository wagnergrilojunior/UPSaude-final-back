package com.upsaude.service.sistema.integracao;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.clinica.atendimento.Consulta;
import com.upsaude.enums.SistemaIntegracaoEnum;
import com.upsaude.enums.TipoRecursoIntegracaoEnum;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class IntegracaoEventoGenerator {

    private final IntegracaoEventoService eventoService;
    private final ObjectMapper objectMapper;

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public void gerarEventosParaAgendamento(Agendamento agendamento) {
        if (agendamento == null || agendamento.getId() == null || agendamento.getTenant() == null) {
            return;
        }

        try {
            
            java.util.Map<String, Object> payloadMap = new java.util.HashMap<>();
            payloadMap.put("id", agendamento.getId());
            payloadMap.put("pacienteId", agendamento.getPaciente() != null ? agendamento.getPaciente().getId() : null);
            payloadMap.put("dataHora", agendamento.getDataHora());
            payloadMap.put("dataHoraFim", agendamento.getDataHoraFim());
            payloadMap.put("status", agendamento.getStatus() != null ? agendamento.getStatus().name() : null);
            payloadMap.put("motivoConsulta", agendamento.getMotivoConsulta());
            String payloadRequest = objectMapper.writeValueAsString(payloadMap);

            eventoService.criarEvento(
                    TipoRecursoIntegracaoEnum.APPOINTMENT,
                    agendamento.getId(),
                    SistemaIntegracaoEnum.RNDS,
                    "Appointment",
                    agendamento.getTenant(),
                    agendamento.getEstabelecimento(),
                    payloadRequest,
                    UUID.randomUUID().toString()
            );

            eventoService.criarEvento(
                    TipoRecursoIntegracaoEnum.APPOINTMENT,
                    agendamento.getId(),
                    SistemaIntegracaoEnum.ESUS_PEC,
                    "Agendamento",
                    agendamento.getTenant(),
                    agendamento.getEstabelecimento(),
                    payloadRequest,
                    UUID.randomUUID().toString()
            );
        } catch (Exception e) {
            log.error("Erro ao gerar eventos de integração para agendamento {}: {}", agendamento.getId(), e.getMessage(), e);
            
        }
    }

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public void gerarEventosParaAtendimento(Atendimento atendimento) {
        if (atendimento == null || atendimento.getId() == null || atendimento.getTenant() == null) {
            return;
        }

        try {
            
            java.util.Map<String, Object> payloadMap = new java.util.HashMap<>();
            payloadMap.put("id", atendimento.getId());
            payloadMap.put("pacienteId", atendimento.getPaciente() != null ? atendimento.getPaciente().getId() : null);
            payloadMap.put("profissionalId", atendimento.getProfissional() != null ? atendimento.getProfissional().getId() : null);
            if (atendimento.getInformacoes() != null) {
                payloadMap.put("statusAtendimento", atendimento.getInformacoes().getStatusAtendimento() != null ? atendimento.getInformacoes().getStatusAtendimento().name() : null);
            }
            String payloadRequest = objectMapper.writeValueAsString(payloadMap);

            eventoService.criarEvento(
                    TipoRecursoIntegracaoEnum.ENCOUNTER,
                    atendimento.getId(),
                    SistemaIntegracaoEnum.RNDS,
                    "Encounter",
                    atendimento.getTenant(),
                    atendimento.getEstabelecimento(),
                    payloadRequest,
                    UUID.randomUUID().toString()
            );

            eventoService.criarEvento(
                    TipoRecursoIntegracaoEnum.ENCOUNTER,
                    atendimento.getId(),
                    SistemaIntegracaoEnum.ESUS_PEC,
                    "Atendimento",
                    atendimento.getTenant(),
                    atendimento.getEstabelecimento(),
                    payloadRequest,
                    UUID.randomUUID().toString()
            );
        } catch (Exception e) {
            log.error("Erro ao gerar eventos de integração para atendimento {}: {}", atendimento.getId(), e.getMessage(), e);
            
        }
    }

    @Transactional(propagation = org.springframework.transaction.annotation.Propagation.REQUIRES_NEW)
    public void gerarEventosParaConsulta(Consulta consulta) {
        if (consulta == null || consulta.getId() == null || consulta.getTenant() == null) {
            return;
        }

        try {
            
            java.util.Map<String, Object> payloadMap = new java.util.HashMap<>();
            payloadMap.put("id", consulta.getId());
            payloadMap.put("atendimentoId", consulta.getAtendimento() != null ? consulta.getAtendimento().getId() : null);
            payloadMap.put("medicoId", consulta.getMedico() != null ? consulta.getMedico().getId() : null);
            if (consulta.getInformacoes() != null) {
                payloadMap.put("statusConsulta", consulta.getInformacoes().getStatusConsulta() != null ? consulta.getInformacoes().getStatusConsulta().name() : null);
            }
            String payloadRequest = objectMapper.writeValueAsString(payloadMap);

            if (consulta.getAtendimento() != null && consulta.getAtendimento().getId() != null) {
                eventoService.criarEvento(
                        TipoRecursoIntegracaoEnum.ENCOUNTER,
                        consulta.getAtendimento().getId(),
                        SistemaIntegracaoEnum.RNDS,
                        "Encounter",
                        consulta.getTenant(),
                        consulta.getEstabelecimento(),
                        payloadRequest,
                        UUID.randomUUID().toString()
                );
            }

            eventoService.criarEvento(
                    TipoRecursoIntegracaoEnum.CONSULTA,
                    consulta.getId(),
                    SistemaIntegracaoEnum.ESUS_PEC,
                    "Consulta",
                    consulta.getTenant(),
                    consulta.getEstabelecimento(),
                    payloadRequest,
                    UUID.randomUUID().toString()
            );
        } catch (Exception e) {
            log.error("Erro ao gerar eventos de integração para consulta {}: {}", consulta.getId(), e.getMessage(), e);
            
        }
    }
}
