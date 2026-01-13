package com.upsaude.service.sistema.integracao.rnds;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.clinica.atendimento.Atendimento;
import com.upsaude.entity.sistema.integracao.IntegracaoEvento;
import com.upsaude.enums.StatusIntegracaoEventoEnum;
import com.upsaude.enums.TipoErroIntegracaoEnum;
import com.upsaude.enums.TipoRecursoIntegracaoEnum;
import com.upsaude.integration.rnds.client.RndsClient;
import com.upsaude.integration.rnds.dto.RndsAppointmentDTO;
import com.upsaude.integration.rnds.dto.RndsEncounterDTO;
import com.upsaude.integration.rnds.mapper.RndsAgendamentoMapper;
import com.upsaude.integration.rnds.mapper.RndsAtendimentoMapper;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.sistema.integracao.IntegracaoEventoRepository;
import com.upsaude.service.sistema.integracao.validation.PreEnvioValidator;
import com.upsaude.service.sistema.integracao.validation.ValidacaoPreEnvioResultado;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class RndsEventProcessor {

    private final IntegracaoEventoRepository eventoRepository;
    private final PreEnvioValidator preEnvioValidator;
    private final RndsClient rndsClient;
    private final RndsAgendamentoMapper agendamentoMapper;
    private final RndsAtendimentoMapper atendimentoMapper;
    private final AgendamentoRepository agendamentoRepository;
    private final AtendimentoRepository atendimentoRepository;
    private final ObjectMapper objectMapper;

    @Transactional
    public void processarEvento(UUID eventoId) {
        IntegracaoEvento evento = eventoRepository.findById(eventoId)
                .orElseThrow(() -> new IllegalArgumentException("Evento de integração não encontrado: " + eventoId));

        if (evento.getStatus() != StatusIntegracaoEventoEnum.PENDENTE) {
            log.warn("Evento {} não está em status PENDENTE. Status atual: {}", eventoId, evento.getStatus());
            return;
        }

        evento.marcarProcessando();
        eventoRepository.save(evento);

        try {
            ValidacaoPreEnvioResultado validacao = validarPreEnvio(evento);
            if (!validacao.isValido()) {
                String mensagemErro = validacao.getMensagemErro();
                if (mensagemErro == null && !validacao.getDetalhesErros().isEmpty()) {
                    mensagemErro = String.join("; ", validacao.getDetalhesErros());
                }
                evento.marcarErro(
                        TipoErroIntegracaoEnum.VALIDACAO,
                        mensagemErro != null ? mensagemErro : "Validação pré-envio falhou",
                        null,
                        null
                );
                eventoRepository.save(evento);
                log.warn("Evento {} falhou na validação pré-envio: {}", eventoId, mensagemErro);
                return;
            }

            RndsClient.RndsResponse response = enviarParaRnds(evento);
            
            if (response.getStatusCode() >= 200 && response.getStatusCode() < 300) {
                String payloadResponse = objectMapper.writeValueAsString(response.getBody());
                evento.marcarSucesso(response.getProtocolo(), payloadResponse);
                eventoRepository.save(evento);
                log.info("Evento {} processado com sucesso. Protocolo: {}", eventoId, response.getProtocolo());
            } else {
                String payloadResponse = objectMapper.writeValueAsString(response.getBody());
                evento.marcarErro(
                        TipoErroIntegracaoEnum.COMUNICACAO,
                        "RNDS retornou status HTTP " + response.getStatusCode(),
                        payloadResponse,
                        null
                );
                eventoRepository.save(evento);
                log.warn("Evento {} falhou no envio para RNDS. Status: {}", eventoId, response.getStatusCode());
            }
        } catch (Exception e) {
            log.error("Erro ao processar evento {}: {}", eventoId, e.getMessage(), e);
            evento.marcarErro(
                    TipoErroIntegracaoEnum.COMUNICACAO,
                    "Erro ao processar evento: " + e.getMessage(),
                    null,
                    null
            );
            eventoRepository.save(evento);
        }
    }

    private ValidacaoPreEnvioResultado validarPreEnvio(IntegracaoEvento evento) {
        TipoRecursoIntegracaoEnum tipoRecurso = evento.getEntidadeTipo();
        UUID entidadeId = evento.getEntidadeId();

        return switch (tipoRecurso) {
            case APPOINTMENT -> {
                Agendamento agendamento = agendamentoRepository.findById(entidadeId)
                        .orElse(null);
                if (agendamento == null) {
                    yield ValidacaoPreEnvioResultado.erro(
                            TipoErroIntegracaoEnum.VALIDACAO,
                            "Agendamento não encontrado: " + entidadeId
                    );
                }
                yield preEnvioValidator.validarParaRnds(agendamento);
            }
            case ENCOUNTER -> {
                Atendimento atendimento = atendimentoRepository.findById(entidadeId)
                        .orElse(null);
                if (atendimento == null) {
                    yield ValidacaoPreEnvioResultado.erro(
                            TipoErroIntegracaoEnum.VALIDACAO,
                            "Atendimento não encontrado: " + entidadeId
                    );
                }
                yield preEnvioValidator.validarParaRnds(atendimento);
            }
            default -> ValidacaoPreEnvioResultado.erro(
                    TipoErroIntegracaoEnum.VALIDACAO,
                    "Tipo de recurso não suportado: " + tipoRecurso
            );
        };
    }

    private RndsClient.RndsResponse enviarParaRnds(IntegracaoEvento evento) {
        TipoRecursoIntegracaoEnum tipoRecurso = evento.getEntidadeTipo();
        UUID entidadeId = evento.getEntidadeId();

        return switch (tipoRecurso) {
            case APPOINTMENT -> {
                Agendamento agendamento = agendamentoRepository.findById(entidadeId)
                        .orElseThrow(() -> new IllegalArgumentException("Agendamento não encontrado: " + entidadeId));
                RndsAppointmentDTO dto = agendamentoMapper.toRnds(agendamento);
                completarPayloadAppointment(dto, agendamento);
                yield rndsClient.enviarAppointment(dto);
            }
            case ENCOUNTER -> {
                Atendimento atendimento = atendimentoRepository.findById(entidadeId)
                        .orElseThrow(() -> new IllegalArgumentException("Atendimento não encontrado: " + entidadeId));
                RndsEncounterDTO dto = atendimentoMapper.toRnds(atendimento);
                completarPayloadEncounter(dto, atendimento);
                yield rndsClient.enviarEncounter(dto);
            }
            default -> throw new IllegalArgumentException("Tipo de recurso não suportado: " + tipoRecurso);
        };
    }

    private void completarPayloadAppointment(RndsAppointmentDTO dto, Agendamento agendamento) {
        if (dto.getRequestedPeriod() == null && agendamento.getDataHora() != null) {
            dto.setRequestedPeriod(RndsAppointmentDTO.RndsPeriodDTO.builder()
                    .start(agendamento.getDataHora().toString())
                    .end(agendamento.getDataHoraFim() != null ? agendamento.getDataHoraFim().toString() : null)
                    .build());
        }
    }

    private void completarPayloadEncounter(RndsEncounterDTO dto, Atendimento atendimento) {
        if (dto.getPeriod() == null && atendimento.getInformacoes() != null) {
            if (atendimento.getInformacoes().getDataInicio() != null) {
                dto.setPeriod(RndsEncounterDTO.RndsPeriodDTO.builder()
                        .start(atendimento.getInformacoes().getDataInicio().toString())
                        .end(atendimento.getInformacoes().getDataFim() != null 
                                ? atendimento.getInformacoes().getDataFim().toString() 
                                : null)
                        .build());
            }
        }
    }
}
