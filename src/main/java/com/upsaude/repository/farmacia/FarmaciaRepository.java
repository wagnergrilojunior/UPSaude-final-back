package com.upsaude.repository.farmacia;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.upsaude.entity.farmacia.Farmacia;

public interface FarmaciaRepository extends JpaRepository<Farmacia, UUID> {

    @Query("SELECT f FROM Farmacia f WHERE f.id = :id AND f.tenant.id = :tenantId")
    Optional<Farmacia> findByIdAndTenant(@Param("id") UUID id, @Param("tenantId") UUID tenantId);

    @Query("SELECT f FROM Farmacia f WHERE f.tenant.id = :tenantId")
    Page<Farmacia> findAllByTenant(@Param("tenantId") UUID tenantId, Pageable pageable);
}

