package com.upsaude.service.impl.api.clinica.prontuario;

import com.upsaude.api.response.clinica.prontuario.ProntuarioResumoResponse;
import com.upsaude.api.response.clinica.prontuario.ProntuarioTimelineResponse;
import com.upsaude.entity.clinica.prontuario.Prontuario;
import com.upsaude.entity.paciente.Paciente;
import com.upsaude.exception.BadRequestException;
import com.upsaude.exception.NotFoundException;
import com.upsaude.repository.clinica.prontuario.ProntuarioRepository;
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
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProntuarioQueryServiceImpl implements ProntuarioQueryService {

    private final ProntuarioRepository prontuarioRepository;
    private final PacienteRepository pacienteRepository;
    private final TenantService tenantService;

    @Override
    @Transactional(readOnly = true)
    public ProntuarioTimelineResponse buscarTimeline(UUID pacienteId) {
        log.debug("Buscando timeline do prontuário. Paciente ID: {}", pacienteId);
        UUID tenantId = tenantService.validarTenantAtual();

        
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        Prontuario prontuario = prontuarioRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, Pageable.unpaged())
                .getContent().stream().findFirst().orElse(null);

        List<ProntuarioTimelineResponse.ProntuarioEventoResponse> eventosResponse = new ArrayList<>();
        if (prontuario != null) {
            eventosResponse.add(toEventoResponse(prontuario));
        }

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

        
        Paciente paciente = pacienteRepository.findById(pacienteId)
                .orElseThrow(() -> new NotFoundException("Paciente não encontrado"));

        Prontuario prontuario = prontuarioRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, Pageable.unpaged())
                .getContent().stream().findFirst().orElse(null);

        int totalAtendimentos = 0;
        int totalConsultas = 0;
        int totalReceitas = 0;
        int totalDispensacoes = 0;
        List<String> principaisDiagnosticos = new ArrayList<>();
        List<ProntuarioResumoResponse.ProntuarioEventoRecenteResponse> eventosRecentes = new ArrayList<>();
        OffsetDateTime ultimaAtualizacao = prontuario != null ? prontuario.getUpdatedAt() : null;

        ProntuarioResumoResponse response = ProntuarioResumoResponse.builder()
                .pacienteId(pacienteId)
                .pacienteNome(paciente.getNomeCompleto())
                .ultimaAtualizacao(ultimaAtualizacao)
                .totalAtendimentos(totalAtendimentos)
                .totalConsultas(totalConsultas)
                .totalReceitas(totalReceitas)
                .totalDispensacoes(totalDispensacoes)
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

        Page<Prontuario> page = prontuarioRepository.findByPacienteIdAndTenantId(pacienteId, tenantId, pageable);

        if (dataInicio != null || dataFim != null) {
            List<Prontuario> filtered = page.getContent().stream()
                    .filter(e -> {
                        OffsetDateTime data = e.getCreatedAt();
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

    private ProntuarioTimelineResponse.ProntuarioEventoResponse toEventoResponse(Prontuario prontuario) {
        return ProntuarioTimelineResponse.ProntuarioEventoResponse.builder()
                .id(prontuario.getId())
                .tipoRegistro("PRONTUARIO")
                .tipoRegistroEnum(null)
                .dataRegistro(prontuario.getCreatedAt())
                .resumo("Prontuário do paciente")
                .atendimentoId(null)
                .consultaId(null)
                .receitaId(null)
                .dispensacaoId(null)
                .build();
    }
}
