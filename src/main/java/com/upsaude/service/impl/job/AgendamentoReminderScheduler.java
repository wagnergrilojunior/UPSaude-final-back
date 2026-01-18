package com.upsaude.service.impl.job;

import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.entity.estabelecimento.ConfiguracaoEstabelecimento;
import com.upsaude.enums.StatusAgendamentoEnum;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.estabelecimento.ConfiguracaoEstabelecimentoRepository;
import com.upsaude.repository.sistema.notificacao.NotificacaoRepository;
import com.upsaude.service.sistema.notificacao.NotificacaoOrchestrator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Slf4j
@Component
@RequiredArgsConstructor
@ConditionalOnProperty(name = "brevo.enabled", havingValue = "true", matchIfMissing = true)
public class AgendamentoReminderScheduler {

    private final AgendamentoRepository agendamentoRepository;
    private final ConfiguracaoEstabelecimentoRepository configuracaoEstabelecimentoRepository;
    private final NotificacaoRepository notificacaoRepository;
    private final NotificacaoOrchestrator notificacaoOrchestrator;

    
    @Scheduled(fixedDelayString = "${brevo.reminder-scheduler.interval-minutes:15}00000")
    @Transactional
    public void processarLembretesAgendamento() {
        OffsetDateTime agora = OffsetDateTime.now();
        OffsetDateTime limite24h = agora.plusHours(25); 
        OffsetDateTime limite1h = agora.plusHours(2); 

        
        List<Agendamento> agendamentos24h = buscarAgendamentosParaLembrete24h(agora, limite24h);
        List<Agendamento> agendamentos1h = buscarAgendamentosParaLembrete1h(agora, limite1h);

        log.debug("Processando lembretes: {} agendamentos para lembrete 24h, {} para lembrete 1h", 
                agendamentos24h.size(), agendamentos1h.size());

        processarLembretes24h(agendamentos24h, agora);
        processarLembretes1h(agendamentos1h, agora);
    }

    private List<Agendamento> buscarAgendamentosParaLembrete24h(OffsetDateTime agora, OffsetDateTime limite) {
        OffsetDateTime dataHoraMinima = agora.plusHours(23); 
        OffsetDateTime dataHoraMaxima = agora.plusHours(25); 

        List<Agendamento> agendamentos = agendamentoRepository.findAgendamentosParaLembrete24h(
                StatusAgendamentoEnum.CONFIRMADO, dataHoraMinima, dataHoraMaxima);

        
        return agendamentos.stream()
                .filter(a -> a.getPaciente() != null)
                .toList();
    }

    private List<Agendamento> buscarAgendamentosParaLembrete1h(OffsetDateTime agora, OffsetDateTime limite) {
        OffsetDateTime dataHoraMinima = agora.plusMinutes(55); 
        OffsetDateTime dataHoraMaxima = agora.plusHours(2); 

        List<Agendamento> agendamentos = agendamentoRepository.findAgendamentosParaLembrete1h(
                StatusAgendamentoEnum.CONFIRMADO, dataHoraMinima, dataHoraMaxima);

        
        return agendamentos.stream()
                .filter(a -> a.getPaciente() != null)
                .toList();
    }

    private void processarLembretes24h(List<Agendamento> agendamentos, OffsetDateTime agora) {
        for (Agendamento agendamento : agendamentos) {
            try {
                
                if (agendamento.getEstabelecimento() != null) {
                    UUID estabelecimentoId = agendamento.getEstabelecimento().getId();
                    ConfiguracaoEstabelecimento config = configuracaoEstabelecimentoRepository
                            .findByEstabelecimentoId(estabelecimentoId)
                            .orElse(null);

                    if (config != null && Boolean.FALSE.equals(config.getEnviaNotificacaoEmail())) {
                        log.debug("Notificações por e-mail desabilitadas para estabelecimento {}. Pulando lembrete 24h.", estabelecimentoId);
                        continue;
                    }

                    if (config != null && Boolean.FALSE.equals(config.getEnviaLembrete24h())) {
                        log.debug("Lembrete 24h desabilitado para estabelecimento {}. Pulando.", estabelecimentoId);
                        continue;
                    }
                }

                OffsetDateTime dataHoraAgendamento = agendamento.getDataHora();
                OffsetDateTime lembrete24h = dataHoraAgendamento.minusHours(24);

                
                boolean jaExisteLembrete24h = notificacaoRepository
                        .findByAgendamentoIdOrderByDataEnvioDesc(agendamento.getId(), 
                                org.springframework.data.domain.PageRequest.of(0, 10))
                        .stream()
                        .anyMatch(n -> com.upsaude.enums.TipoNotificacaoEnum.LEMBRETE_24H.equals(n.getTipoNotificacao())
                                && n.getStatusEnvio() != null && !"FALHA".equals(n.getStatusEnvio()));

                
                if (!jaExisteLembrete24h && lembrete24h.isAfter(agora) && lembrete24h.isBefore(agora.plusHours(2))) {
                    String email = obterEmailPaciente(agendamento);
                    if (email != null && !email.trim().isEmpty()) {
                        notificacaoOrchestrator.criarNotificacao(
                                com.upsaude.enums.TipoNotificacaoEnum.LEMBRETE_24H,
                                com.upsaude.enums.CanalNotificacaoEnum.EMAIL,
                                email,
                                agendamento.getPaciente().getNomeCompleto(),
                                "Lembrete: Agendamento em 24 horas",
                                "Você tem um agendamento em 24 horas.",
                                agendamento.getPaciente().getId(),
                                null,
                                agendamento.getId(),
                                agendamento.getEstabelecimento() != null ? agendamento.getEstabelecimento().getId() : null,
                                lembrete24h,
                                criarParamsAgendamento(agendamento)
                        );

                        agendamento.setNotificacaoEnviada24h(true);
                        agendamentoRepository.save(agendamento);
                        log.info("Lembrete 24h agendado para agendamento ID: {}", agendamento.getId());
                    }
                }
            } catch (Exception e) {
                log.error("Erro ao processar lembrete 24h para agendamento ID: {}", agendamento.getId(), e);
            }
        }
    }

