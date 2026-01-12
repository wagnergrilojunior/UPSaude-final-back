package com.upsaude.repository.fhir;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.upsaude.entity.fhir.FhirSyncLog;
import com.upsaude.entity.fhir.FhirSyncLog.SyncStatus;

@Repository
public interface FhirSyncLogRepository extends JpaRepository<FhirSyncLog, UUID> {

    List<FhirSyncLog> findByRecursoOrderByDataInicioDesc(String recurso);

    Optional<FhirSyncLog> findFirstByRecursoAndStatusOrderByDataInicioDesc(String recurso, SyncStatus status);

    @Query("SELECT f FROM FhirSyncLog f WHERE f.recurso = :recurso ORDER BY f.dataInicio DESC LIMIT 1")
    Optional<FhirSyncLog> findLastByRecurso(@Param("recurso") String recurso);

    List<FhirSyncLog> findByStatusOrderByDataInicioDesc(SyncStatus status);

    List<FhirSyncLog> findByDataInicioBetweenOrderByDataInicioDesc(OffsetDateTime inicio, OffsetDateTime fim);

    @Query("SELECT f FROM FhirSyncLog f ORDER BY f.dataInicio DESC LIMIT :limit")
    List<FhirSyncLog> findRecent(@Param("limit") int limit);

    long countByRecursoAndStatus(String recurso, SyncStatus status);

    @Query("SELECT DISTINCT f.recurso FROM FhirSyncLog f ORDER BY f.recurso")
    List<String> findDistinctRecursos();
}
