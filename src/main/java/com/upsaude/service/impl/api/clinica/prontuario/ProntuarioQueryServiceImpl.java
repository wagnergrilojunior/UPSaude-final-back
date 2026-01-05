package com.upsaude.service.impl.api.clinica.prontuario;

import com.upsaude.api.response.clinica.prontuario.ProntuarioResumoResponse;
import com.upsaude.api.response.clinica.prontuario.ProntuarioTimelineResponse;
import com.upsaude.entity.clinica.prontuario.Prontuarios;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.prontuario.ProntuariosRepository;
import com.upsaude.repository.paciente.PacienteRepository;
import com.upsaude.service.api.clinica.prontuario.ProntuarioQueryService;
import com.upsaude.service.api.sistema.multitenancy.TenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuarioQueryServiceImpl implements ProntuarioQueryService {

    private final ProntuariosRepository prontuariosRepository;
    private final PacienteRepository pacienteRepository;
    private final TenantService tenantService;

    @Override
    @Transactional(readOnly = true)
    public ProntuarioTimelineResponse buscarTimeline(UUID pacienteId) {
        log.debug("Buscando timeline do prontuário. Paciente ID: {}", pacienteId);
        UUID tenantId = tenantService.validarTenantAtual();

        Paciente paciente = pacienteRepository.findByIdAndTenant(pacienteId, tenantId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        List<Prontuarios> eventos = prontuariosRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, Pageable.unpaged())
                .getContent();

        List<ProntuarioTimelineResponse.ProntuarioEventoResponse> eventosResponse = eventos.stream()
                .map(this::toEventoResponse)
                .collect(Collectors.toList());

        ProntuarioTimelineResponse response = ProntuarioTimelineResponse.builder()
                .pacienteId(pacienteId)
                .pacienteNome(paciente.getNomeCompleto())
                .eventos(eventosResponse)
                .build();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public ProntuarioResumoResponse buscarResumo(UUID pacienteId) {
        log.debug("Buscando resumo do prontuário. Paciente ID: {}", pacienteId);
        UUID tenantId = tenantService.validarTenantAtual();

        Paciente paciente = pacienteRepository.findByIdAndTenant(pacienteId, tenantId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        List<Prontuarios> eventos = prontuariosRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, Pageable.unpaged())
                .getContent();

        long totalAtendimentos = eventos.stream()
                .filter(e -> "ATENDIMENTO".equals(e.getTipoRegistroEnum()))
                .count();
        long totalConsultas = eventos.stream()
                .filter(e -> "CONSULTA".equals(e.getTipoRegistroEnum()))
                .count();
        long totalReceitas = eventos.stream()
                .filter(e -> "RECEITA".equals(e.getTipoRegistroEnum()))
                .count();
        long totalDispensacoes = eventos.stream()
                .filter(e -> "DISPENSACAO".equals(e.getTipoRegistroEnum()))
                .count();

        List<String> principaisDiagnosticos = eventos.stream()
                .filter(e -> e.getResumo() != null && e.getResumo().contains("CID"))
                .map(Prontuarios::getResumo)
                .limit(5)
                .collect(Collectors.toList());

        List<ProntuarioResumoResponse.ProntuarioEventoRecenteResponse> eventosRecentes = eventos.stream()
                .sorted((e1, e2) -> {
                    OffsetDateTime d1 = e1.getDataRegistro() != null ? e1.getDataRegistro() : e1.getCreatedAt();
                    OffsetDateTime d2 = e2.getDataRegistro() != null ? e2.getDataRegistro() : e2.getCreatedAt();
                    return d2.compareTo(d1);
                })
                .limit(10)
                .map(e -> ProntuarioResumoResponse.ProntuarioEventoRecenteResponse.builder()
                        .id(e.getId())
                        .tipoRegistro(e.getTipoRegistro())
                        .dataRegistro(e.getDataRegistro() != null ? e.getDataRegistro() : e.getCreatedAt())
                        .resumo(e.getResumo())
                        .build())
                .collect(Collectors.toList());

        OffsetDateTime ultimaAtualizacao = eventos.stream()
                .map(e -> e.getDataRegistro() != null ? e.getDataRegistro() : e.getCreatedAt())
                .max(OffsetDateTime::compareTo)
                .orElse(null);

        ProntuarioResumoResponse response = ProntuarioResumoResponse.builder()
                .pacienteId(pacienteId)
                .pacienteNome(paciente.getNomeCompleto())
                .ultimaAtualizacao(ultimaAtualizacao)
                .totalAtendimentos((int) totalAtendimentos)
                .totalConsultas((int) totalConsultas)
                .totalReceitas((int) totalReceitas)
                .totalDispensacoes((int) totalDispensacoes)
                .principaisDiagnosticos(principaisDiagnosticos)
                .eventosRecentes(eventosRecentes)
                .build();

        return response;
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ProntuarioTimelineResponse.ProntuarioEventoResponse> buscarEventos(
            UUID pacienteId,
            String tipoRegistro,
            OffsetDateTime dataInicio,
            OffsetDateTime dataFim,
            Pageable pageable) {
        log.debug("Buscando eventos do prontuário. Paciente ID: {}, tipo: {}, dataInicio: {}, dataFim: {}", 
                pacienteId, tipoRegistro, dataInicio, dataFim);
        UUID tenantId = tenantService.validarTenantAtual();

        if (pacienteId == null) {
            throw new BadRequestException("ID do paciente é obrigatório");
        }

        Page<Prontuarios> page;
        if (tipoRegistro != null && !tipoRegistro.isEmpty()) {
            page = prontuariosRepository.findByTipoRegistroContainingIgnoreCaseAndTenantId(tipoRegistro, tenantId, pageable);
        } else {
            page = prontuariosRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, pageable);
        }

        if (dataInicio != null || dataFim != null) {
            List<Prontuarios> filtered = page.getContent().stream()
                    .filter(e -> {
                        OffsetDateTime data = e.getDataRegistro() != null ? e.getDataRegistro() : e.getCreatedAt();
                        if (dataInicio != null && data.isBefore(dataInicio)) {
                            return false;
                        }
                        if (dataFim != null && data.isAfter(dataFim)) {
                            return false;
                        }
                        return true;
                    })
                    .collect(Collectors.toList());
            return new org.springframework.data.domain.PageImpl<>(
                    filtered.stream().map(this::toEventoResponse).collect(Collectors.toList()),
                    pageable,
                    filtered.size()
            );
        }

        return page.map(this::toEventoResponse);
    }

    private ProntuarioTimelineResponse.ProntuarioEventoResponse toEventoResponse(Prontuarios prontuario) {
        return ProntuarioTimelineResponse.ProntuarioEventoResponse.builder()
                .id(prontuario.getId())
                .tipoRegistro(prontuario.getTipoRegistro())
                .tipoRegistroEnum(prontuario.getTipoRegistroEnum())
                .dataRegistro(prontuario.getDataRegistro() != null ? prontuario.getDataRegistro() : prontuario.getCreatedAt())
                .resumo(prontuario.getResumo())
                .atendimentoId(prontuario.getAtendimento() != null ? prontuario.getAtendimento().getId() : null)
                .consultaId(prontuario.getConsulta() != null ? prontuario.getConsulta().getId() : null)
                .receitaId(prontuario.getReceita() != null ? prontuario.getReceita().getId() : null)
                .dispensacaoId(prontuario.getDispensacao() != null ? prontuario.getDispensacao().getId() : null)
                .build();
    }
}

