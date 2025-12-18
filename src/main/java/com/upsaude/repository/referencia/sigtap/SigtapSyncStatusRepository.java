package com.upsaude.repository.referencia.sigtap;

import com.upsaude.entity.referencia.sigtap.SigtapSyncStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SigtapSyncStatusRepository extends JpaRepository<SigtapSyncStatus, UUID> {
    
    Optional<SigtapSyncStatus> findByCompetenciaAndStatus(String competencia, String status);
    
    Optional<SigtapSyncStatus> findFirstByCompetenciaOrderByCreatedAtDesc(String competencia);
}
