package com.upsaude.integration.fhir.service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.entity.fhir.FhirSyncLog.SyncStatus;
import com.upsaude.repository.fhir.FhirSyncLogRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class FhirSyncLogService {

    private final FhirSyncLogRepository repository;

    @Transactional
    public FhirSyncLog iniciarSincronizacao(String recurso, UUID usuarioId) {
        FhirSyncLog syncLog = FhirSyncLog.builder()
                .recurso(recurso)
                .dataInicio(OffsetDateTime.now())
                .status(SyncStatus.EM_ANDAMENTO)
                .usuarioId(usuarioId)
                .build();

        FhirSyncLog saved = repository.save(syncLog);
        log.info("Iniciada sincronização FHIR para recurso: {}", recurso);
        return saved;
    }

    @Transactional
    public FhirSyncLog concluirSincronizacao(UUID logId, int totalEncontrados, int novosInseridos, int atualizados) {
        FhirSyncLog syncLog = repository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Log de sincronização não encontrado: " + logId));

        syncLog.setTotalEncontrados(totalEncontrados);
        syncLog.setNovosInseridos(novosInseridos);
        syncLog.setAtualizados(atualizados);
        syncLog.concluir();

        FhirSyncLog saved = repository.save(syncLog);
        log.info("Sincronização FHIR concluída para {}: {} encontrados, {} novos, {} atualizados",
                syncLog.getRecurso(), totalEncontrados, novosInseridos, atualizados);
        return saved;
    }

    @Transactional
    public FhirSyncLog registrarErro(UUID logId, String mensagemErro, int erros) {
        FhirSyncLog syncLog = repository.findById(logId)
                .orElseThrow(() -> new IllegalArgumentException("Log de sincronização não encontrado: " + logId));

        syncLog.setErros(erros);
        syncLog.falhar(mensagemErro);

        FhirSyncLog saved = repository.save(syncLog);
        log.error("Sincronização FHIR falhou para {}: {}", syncLog.getRecurso(), mensagemErro);
        return saved;
    }

    public Optional<FhirSyncLog> buscarUltimaSincronizacao(String recurso) {
        return repository.findLastByRecurso(recurso);
    }

    public Optional<FhirSyncLog> buscarUltimaSincronizacaoBemSucedida(String recurso) {
        return repository.findFirstByRecursoAndStatusOrderByDataInicioDesc(recurso, SyncStatus.CONCLUIDO);
    }

    public List<FhirSyncLog> listarPorRecurso(String recurso) {
        return repository.findByRecursoOrderByDataInicioDesc(recurso);
    }

    public List<FhirSyncLog> listarRecentes(int limite) {
        return repository.findRecent(limite);
    }

    public List<FhirSyncLog> listarPorStatus(SyncStatus status) {
        return repository.findByStatusOrderByDataInicioDesc(status);
    }

    public List<String> listarRecursosSincronizados() {
        return repository.findDistinctRecursos();
    }

    public boolean existeSincronizacaoRecente(String recurso, int horas) {
        return buscarUltimaSincronizacaoBemSucedida(recurso)
                .map(log -> log.getDataFim().isAfter(OffsetDateTime.now().minusHours(horas)))
                .orElse(false);
    }
}