    private void processarLembretes1h(List<Agendamento> agendamentos, OffsetDateTime agora) {
        for (Agendamento agendamento : agendamentos) {
            try {
                
                if (agendamento.getEstabelecimento() != null) {
                    UUID estabelecimentoId = agendamento.getEstabelecimento().getId();
                    ConfiguracaoEstabelecimento config = configuracaoEstabelecimentoRepository
                            .findByEstabelecimentoId(estabelecimentoId)
                            .orElse(null);

                    if (config != null && Boolean.FALSE.equals(config.getEnviaNotificacaoEmail())) {
                        log.debug("Notificações por e-mail desabilitadas para estabelecimento {}. Pulando lembrete 1h.", estabelecimentoId);
                        continue;
                    }

                    if (config != null && Boolean.FALSE.equals(config.getEnviaLembrete1h())) {
                        log.debug("Lembrete 1h desabilitado para estabelecimento {}. Pulando.", estabelecimentoId);
                        continue;
                    }
                }

                OffsetDateTime dataHoraAgendamento = agendamento.getDataHora();
                OffsetDateTime lembrete1h = dataHoraAgendamento.minusHours(1);

                
                boolean jaExisteLembrete1h = notificacaoRepository
                        .findByAgendamentoIdOrderByDataEnvioDesc(agendamento.getId(), 
                                org.springframework.data.domain.PageRequest.of(0, 10))
                        .stream()
                        .anyMatch(n -> com.upsaude.enums.TipoNotificacaoEnum.LEMBRETE_1H.equals(n.getTipoNotificacao())
                                && n.getStatusEnvio() != null && !"FALHA".equals(n.getStatusEnvio()));

                
                if (!jaExisteLembrete1h && lembrete1h.isAfter(agora) && lembrete1h.isBefore(agora.plusMinutes(30))) {
                    String email = obterEmailPaciente(agendamento);
                    if (email != null && !email.trim().isEmpty()) {
                        notificacaoOrchestrator.criarNotificacao(
                                com.upsaude.enums.TipoNotificacaoEnum.LEMBRETE_1H,
                                com.upsaude.enums.CanalNotificacaoEnum.EMAIL,
                                email,
                                agendamento.getPaciente().getNomeCompleto(),
                                "Lembrete: Agendamento em 1 hora",
                                "Você tem um agendamento em 1 hora.",
                                agendamento.getPaciente().getId(),
                                null,
                                agendamento.getId(),
                                agendamento.getEstabelecimento() != null ? agendamento.getEstabelecimento().getId() : null,
                                lembrete1h,
                                criarParamsAgendamento(agendamento)
                        );

                        agendamento.setNotificacaoEnviada1h(true);
                        agendamentoRepository.save(agendamento);
                        log.info("Lembrete 1h agendado para agendamento ID: {}", agendamento.getId());
                    }
                }
            } catch (Exception e) {
                log.error("Erro ao processar lembrete 1h para agendamento ID: {}", agendamento.getId(), e);
            }
        }
    }

    private String obterEmailPaciente(Agendamento agendamento) {
        if (agendamento.getPaciente() != null && agendamento.getPaciente().getContatos() != null) {
            return agendamento.getPaciente().getContatos().stream()
                    .filter(c -> c.getTipo() == com.upsaude.enums.TipoContatoEnum.EMAIL)
                    .map(c -> c.getEmail())
                    .filter(e -> e != null && !e.trim().isEmpty())
                    .findFirst()
                    .orElse(null);
        }
        return null;
    }

    private java.util.Map<String, Object> criarParamsAgendamento(Agendamento agendamento) {
        java.util.Map<String, Object> params = new java.util.HashMap<>();
        if (agendamento.getPaciente() != null) {
            params.put("pacienteNome", agendamento.getPaciente().getNomeCompleto());
        }
        if (agendamento.getDataHora() != null) {
            params.put("dataHora", agendamento.getDataHora().toString());
        }
        if (agendamento.getEstabelecimento() != null && agendamento.getEstabelecimento().getDadosIdentificacao() != null) {
            params.put("estabelecimentoNome", agendamento.getEstabelecimento().getDadosIdentificacao().getNome());
        }
        if (agendamento.getProfissional() != null && agendamento.getProfissional().getDadosPessoaisBasicos() != null) {
            params.put("profissionalNome", agendamento.getProfissional().getDadosPessoaisBasicos().getNomeCompleto());
        }
        if (agendamento.getMedico() != null && agendamento.getMedico().getDadosPessoaisBasicos() != null) {
            params.put("medicoNome", agendamento.getMedico().getDadosPessoaisBasicos().getNomeCompleto());
        }
        return params;
    }
}
