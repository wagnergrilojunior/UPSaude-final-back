package com.upsaude.service.impl;

import com.upsaude.api.request.RelatorioEstatisticasRequest;
import com.upsaude.api.response.RelatorioEstatisticasResponse;
import com.upsaude.entity.*;
import com.upsaude.repository.*;
import com.upsaude.service.RelatoriosService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implementação do serviço de gerenciamento de Relatórios e Estatísticas.
 *
 * @author UPSaúde
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RelatoriosServiceImpl implements RelatoriosService {

    private final AtendimentoRepository atendimentoRepository;
    private final ConsultasRepository consultasRepository;
    private final ExamesRepository examesRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final VisitasDomiciliaresRepository visitasDomiciliaresRepository;

    @Override
    public RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request) {
        log.debug("Gerando relatório de estatísticas. Data início: {}, Data fim: {}", 
                request.getDataInicio(), request.getDataFim());

        LocalDate dataInicio = request.getDataInicio() != null ? request.getDataInicio() : LocalDate.now().minusMonths(1);
        LocalDate dataFim = request.getDataFim() != null ? request.getDataFim() : LocalDate.now();

        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        // Contagens totais usando métodos de query existentes
        long totalAtendimentos = contarAtendimentos(request, inicio, fim);
        long totalConsultas = contarConsultas(request, inicio, fim);
        long totalExames = contarExames(request, inicio, fim);
        long totalAgendamentos = contarAgendamentos(request, inicio, fim);
        long totalVisitasDomiciliares = contarVisitas(request, inicio, fim);

        // Contagem de pacientes únicos no período
        long totalPacientes = pacienteRepository.count();

        // Agregações por tipo/categoria (simplificadas - podem ser expandidas)
        Map<String, Long> atendimentosPorTipo = new HashMap<>();
        Map<String, Long> atendimentosPorEspecialidade = new HashMap<>();
        Map<String, Long> examesPorTipo = new HashMap<>();
        Map<String, Long> procedimentosPorTipo = new HashMap<>();
        Map<String, Long> atendimentosPorProfissional = new HashMap<>();

        // Calcular indicadores de saúde
        Map<String, BigDecimal> indicadoresSaude = calcularIndicadoresSaude(totalAgendamentos, totalConsultas, totalAtendimentos, inicio, fim);

        return RelatorioEstatisticasResponse.builder()
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalExames(totalExames)
                .totalProcedimentos(0L) // TODO: Implementar contagem de procedimentos
                .totalAgendamentos(totalAgendamentos)
                .totalPacientes(totalPacientes)
                .totalVisitasDomiciliares(totalVisitasDomiciliares)
                .atendimentosPorTipo(atendimentosPorTipo)
                .atendimentosPorEspecialidade(atendimentosPorEspecialidade)
                .examesPorTipo(examesPorTipo)
                .procedimentosPorTipo(procedimentosPorTipo)
                .atendimentosPorProfissional(atendimentosPorProfissional)
                .indicadoresSaude(indicadoresSaude)
                .build();
    }

    private long contarAtendimentos(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {
        // estabelecimento não faz parte do Request - buscar todos e filtrar por data
        return atendimentoRepository.findAll().stream()
                .filter(a -> a.getInformacoes() != null && 
                           a.getInformacoes().getDataHora() != null &&
                           !a.getInformacoes().getDataHora().isBefore(inicio) &&
                           !a.getInformacoes().getDataHora().isAfter(fim))
                .count();
    }

    private long contarConsultas(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {
        // estabelecimento não faz parte do Request - buscar todas e filtrar por data
        return consultasRepository.findAll().stream()
                .filter(c -> {
                    if (c.getInformacoes() != null && c.getInformacoes().getDataConsulta() != null) {
                        OffsetDateTime dataConsulta = c.getInformacoes().getDataConsulta();
                        return !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                    }
                    // Fallback para createdAt se dataConsulta não estiver disponível
                    return c.getCreatedAt() != null &&
                           !c.getCreatedAt().isBefore(inicio) &&
                           !c.getCreatedAt().isAfter(fim);
                })
                .count();
    }

    private long contarExames(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {
        // estabelecimento não faz parte do Request - buscar todos e filtrar por data
        return examesRepository.findAll().stream()
                .filter(e -> e.getDataExame() != null &&
                           !e.getDataExame().isBefore(inicio) &&
                           !e.getDataExame().isAfter(fim))
                .count();
    }

    private long contarAgendamentos(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {
        // estabelecimento não faz parte do Request - buscar todos por data
        List<Agendamento> agendamentos = agendamentoRepository.findByDataHoraBetweenOrderByDataHoraAsc(
                inicio, fim, PageRequest.of(0, Integer.MAX_VALUE)).getContent();
        return agendamentos.size();
    }

    private long contarVisitas(RelatorioEstatisticasRequest request, OffsetDateTime inicio, OffsetDateTime fim) {
        return visitasDomiciliaresRepository.findAll().stream()
                .filter(v -> v.getDataVisita() != null &&
                           !v.getDataVisita().isBefore(inicio) &&
                           !v.getDataVisita().isAfter(fim))
                .count();
    }

    private Map<String, BigDecimal> calcularIndicadoresSaude(long totalAgendamentos, 
                                                             long agendamentosRealizados,
                                                             long totalAtendimentos,
                                                             OffsetDateTime inicio,
                                                             OffsetDateTime fim) {
        Map<String, BigDecimal> indicadores = new HashMap<>();
        
        // Taxa de ocupação de agendamentos
        if (totalAgendamentos > 0) {
            BigDecimal taxaOcupacao = BigDecimal.valueOf(agendamentosRealizados)
                    .divide(BigDecimal.valueOf(totalAgendamentos), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            indicadores.put("taxaOcupacaoAgendamentos", taxaOcupacao);
        }
        
        // Média de atendimentos por dia
        long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio.toLocalDate(), fim.toLocalDate()) + 1;
        if (dias > 0) {
            BigDecimal mediaAtendimentosDia = BigDecimal.valueOf(totalAtendimentos)
                    .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
            indicadores.put("mediaAtendimentosPorDia", mediaAtendimentosDia);
        }
        
        return indicadores;
    }
}

