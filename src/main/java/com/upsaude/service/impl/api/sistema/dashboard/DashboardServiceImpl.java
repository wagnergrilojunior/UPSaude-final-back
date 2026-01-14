package com.upsaude.service.impl.api.sistema.dashboard;

import com.upsaude.api.response.sistema.dashboard.DashboardEstabelecimentoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardMedicoResponse;
import com.upsaude.api.response.sistema.dashboard.DashboardTenantResponse;
import com.upsaude.entity.estabelecimento.Estabelecimentos;
import com.upsaude.entity.profissional.Medicos;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.agendamento.AgendamentoRepository;
import com.upsaude.repository.clinica.atendimento.AtendimentoRepository;
import com.upsaude.repository.clinica.atendimento.ConsultasRepository;
import com.upsaude.repository.estabelecimento.EstabelecimentosRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.repository.profissional.MedicosRepository;
import com.upsaude.service.api.sistema.dashboard.DashboardService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import com.upsaude.service.api.support.relatorios.EstabelecimentoFilterHelper;
import com.upsaude.service.api.support.relatorios.MedicoFilterHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final TenantService tenantService;
    private final AtendimentoRepository atendimentoRepository;
    private final ConsultasRepository consultasRepository;
    private final AgendamentoRepository agendamentoRepository;
    private final PacienteRepository pacienteRepository;
    private final EstabelecimentosRepository estabelecimentosRepository;
    private final MedicosRepository medicosRepository;
    private final EstabelecimentoFilterHelper estabelecimentoFilterHelper;
    private final MedicoFilterHelper medicoFilterHelper;

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "dashboardTenant",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public DashboardTenantResponse dashboardTenant(UUID tenantId, LocalDate dataInicio, LocalDate dataFim) {
        log.debug("Gerando dashboard para tenant {} entre {} e {}", tenantId, dataInicio, dataFim);
        
        UUID currentTenantId = tenantService.validarTenantAtual();
        if (!currentTenantId.equals(tenantId)) {
            throw new BadRequestException("Não é possível acessar dashboard de outro tenant");
        }

        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        // KPIs principais
        long totalAtendimentos = contarAtendimentos(tenantId, inicio, fim);
        long totalConsultas = contarConsultas(tenantId, inicio, fim);
        long totalAgendamentos = contarAgendamentos(tenantId, inicio, fim);
        long totalPacientes = contarPacientes(tenantId);
        long totalEstabelecimentos = contarEstabelecimentos(tenantId);

        // Evolução temporal
        List<DashboardTenantResponse.EvolucaoTemporal> evolucaoTemporal = calcularEvolucaoTemporal(tenantId, dataInicio, dataFim);

        // Top procedimentos (via agendamentos com especialidade)
        List<DashboardTenantResponse.ItemTopProcedimento> topProcedimentos = calcularTopProcedimentos(tenantId, inicio, fim, 10);

        // Top CID
        List<DashboardTenantResponse.ItemTopCid> topCids = calcularTopCids(tenantId, inicio, fim, 10);

        // Top médicos
        List<DashboardTenantResponse.ItemTopMedico> topMedicos = calcularTopMedicos(tenantId, inicio, fim, 10);

        // Comparação com período anterior
        DashboardTenantResponse.ComparacaoPeriodo comparacao = calcularComparacaoPeriodo(tenantId, dataInicio, dataFim);

        // Indicadores
        Map<String, BigDecimal> indicadores = calcularIndicadores(totalAtendimentos, totalConsultas, totalAgendamentos, totalPacientes, inicio, fim);

        return DashboardTenantResponse.builder()
                .tenantId(tenantId)
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalAgendamentos(totalAgendamentos)
                .totalPacientes(totalPacientes)
                .totalEstabelecimentos(totalEstabelecimentos)
                .evolucaoTemporal(evolucaoTemporal)
                .topProcedimentos(topProcedimentos)
                .topCids(topCids)
                .topMedicos(topMedicos)
                .comparacaoPeriodoAnterior(comparacao)
                .indicadores(indicadores)
                .build();
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "dashboardEstabelecimento",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public DashboardEstabelecimentoResponse dashboardEstabelecimento(UUID estabelecimentoId, LocalDate dataInicio, LocalDate dataFim) {
        log.debug("Gerando dashboard para estabelecimento {} entre {} e {}", estabelecimentoId, dataInicio, dataFim);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        if (!estabelecimentoFilterHelper.validarEstabelecimentoPertenceAoTenant(estabelecimentoId, tenantId)) {
            throw new NotFoundException("Estabelecimento não encontrado ou não pertence ao tenant");
        }

        Estabelecimentos estabelecimento = estabelecimentosRepository.findByIdAndTenant(estabelecimentoId, tenantId)
                .orElseThrow(() -> new NotFoundException("Estabelecimento não encontrado"));

        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        // KPIs principais
        long totalAtendimentos = contarAtendimentosPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim);
        long totalConsultas = contarConsultasPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim);
        long totalAgendamentos = contarAgendamentosPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim);
        long totalPacientes = contarPacientesPorEstabelecimento(estabelecimentoId, tenantId);
        long totalMedicos = contarMedicosPorEstabelecimento(estabelecimentoId, tenantId);

        // Evolução temporal
        List<DashboardTenantResponse.EvolucaoTemporal> evolucaoTemporal = calcularEvolucaoTemporalPorEstabelecimento(estabelecimentoId, tenantId, dataInicio, dataFim);

        // Top procedimentos
        List<DashboardTenantResponse.ItemTopProcedimento> topProcedimentos = calcularTopProcedimentosPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim, 10);

        // Top CID
        List<DashboardTenantResponse.ItemTopCid> topCids = calcularTopCidsPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim, 10);

        // Top médicos
        List<DashboardTenantResponse.ItemTopMedico> topMedicos = calcularTopMedicosPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim, 10);

        // Comparação com período anterior
        DashboardTenantResponse.ComparacaoPeriodo comparacao = calcularComparacaoPeriodoPorEstabelecimento(estabelecimentoId, tenantId, dataInicio, dataFim);

        // Indicadores
        Map<String, BigDecimal> indicadores = calcularIndicadores(totalAtendimentos, totalConsultas, totalAgendamentos, totalPacientes, inicio, fim);

        String estabelecimentoNome = estabelecimento.getDadosIdentificacao() != null 
                ? estabelecimento.getDadosIdentificacao().getNome() 
                : "Estabelecimento";
        String estabelecimentoCnes = estabelecimento.getDadosIdentificacao() != null 
                ? estabelecimento.getDadosIdentificacao().getCnes() 
                : null;

        return DashboardEstabelecimentoResponse.builder()
                .estabelecimentoId(estabelecimentoId)
                .estabelecimentoNome(estabelecimentoNome)
                .estabelecimentoCnes(estabelecimentoCnes)
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalAgendamentos(totalAgendamentos)
                .totalPacientes(totalPacientes)
                .totalMedicos(totalMedicos)
                .evolucaoTemporal(evolucaoTemporal)
                .topProcedimentos(topProcedimentos)
                .topCids(topCids)
                .topMedicos(topMedicos)
                .comparacaoPeriodoAnterior(comparacao)
                .indicadores(indicadores)
                .build();
    }

    @Override
    @org.springframework.cache.annotation.Cacheable(
            value = "dashboardMedico",
            keyGenerator = "relatoriosCacheKeyGenerator",
            unless = "#result == null"
    )
    public DashboardMedicoResponse dashboardMedico(UUID medicoId, LocalDate dataInicio, LocalDate dataFim) {
        log.debug("Gerando dashboard para médico {} entre {} e {}", medicoId, dataInicio, dataFim);
        
        UUID tenantId = tenantService.validarTenantAtual();
        
        if (!medicoFilterHelper.validarMedicoPertenceAoTenant(medicoId, tenantId)) {
            throw new NotFoundException("Médico não encontrado ou não pertence ao tenant");
        }

        Medicos medico = medicosRepository.findByIdAndTenant(medicoId, tenantId)
                .orElseThrow(() -> new NotFoundException("Médico não encontrado"));

        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);

        // KPIs principais
        long totalAtendimentos = contarAtendimentosPorMedico(medicoId, tenantId, inicio, fim);
        long totalConsultas = contarConsultasPorMedico(medicoId, tenantId, inicio, fim);
        long totalAgendamentos = contarAgendamentosPorMedico(medicoId, tenantId, inicio, fim);
        long totalPacientes = contarPacientesPorMedico(medicoId, tenantId);

        // Evolução temporal
        List<DashboardTenantResponse.EvolucaoTemporal> evolucaoTemporal = calcularEvolucaoTemporalPorMedico(medicoId, tenantId, dataInicio, dataFim);

        // Top procedimentos
        List<DashboardTenantResponse.ItemTopProcedimento> topProcedimentos = calcularTopProcedimentosPorMedico(medicoId, tenantId, inicio, fim, 10);

        // Top CID
        List<DashboardTenantResponse.ItemTopCid> topCids = calcularTopCidsPorMedico(medicoId, tenantId, inicio, fim, 10);

        // Comparação com período anterior
        DashboardTenantResponse.ComparacaoPeriodo comparacao = calcularComparacaoPeriodoPorMedico(medicoId, tenantId, dataInicio, dataFim);

        // Indicadores
        Map<String, BigDecimal> indicadores = calcularIndicadores(totalAtendimentos, totalConsultas, totalAgendamentos, totalPacientes, inicio, fim);

        String medicoNome = medico.getDadosPessoaisBasicos() != null 
                ? medico.getDadosPessoaisBasicos().getNomeCompleto() 
                : "Médico";
        String medicoCrm = medico.getRegistroProfissional() != null 
                ? medico.getRegistroProfissional().getCrm() 
                : null;

        return DashboardMedicoResponse.builder()
                .medicoId(medicoId)
                .medicoNome(medicoNome)
                .medicoCrm(medicoCrm)
                .dataInicio(dataInicio)
                .dataFim(dataFim)
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalAgendamentos(totalAgendamentos)
                .totalPacientes(totalPacientes)
                .evolucaoTemporal(evolucaoTemporal)
                .topProcedimentos(topProcedimentos)
                .topCids(topCids)
                .comparacaoPeriodoAnterior(comparacao)
                .indicadores(indicadores)
                .build();
    }

    // Métodos auxiliares para contagem
    private long contarAtendimentos(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getInformacoes() != null && a.getInformacoes().getDataHora() != null
                        && !a.getInformacoes().getDataHora().isBefore(inicio)
                        && !a.getInformacoes().getDataHora().isAfter(fim))
                .count();
    }

    private long contarConsultas(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return consultasRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(c -> {
                    OffsetDateTime dataConsulta = c.getInformacoes() != null && c.getInformacoes().getDataConsulta() != null
                            ? c.getInformacoes().getDataConsulta()
                            : c.getCreatedAt();
                    return dataConsulta != null && !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                })
                .count();
    }

    private long contarAgendamentos(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return agendamentoRepository.findByDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
    }

    private long contarPacientes(UUID tenantId) {
        return pacienteRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
    }

    private long contarEstabelecimentos(UUID tenantId) {
        com.upsaude.entity.sistema.multitenancy.Tenant tenant = new com.upsaude.entity.sistema.multitenancy.Tenant();
        tenant.setId(tenantId);
        return estabelecimentosRepository.findByTenant(tenant).size();
    }

    // Métodos específicos por estabelecimento
    private long contarAtendimentosPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId())
                        && a.getInformacoes() != null && a.getInformacoes().getDataHora() != null
                        && !a.getInformacoes().getDataHora().isBefore(inicio)
                        && !a.getInformacoes().getDataHora().isAfter(fim))
                .count();
    }

    private long contarConsultasPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return consultasRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(c -> c.getAtendimento() != null && c.getAtendimento().getEstabelecimento() != null
                        && estabelecimentoId.equals(c.getAtendimento().getEstabelecimento().getId()))
                .filter(c -> {
                    OffsetDateTime dataConsulta = c.getInformacoes() != null && c.getInformacoes().getDataConsulta() != null
                            ? c.getInformacoes().getDataConsulta()
                            : c.getCreatedAt();
                    return dataConsulta != null && !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                })
                .count();
    }

    private long contarAgendamentosPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                estabelecimentoId, inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
    }

    private long contarPacientesPorEstabelecimento(UUID estabelecimentoId, UUID tenantId) {
        return atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId()))
                .map(a -> a.getPaciente().getId())
                .distinct()
                .count();
    }

    private long contarMedicosPorEstabelecimento(UUID estabelecimentoId, UUID tenantId) {
        return agendamentoRepository.findByEstabelecimentoIdAndTenantIdOrderByDataHoraAsc(
                estabelecimentoId, tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getMedico() != null)
                .map(a -> a.getMedico().getId())
                .distinct()
                .count();
    }

    // Métodos específicos por médico
    private long contarAtendimentosPorMedico(UUID medicoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        // Atendimentos não têm relação direta com médico, apenas com profissional
        // Por enquanto retornamos 0, pode ser implementado quando houver relacionamento
        return 0L;
    }

    private long contarConsultasPorMedico(UUID medicoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return consultasRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(c -> c.getAtendimento() != null && c.getAtendimento().getProfissional() != null)
                .filter(c -> {
                    OffsetDateTime dataConsulta = c.getInformacoes() != null && c.getInformacoes().getDataConsulta() != null
                            ? c.getInformacoes().getDataConsulta()
                            : c.getCreatedAt();
                    return dataConsulta != null && !dataConsulta.isBefore(inicio) && !dataConsulta.isAfter(fim);
                })
                .count();
    }

    private long contarAgendamentosPorMedico(UUID medicoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim) {
        return agendamentoRepository.findByMedicoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                medicoId, inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE)).getTotalElements();
    }

    private long contarPacientesPorMedico(UUID medicoId, UUID tenantId) {
        return agendamentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getMedico() != null && medicoId.equals(a.getMedico().getId()))
                .map(a -> a.getPaciente().getId())
                .distinct()
                .count();
    }

    // Métodos de cálculo de evolução temporal, tops e comparações
    private List<DashboardTenantResponse.EvolucaoTemporal> calcularEvolucaoTemporal(UUID tenantId, LocalDate dataInicio, LocalDate dataFim) {
        List<DashboardTenantResponse.EvolucaoTemporal> evolucao = new ArrayList<>();
        LocalDate dataAtual = dataInicio;
        
        while (!dataAtual.isAfter(dataFim)) {
            OffsetDateTime inicioDia = dataAtual.atStartOfDay().atOffset(ZoneOffset.UTC);
            OffsetDateTime fimDia = dataAtual.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
            
            long atendimentos = contarAtendimentos(tenantId, inicioDia, fimDia);
            long consultas = contarConsultas(tenantId, inicioDia, fimDia);
            long agendamentos = contarAgendamentos(tenantId, inicioDia, fimDia);
            
            evolucao.add(DashboardTenantResponse.EvolucaoTemporal.builder()
                    .data(dataAtual)
                    .atendimentos(atendimentos)
                    .consultas(consultas)
                    .agendamentos(agendamentos)
                    .build());
            
            dataAtual = dataAtual.plusDays(1);
        }
        
        return evolucao;
    }

    private List<DashboardTenantResponse.EvolucaoTemporal> calcularEvolucaoTemporalPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, LocalDate dataInicio, LocalDate dataFim) {
        List<DashboardTenantResponse.EvolucaoTemporal> evolucao = new ArrayList<>();
        LocalDate dataAtual = dataInicio;
        
        while (!dataAtual.isAfter(dataFim)) {
            OffsetDateTime inicioDia = dataAtual.atStartOfDay().atOffset(ZoneOffset.UTC);
            OffsetDateTime fimDia = dataAtual.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
            
            long atendimentos = contarAtendimentosPorEstabelecimento(estabelecimentoId, tenantId, inicioDia, fimDia);
            long consultas = contarConsultasPorEstabelecimento(estabelecimentoId, tenantId, inicioDia, fimDia);
            long agendamentos = contarAgendamentosPorEstabelecimento(estabelecimentoId, tenantId, inicioDia, fimDia);
            
            evolucao.add(DashboardTenantResponse.EvolucaoTemporal.builder()
                    .data(dataAtual)
                    .atendimentos(atendimentos)
                    .consultas(consultas)
                    .agendamentos(agendamentos)
                    .build());
            
            dataAtual = dataAtual.plusDays(1);
        }
        
        return evolucao;
    }

    private List<DashboardTenantResponse.EvolucaoTemporal> calcularEvolucaoTemporalPorMedico(UUID medicoId, UUID tenantId, LocalDate dataInicio, LocalDate dataFim) {
        List<DashboardTenantResponse.EvolucaoTemporal> evolucao = new ArrayList<>();
        LocalDate dataAtual = dataInicio;
        
        while (!dataAtual.isAfter(dataFim)) {
            OffsetDateTime inicioDia = dataAtual.atStartOfDay().atOffset(ZoneOffset.UTC);
            OffsetDateTime fimDia = dataAtual.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
            
            long atendimentos = contarAtendimentosPorMedico(medicoId, tenantId, inicioDia, fimDia);
            long consultas = contarConsultasPorMedico(medicoId, tenantId, inicioDia, fimDia);
            long agendamentos = contarAgendamentosPorMedico(medicoId, tenantId, inicioDia, fimDia);
            
            evolucao.add(DashboardTenantResponse.EvolucaoTemporal.builder()
                    .data(dataAtual)
                    .atendimentos(atendimentos)
                    .consultas(consultas)
                    .agendamentos(agendamentos)
                    .build());
            
            dataAtual = dataAtual.plusDays(1);
        }
        
        return evolucao;
    }

    private List<DashboardTenantResponse.ItemTopProcedimento> calcularTopProcedimentos(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        // Por enquanto retornamos lista vazia, pode ser implementado quando houver dados de procedimentos
        return new ArrayList<>();
    }

    private List<DashboardTenantResponse.ItemTopProcedimento> calcularTopProcedimentosPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        return new ArrayList<>();
    }

    private List<DashboardTenantResponse.ItemTopProcedimento> calcularTopProcedimentosPorMedico(UUID medicoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        return new ArrayList<>();
    }

    private List<DashboardTenantResponse.ItemTopCid> calcularTopCids(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        Map<String, Long> cidCounts = new HashMap<>();
        Map<String, String> cidDescricoes = new HashMap<>();
        
        atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getInformacoes() != null && a.getInformacoes().getDataHora() != null
                        && !a.getInformacoes().getDataHora().isBefore(inicio)
                        && !a.getInformacoes().getDataHora().isAfter(fim))
                .filter(a -> a.getDiagnostico() != null && a.getDiagnostico().getMainCid10() != null)
                .forEach(a -> {
                    String cid = a.getDiagnostico().getMainCid10().getSubcat() != null 
                            ? a.getDiagnostico().getMainCid10().getSubcat() 
                            : a.getDiagnostico().getMainCid10().getId().toString();
                    cidCounts.put(cid, cidCounts.getOrDefault(cid, 0L) + 1);
                    if (!cidDescricoes.containsKey(cid) && a.getDiagnostico().getMainCid10().getDescricao() != null) {
                        cidDescricoes.put(cid, a.getDiagnostico().getMainCid10().getDescricao());
                    }
                });
        
        return cidCounts.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .map(entry -> DashboardTenantResponse.ItemTopCid.builder()
                        .cidCodigo(entry.getKey())
                        .cidDescricao(cidDescricoes.getOrDefault(entry.getKey(), entry.getKey()))
                        .quantidade(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<DashboardTenantResponse.ItemTopCid> calcularTopCidsPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        Map<String, Long> cidCounts = new HashMap<>();
        Map<String, String> cidDescricoes = new HashMap<>();
        
        atendimentoRepository.findAllByTenant(tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getEstabelecimento() != null && estabelecimentoId.equals(a.getEstabelecimento().getId())
                        && a.getInformacoes() != null && a.getInformacoes().getDataHora() != null
                        && !a.getInformacoes().getDataHora().isBefore(inicio)
                        && !a.getInformacoes().getDataHora().isAfter(fim))
                .filter(a -> a.getDiagnostico() != null && a.getDiagnostico().getMainCid10() != null)
                .forEach(a -> {
                    String cid = a.getDiagnostico().getMainCid10().getSubcat() != null 
                            ? a.getDiagnostico().getMainCid10().getSubcat() 
                            : a.getDiagnostico().getMainCid10().getId().toString();
                    cidCounts.put(cid, cidCounts.getOrDefault(cid, 0L) + 1);
                    if (!cidDescricoes.containsKey(cid) && a.getDiagnostico().getMainCid10().getDescricao() != null) {
                        cidDescricoes.put(cid, a.getDiagnostico().getMainCid10().getDescricao());
                    }
                });
        
        return cidCounts.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .map(entry -> DashboardTenantResponse.ItemTopCid.builder()
                        .cidCodigo(entry.getKey())
                        .cidDescricao(cidDescricoes.getOrDefault(entry.getKey(), entry.getKey()))
                        .quantidade(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<DashboardTenantResponse.ItemTopCid> calcularTopCidsPorMedico(UUID medicoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        // Por enquanto retornamos lista vazia, pode ser implementado quando houver relacionamento médico-atendimento
        return new ArrayList<>();
    }

    private List<DashboardTenantResponse.ItemTopMedico> calcularTopMedicos(UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        Map<UUID, Long> medicoAtendimentos = new HashMap<>();
        Map<UUID, Long> medicoAgendamentos = new HashMap<>();
        Map<UUID, String> medicoNomes = new HashMap<>();
        
        agendamentoRepository.findByDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getMedico() != null)
                .forEach(a -> {
                    UUID medicoId = a.getMedico().getId();
                    medicoAgendamentos.put(medicoId, medicoAgendamentos.getOrDefault(medicoId, 0L) + 1);
                    if (medicoNomes.get(medicoId) == null && a.getMedico().getDadosPessoaisBasicos() != null) {
                        medicoNomes.put(medicoId, a.getMedico().getDadosPessoaisBasicos().getNomeCompleto());
                    }
                });
        
        return medicoAgendamentos.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .map(entry -> DashboardTenantResponse.ItemTopMedico.builder()
                        .medicoId(entry.getKey())
                        .medicoNome(medicoNomes.getOrDefault(entry.getKey(), entry.getKey().toString()))
                        .totalAtendimentos(medicoAtendimentos.getOrDefault(entry.getKey(), 0L))
                        .totalAgendamentos(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private List<DashboardTenantResponse.ItemTopMedico> calcularTopMedicosPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, OffsetDateTime inicio, OffsetDateTime fim, int limit) {
        Map<UUID, Long> medicoAtendimentos = new HashMap<>();
        Map<UUID, Long> medicoAgendamentos = new HashMap<>();
        Map<UUID, String> medicoNomes = new HashMap<>();
        
        agendamentoRepository.findByEstabelecimentoIdAndDataHoraBetweenAndTenantIdOrderByDataHoraAsc(
                estabelecimentoId, inicio, fim, tenantId, PageRequest.of(0, Integer.MAX_VALUE))
                .getContent().stream()
                .filter(a -> a.getMedico() != null)
                .forEach(a -> {
                    UUID medicoId = a.getMedico().getId();
                    medicoAgendamentos.put(medicoId, medicoAgendamentos.getOrDefault(medicoId, 0L) + 1);
                    if (medicoNomes.get(medicoId) == null && a.getMedico().getDadosPessoaisBasicos() != null) {
                        medicoNomes.put(medicoId, a.getMedico().getDadosPessoaisBasicos().getNomeCompleto());
                    }
                });
        
        return medicoAgendamentos.entrySet().stream()
                .sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue()))
                .limit(limit)
                .map(entry -> DashboardTenantResponse.ItemTopMedico.builder()
                        .medicoId(entry.getKey())
                        .medicoNome(medicoNomes.getOrDefault(entry.getKey(), entry.getKey().toString()))
                        .totalAtendimentos(medicoAtendimentos.getOrDefault(entry.getKey(), 0L))
                        .totalAgendamentos(entry.getValue())
                        .build())
                .collect(Collectors.toList());
    }

    private DashboardTenantResponse.ComparacaoPeriodo calcularComparacaoPeriodo(UUID tenantId, LocalDate dataInicio, LocalDate dataFim) {
        long diasPeriodo = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
        LocalDate periodoAnteriorInicio = dataInicio.minusDays(diasPeriodo);
        LocalDate periodoAnteriorFim = dataInicio.minusDays(1);
        
        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
        OffsetDateTime inicioAnterior = periodoAnteriorInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fimAnterior = periodoAnteriorFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
        
        long atendimentosAtual = contarAtendimentos(tenantId, inicio, fim);
        long atendimentosAnterior = contarAtendimentos(tenantId, inicioAnterior, fimAnterior);
        long consultasAtual = contarConsultas(tenantId, inicio, fim);
        long consultasAnterior = contarConsultas(tenantId, inicioAnterior, fimAnterior);
        long agendamentosAtual = contarAgendamentos(tenantId, inicio, fim);
        long agendamentosAnterior = contarAgendamentos(tenantId, inicioAnterior, fimAnterior);
        
        BigDecimal variacaoAtendimentos = calcularVariacao(atendimentosAnterior, atendimentosAtual);
        BigDecimal variacaoConsultas = calcularVariacao(consultasAnterior, consultasAtual);
        BigDecimal variacaoAgendamentos = calcularVariacao(agendamentosAnterior, agendamentosAtual);
        BigDecimal variacaoPacientes = BigDecimal.ZERO; // Simplificado - pode ser implementado quando necessário
        
        return DashboardTenantResponse.ComparacaoPeriodo.builder()
                .variacaoAtendimentos(variacaoAtendimentos)
                .variacaoConsultas(variacaoConsultas)
                .variacaoAgendamentos(variacaoAgendamentos)
                .variacaoPacientes(variacaoPacientes)
                .build();
    }

    private DashboardTenantResponse.ComparacaoPeriodo calcularComparacaoPeriodoPorEstabelecimento(UUID estabelecimentoId, UUID tenantId, LocalDate dataInicio, LocalDate dataFim) {
        long diasPeriodo = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
        LocalDate periodoAnteriorInicio = dataInicio.minusDays(diasPeriodo);
        LocalDate periodoAnteriorFim = dataInicio.minusDays(1);
        
        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
        OffsetDateTime inicioAnterior = periodoAnteriorInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fimAnterior = periodoAnteriorFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
        
        long atendimentosAtual = contarAtendimentosPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim);
        long atendimentosAnterior = contarAtendimentosPorEstabelecimento(estabelecimentoId, tenantId, inicioAnterior, fimAnterior);
        long consultasAtual = contarConsultasPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim);
        long consultasAnterior = contarConsultasPorEstabelecimento(estabelecimentoId, tenantId, inicioAnterior, fimAnterior);
        long agendamentosAtual = contarAgendamentosPorEstabelecimento(estabelecimentoId, tenantId, inicio, fim);
        long agendamentosAnterior = contarAgendamentosPorEstabelecimento(estabelecimentoId, tenantId, inicioAnterior, fimAnterior);
        
        BigDecimal variacaoAtendimentos = calcularVariacao(atendimentosAnterior, atendimentosAtual);
        BigDecimal variacaoConsultas = calcularVariacao(consultasAnterior, consultasAtual);
        BigDecimal variacaoAgendamentos = calcularVariacao(agendamentosAnterior, agendamentosAtual);
        
        return DashboardTenantResponse.ComparacaoPeriodo.builder()
                .variacaoAtendimentos(variacaoAtendimentos)
                .variacaoConsultas(variacaoConsultas)
                .variacaoAgendamentos(variacaoAgendamentos)
                .variacaoPacientes(BigDecimal.ZERO)
                .build();
    }

    private DashboardTenantResponse.ComparacaoPeriodo calcularComparacaoPeriodoPorMedico(UUID medicoId, UUID tenantId, LocalDate dataInicio, LocalDate dataFim) {
        long diasPeriodo = java.time.temporal.ChronoUnit.DAYS.between(dataInicio, dataFim) + 1;
        LocalDate periodoAnteriorInicio = dataInicio.minusDays(diasPeriodo);
        LocalDate periodoAnteriorFim = dataInicio.minusDays(1);
        
        OffsetDateTime inicio = dataInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fim = dataFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
        OffsetDateTime inicioAnterior = periodoAnteriorInicio.atStartOfDay().atOffset(ZoneOffset.UTC);
        OffsetDateTime fimAnterior = periodoAnteriorFim.atTime(23, 59, 59).atOffset(ZoneOffset.UTC);
        
        long atendimentosAtual = contarAtendimentosPorMedico(medicoId, tenantId, inicio, fim);
        long atendimentosAnterior = contarAtendimentosPorMedico(medicoId, tenantId, inicioAnterior, fimAnterior);
        long consultasAtual = contarConsultasPorMedico(medicoId, tenantId, inicio, fim);
        long consultasAnterior = contarConsultasPorMedico(medicoId, tenantId, inicioAnterior, fimAnterior);
        long agendamentosAtual = contarAgendamentosPorMedico(medicoId, tenantId, inicio, fim);
        long agendamentosAnterior = contarAgendamentosPorMedico(medicoId, tenantId, inicioAnterior, fimAnterior);
        
        BigDecimal variacaoAtendimentos = calcularVariacao(atendimentosAnterior, atendimentosAtual);
        BigDecimal variacaoConsultas = calcularVariacao(consultasAnterior, consultasAtual);
        BigDecimal variacaoAgendamentos = calcularVariacao(agendamentosAnterior, agendamentosAtual);
        
        return DashboardTenantResponse.ComparacaoPeriodo.builder()
                .variacaoAtendimentos(variacaoAtendimentos)
                .variacaoConsultas(variacaoConsultas)
                .variacaoAgendamentos(variacaoAgendamentos)
                .variacaoPacientes(BigDecimal.ZERO)
                .build();
    }

    private BigDecimal calcularVariacao(long valorAnterior, long valorAtual) {
        if (valorAnterior == 0) {
            return valorAtual > 0 ? BigDecimal.valueOf(100) : BigDecimal.ZERO;
        }
        return BigDecimal.valueOf(valorAtual - valorAnterior)
                .divide(BigDecimal.valueOf(valorAnterior), 4, RoundingMode.HALF_UP)
                .multiply(BigDecimal.valueOf(100));
    }

    private Map<String, BigDecimal> calcularIndicadores(long totalAtendimentos, long totalConsultas, long totalAgendamentos, long totalPacientes, OffsetDateTime inicio, OffsetDateTime fim) {
        Map<String, BigDecimal> indicadores = new HashMap<>();
        
        long dias = java.time.temporal.ChronoUnit.DAYS.between(inicio.toLocalDate(), fim.toLocalDate()) + 1;
        if (dias > 0) {
            BigDecimal mediaAtendimentosDia = BigDecimal.valueOf(totalAtendimentos)
                    .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
            indicadores.put("mediaAtendimentosPorDia", mediaAtendimentosDia);
            
            BigDecimal mediaConsultasDia = BigDecimal.valueOf(totalConsultas)
                    .divide(BigDecimal.valueOf(dias), 2, RoundingMode.HALF_UP);
            indicadores.put("mediaConsultasPorDia", mediaConsultasDia);
        }
        
        if (totalAgendamentos > 0) {
            BigDecimal taxaOcupacao = BigDecimal.valueOf(totalConsultas)
                    .divide(BigDecimal.valueOf(totalAgendamentos), 4, RoundingMode.HALF_UP)
                    .multiply(BigDecimal.valueOf(100));
            indicadores.put("taxaOcupacaoAgendamentos", taxaOcupacao);
        }
        
        if (totalPacientes > 0) {
            BigDecimal atendimentosPorPaciente = BigDecimal.valueOf(totalAtendimentos)
                    .divide(BigDecimal.valueOf(totalPacientes), 2, RoundingMode.HALF_UP);
            indicadores.put("atendimentosPorPaciente", atendimentosPorPaciente);
        }
        
        return indicadores;
    }
}
