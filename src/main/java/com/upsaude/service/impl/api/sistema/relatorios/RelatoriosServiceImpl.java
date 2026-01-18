package com.upsaude.service.impl.api.sistema.relatorios;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.upsaude.api.request.sistema.relatorios.RelatorioEstatisticasRequest;
import com.upsaude.api.response.sistema.relatorios.RelatorioEstatisticasResponse;
import com.upsaude.entity.agendamento.Agendamento;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.sistema.relatorios.RelatoriosService;
import com.upsaude.service.api.support.relatorios.TenantFilterHelper;
import com.upsaude.service.api.support.relatorios.EstabelecimentoFilterHelper;
import com.upsaude.service.api.support.relatorios.MedicoFilterHelper;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class RelatoriosServiceImpl implements RelatoriosService {

    private final AtendimentoRepository atendimentoRepository;
    private final ConsultasRepository consultasRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final TenantService tenantService;
    private final TenantFilterHelper tenantFilterHelper;
    private final EstabelecimentoFilterHelper estabelecimentoFilterHelper;
    private final MedicoFilterHelper medicoFilterHelper;

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "relatorioEstatisticas",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public RelatorioEstatisticasResponse gerarEstatisticas(RelatorioEstatisticasRequest request) {
        log.debug("Gerando relatório de estatísticas. Data início: {}, Data fim: {}",
                request.getDataInicio(), request.getDataFim());

        UUID tenantId = tenantService.validarTenantAtual();
        log.debug("Gerando relatório para tenant: {}", tenantId);

        LocalDate dataInicio = request.getDataInicio() != null ? request.getDataInicio() : LocalDate.now().minusMonths(1);
        LocalDate dataFim = request.getDataFim() != null ? request.getDataFim() : LocalDate.now();

        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        long totalAtendimentos = contarAtendimentos(request, tenantId, inicio, fim);
        long totalConsultas = contarConsultas(request, tenantId, inicio, fim);
        long totalAgendamentos = contarAgendamentos(request, tenantId, inicio, fim);

        long totalPacientes = contarPacientes(tenantId, request);

        Map<String, Long> atendimentosPorTipo = calcularAtendimentosPorTipo(request, tenantId, inicio, fim);
        Map<String, Long> atendimentosPorEspecialidade = calcularAtendimentosPorEspecialidade(request, tenantId, inicio, fim);
        Map<String, Long> examesPorTipo = new HashMap<>();
        Map<String, Long> procedimentosPorTipo = new HashMap<>();
        Map<String, Long> atendimentosPorProfissional = calcularAtendimentosPorProfissional(request, tenantId, inicio, fim);

        Map<String, BigDecimal> indicadoresSaude = calcularIndicadoresSaude(totalAgendamentos, totalConsultas, totalAtendimentos, inicio, fim);

        return RelatorioEstatisticasResponse.builder()
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalExames(0L)
                .totalProcedimentos(0L)
                .totalAgendamentos(totalAgendamentos)
                .totalPacientes(totalPacientes)
                .totalVisitasDomiciliares(0L)
                .atendimentosPorTipo(atendimentosPorTipo)
                .atendimentosPorEspecialidade(atendimentosPorEspecialidade)
                .examesPorTipo(examesPorTipo)
                .procedimentosPorTipo(procedimentosPorTipo)
                .atendimentosPorProfissional(atendimentosPorProfissional)
                .indicadoresSaude(indicadoresSaude)
                .build();
    }

    private long contarAtendimentos(RelatorioEstatisticasRequest request, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        log.debug("Contando atendimentos para tenant {} entre {} e {}", tenantId, inicio, fim);
        
        
        if (request.getEstabelecimentoId() != null) {
            if (!estabelecimentoFilterHelper.validarEstabelecimentoPertenceAoTenant(request.getEstabelecimentoId(), tenantId)) {
                throw new com.upsaude.exception.BadRequestException("Estabelecimento não pertence ao tenant");
            }
        }
        
        if (request.getMedicoId() != null) {
            if (!medicoFilterHelper.validarMedicoPertenceAoTenant(request.getMedicoId(), tenantId)) {
                throw new com.upsaude.exception.BadRequestException("Médico não pertence ao tenant");
            }
        }
        
        return atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> {
                    
                    if (a.getInformacoes() == null || a.getInformacoes().getDataHora() == null) {
                        return false;
                    }
                    if (a.getInformacoes().getDataHora().isBefore(inicio) || a.getInformacoes().getDataHora().isAfter(fim)) {
                        return false;
                    }
                    
                    
                    if (request.getEstabelecimentoId() != null) {
                        if (a.getEstabelecimento() == null || !request.getEstabelecimentoId().equals(a.getEstabelecimento().getId())) {
                            return false;
                        }
                    }
                    
                    
                    if (request.getMedicoId() != null) {
                        
                        
                    }
                    
                    return true;
                })
                .count();
    }

    private long contarConsultas(RelatorioEstatisticasRequest request, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        log.debug("Contando consultas para tenant {} entre {} e {}", tenantId, inicio, fim);
        
        return consultasRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(c -> {
                    if (c.getInformacoes() != null && c.getInformacoes().getDataConsulta() != null) {
                        OffsetDateTime dataConsulta = c.getInformacoes().getDataConsulta();
                        return !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                    }

                    return c.getCreatedAt() != null &&
                           !c.getCreatedAt().isBefore(inicio) &&
                           !c.getCreatedAt().isAfter(fim);
                })
                .count();
    }

    private long contarAgendamentos(RelatorioEstatisticasRequest request, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        log.debug("Contando agendamentos para tenant {} entre {} e {}", tenantId, inicio, fim);
        
        
        if (request.getEstabelecimentoId() != null) {
            if (!estabelecimentoFilterHelper.validarEstabelecimentoPertenceAoTenant(request.getEstabelecimentoId(), tenantId)) {
                throw new com.upsaude.exception.BadRequestException("Estabelecimento não pertence ao tenant");
            }
        }
        
        if (request.getMedicoId() != null) {
            if (!medicoFilterHelper.validarMedicoPertenceAoTenant(request.getMedicoId(), tenantId)) {
                throw new com.upsaude.exception.BadRequestException("Médico não pertence ao tenant");
            }
        }
        
        
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        List<Agendamento> agendamentos;
        
        if (request.getEstabelecimentoId() != null && request.getMedicoId() != null) {
            agendamentos = agendamentoRepository.findByEstabelecimentoIdAndMedicoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getEstabelecimentoId(), request.getMedicoId(), inicio, fim, tenantId, pageRequest).getContent();
        } else if (request.getEstabelecimentoId() != null) {
            agendamentos = agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getEstabelecimentoId(), inicio, fim, tenantId, pageRequest).getContent();
        } else if (request.getMedicoId() != null) {
            agendamentos = agendamentoRepository.findByMedicoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getMedicoId(), inicio, fim, tenantId, pageRequest).getContent();
        } else if (request.getEspecialidadeId() != null) {
            agendamentos = agendamentoRepository.findByEspecialidadeIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getEspecialidadeId(), inicio, fim, tenantId, pageRequest).getContent();
        } else {
            agendamentos = agendamentoRepository.findByDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    inicio, fim, tenantId, pageRequest).getContent();
        }
        
        return agendamentos.size();
    }

    private long contarPacientes(UUID tenantId, RelatorioEstatisticasRequest request) {
        log.debug("Contando pacientes para tenant {}", tenantId);
        
        
        if (request.getEstabelecimentoId() != null) {
            if (!estabelecimentoFilterHelper.validarEstabelecimentoPertenceAoTenant(request.getEstabelecimentoId(), tenantId)) {
                throw new com.upsaude.exception.BadRequestException("Estabelecimento não pertence ao tenant");
            }
            
            
            long pacientesAtendimentos = atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                    .getContent().stream()
                    .filter(a -> a.getEstabelecimento() != null && 
                            request.getEstabelecimentoId().equals(a.getEstabelecimento().getId()))
                    .map(a -> a.getPaciente().getId())
                    .distinct()
                    .count();
            
            long pacientesAgendamentos = agendamentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                    .getContent().stream()
                    .filter(a -> a.getEstabelecimento() != null && 
                            request.getEstabelecimentoId().equals(a.getEstabelecimento().getId()))
                    .map(a -> a.getPaciente().getId())
                    .distinct()
                    .count();
            
            
            return Math.max(pacientesAtendimentos, pacientesAgendamentos);
        }
        
        return pacienteRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getTotalElements();
    }

    private Map<String, Long> calcularAtendimentosPorTipo(RelatorioEstatisticasRequest request, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        Map<String, Long> resultado = new HashMap<>();
        
        atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> {
                    
                    if (a.getInformacoes() == null || a.getInformacoes().getDataHora() == null) {
                        return false;
                    }
                    OffsetDateTime dataHora = a.getInformacoes().getDataHora();
                    if (dataHora.isBefore(inicio) || dataHora.isAfter(fim)) {
                        return false;
                    }
                    
                    
                    if (request.getEstabelecimentoId() != null) {
                        if (a.getEstabelecimento() == null || !request.getEstabelecimentoId().equals(a.getEstabelecimento().getId())) {
                            return false;
                        }
                    }
                    
                    
                    if (request.getMedicoId() != null) {
                        
                    }
                    
                    return true;
                })
                .forEach(a -> {
                    String tipo = a.getClasseAtendimento() != null ? a.getClasseAtendimento().name() : "OUTROS";
                    resultado.put(tipo, resultado.getOrDefault(tipo, 0L) + 1);
                });
        
        return resultado;
    }

    private Map<String, Long> calcularAtendimentosPorEspecialidade(RelatorioEstatisticasRequest request, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        Map<String, Long> resultado = new HashMap<>();
        
        PageRequest pageRequest = PageRequest.of(0, Integer.MAX_VALUE);
        List<Agendamento> agendamentos;
        
        
        if (request.getEstabelecimentoId() != null && request.getMedicoId() != null) {
            agendamentos = agendamentoRepository.findByEstabelecimentoIdAndMedicoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getEstabelecimentoId(), request.getMedicoId(), inicio, fim, tenantId, pageRequest).getContent();
        } else if (request.getEstabelecimentoId() != null) {
            agendamentos = agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getEstabelecimentoId(), inicio, fim, tenantId, pageRequest).getContent();
        } else if (request.getMedicoId() != null) {
            agendamentos = agendamentoRepository.findByMedicoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getMedicoId(), inicio, fim, tenantId, pageRequest).getContent();
        } else if (request.getEspecialidadeId() != null) {
            agendamentos = agendamentoRepository.findByEspecialidadeIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    request.getEspecialidadeId(), inicio, fim, tenantId, pageRequest).getContent();
        } else {
            agendamentos = agendamentoRepository.findByDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                    inicio, fim, tenantId, pageRequest).getContent();
        }
        
        
        agendamentos.stream()
                .filter(a -> a.getEspecialidade() != null)
                .forEach(a -> {
                    String especialidadeNome = a.getEspecialidade().getNome() != null 
                            ? a.getEspecialidade().getNome() 
                            : a.getEspecialidade().getId().toString();
                    resultado.put(especialidadeNome, resultado.getOrDefault(especialidadeNome, 0L) + 1);
                });
        
        return resultado;
    }

    private Map<String, Long> calcularAtendimentosPorProfissional(RelatorioEstatisticasRequest request, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        Map<String, Long> resultado = new HashMap<>();
        
        atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> {
                    
                    if (a.getInformacoes() == null || a.getInformacoes().getDataHora() == null) {
                        return false;
                    }
                    OffsetDateTime dataHora = a.getInformacoes().getDataHora();
                    if (dataHora.isBefore(inicio) || dataHora.isAfter(fim)) {
                        return false;
                    }
                    
                    
                    if (request.getEstabelecimentoId() != null) {
                        if (a.getEstabelecimento() == null || !request.getEstabelecimentoId().equals(a.getEstabelecimento().getId())) {
                            return false;
                        }
                    }
                    
                    
                    if (request.getMedicoId() != null) {
                        
                    }
                    
                    return a.getProfissional() != null;
                })
                .forEach(a -> {
                    String profissionalNome = a.getProfissional().getDadosPessoaisBasicos() != null 
                            && a.getProfissional().getDadosPessoaisBasicos().getNomeCompleto() != null
                            ? a.getProfissional().getDadosPessoaisBasicos().getNomeCompleto() 
                            : a.getProfissional().getId().toString();
                    resultado.put(profissionalNome, resultado.getOrDefault(profissionalNome, 0L) + 1);
                });
        
        return resultado;
    }

    private Map<String, BigDecimal> calcularIndicadoresSaude(long totalAgendamentos,
                                                             long agendamentosRealizados,
                                                             long totalAtendimentos,
                                                             OffsetDateTime inicio,
                                                             OffsetDateTime fim) {
        Map<String, BigDecimal> indicadores = new HashMap<>();

        if (totalAgendamentos > 0) {
            BigDecimal taxaOcupacao = BigDecimal.valueOf(agendamentosRealizados)
                    .divide(BigDecimal.valueOf(totalAgendamentos), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            indicadores.put("taxaOcupacaoAgendamentos", taxaOcupacao);
        }

        long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio.toLocalDate(), fim.toLocalDate()) + 1;
        if (dias > 0) {
            BigDecimal mediaAtendimentosDia = BigDecimal.valueOf(totalAtendimentos)
                    .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
            indicadores.put("mediaAtendimentosPorDia", mediaAtendimentosDia);
        }

        return indicadores;
    }
}
